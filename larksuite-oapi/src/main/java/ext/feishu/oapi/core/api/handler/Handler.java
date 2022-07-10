package ext.feishu.oapi.core.api.handler;

import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.api.Constants;
import ext.feishu.oapi.core.api.exception.AppTicketIsEmptyException;
import ext.feishu.oapi.core.api.handler.subhandler.BuildSubHandler;
import ext.feishu.oapi.core.api.handler.subhandler.InitSubHandler;
import ext.feishu.oapi.core.api.handler.subhandler.SendSubHandler;
import ext.feishu.oapi.core.api.handler.subhandler.SignSubHandler;
import ext.feishu.oapi.core.api.handler.subhandler.UnmarshalRespSubHandler;
import ext.feishu.oapi.core.api.handler.subhandler.ValidateRespSubHandler;
import ext.feishu.oapi.core.api.handler.subhandler.ValidateSubHandler;
import ext.feishu.oapi.core.api.request.Request;
import ext.feishu.oapi.core.api.response.Body;
import ext.feishu.oapi.core.api.response.EmptyData;
import ext.feishu.oapi.core.api.token.ApplyAppTicketReq;
import ext.feishu.oapi.core.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Handler {
    public static final Handler DEFAULT = new Handler();
    private static final Logger log = LoggerFactory.getLogger(Handler.class);
    private final SubHandler init;
    private final SubHandler validate;
    private final SubHandler build;
    private final SubHandler sign;
    private final SubHandler send;
    private final SubHandler validateResponse;
    private final SubHandler unmarshalResponse;

    private Handler() {
        this.init = new InitSubHandler();
        this.validate = new ValidateSubHandler();
        this.build = new BuildSubHandler();
        this.sign = new SignSubHandler();
        this.send = new SendSubHandler();
        this.validateResponse = new ValidateRespSubHandler();
        this.unmarshalResponse = new UnmarshalRespSubHandler();
    }

    public <I, O> void handle(Context ctx, Request<I, O> req) throws Exception {
        Exception exception = null;
        try {
            this.init.handle(ctx, req);
            this.validate.handle(ctx, req);
            int i = 0;
            do {
                this.build.handle(ctx, req);
                this.sign.handle(ctx, req);
                this.send.handle(ctx, req);
                this.validateResponse.handle(ctx, req);
                this.unmarshalResponse.handle(ctx, req);
                i++;
            } while (req.isRetry() && i < 2);
        } catch (Exception e) {
            exception = e;
        } finally {
            complement(ctx, req, exception);
        }
    }

    private void deleteTmpFile(String requestBodyFilePath) {
        if (Strings.isEmpty(requestBodyFilePath)) {
            return;
        }
        new File(requestBodyFilePath).delete();
    }

    private <I, O> void complement(Context ctx, Request<I, O> req, Exception e) throws Exception {
        deleteTmpFile(req.getRequestBodyFilePath());
        if (e != null) {
            if (e instanceof AppTicketIsEmptyException) {
                applyAppTicket(ctx);
            }
            throw e;
        }
        if (req.getResponse().getCode() == Body.ErrCodeAppTicketInvalid) {
            applyAppTicket(ctx);
        }
    }

    private void applyAppTicket(Context ctx) {
        Config config = Config.ByCtx(ctx);
        ApplyAppTicketReq applyAppTicketReq = new ApplyAppTicketReq();
        applyAppTicketReq.setAppId(config.getAppSettings().getAppID());
        applyAppTicketReq.setAppSecret(config.getAppSettings().getAppSecret());
        Request<ApplyAppTicketReq, EmptyData> req = Request.newRequestByAuth(Constants.APPLY_APP_TICKET_PATH,
                "POST", applyAppTicketReq, new EmptyData());
        try {
            handle(ctx, req);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
