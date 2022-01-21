package ext.feishu.oapi.core.card.handler;

import ext.feishu.oapi.core.Constants;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.card.exception.NotFoundHandlerException;
import ext.feishu.oapi.core.card.handler.subhandler.HandleSubHandler;
import ext.feishu.oapi.core.card.handler.subhandler.InitSubHandler;
import ext.feishu.oapi.core.card.handler.subhandler.UnmarshalSubHandler;
import ext.feishu.oapi.core.card.handler.subhandler.ValidateSubHandler;
import ext.feishu.oapi.core.card.mode.HTTPCard;
import ext.feishu.oapi.core.model.OapiResponse;
import ext.feishu.oapi.core.utils.Jsons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Handler {
    public static final Handler DEFAULT = new Handler();
    private static final Logger log = LoggerFactory.getLogger(Handler.class);
    private final ISubHandler[] subHandlers;

    public Handler() {
        this.subHandlers = new ISubHandler[]{new InitSubHandler(), new ValidateSubHandler(),
                new UnmarshalSubHandler(), new HandleSubHandler()};
    }

    public void handle(Context context, HTTPCard httpCard) {
        OapiResponse response = httpCard.getResponse();
        response.setStatusCode(200);
        response.setContentType(Constants.APPLICATION_JSON);
        try {
            for (ISubHandler subHandler : this.subHandlers) {
                subHandler.handle(context, httpCard);
            }
            complement(httpCard, response);
        } catch (Exception e) {
            if (e instanceof NotFoundHandlerException) {
                log.info("card handle failed, header:{} - {}", httpCard.getHeader(), e.getMessage());
                response.setBody(String.format(OapiResponse.RESPONSE_FORMAT, e.getMessage()));
                return;
            }
            log.error("card handle failed, header:{} - {}", httpCard.getHeader(), e.getMessage(), e);
            response.setStatusCode(500);
            response.setBody(String.format(OapiResponse.RESPONSE_FORMAT, e.getMessage()));
        }
    }

    private void complement(HTTPCard httpCard, OapiResponse response) {
        if (Constants.URL_VERIFICATION.equals(httpCard.getType())) {
            response.setBody(String.format(OapiResponse.CHALLENGE_RESPONSE_FORMAT, httpCard.getChallenge()));
            return;
        }
        Object output = httpCard.getOutput();
        if (output == null) {
            response.setBody(String.format(OapiResponse.RESPONSE_FORMAT, "success"));
            return;
        }
        String body;
        if (output instanceof String) {
            body = (String) output;
        } else {
            body = Jsons.DEFAULT_GSON.toJson(output);
        }
        response.setBody(body);
    }
}
