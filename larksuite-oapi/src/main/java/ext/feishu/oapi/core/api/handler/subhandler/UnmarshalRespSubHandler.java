package ext.feishu.oapi.core.api.handler.subhandler;

import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.api.handler.SubHandler;
import ext.feishu.oapi.core.api.request.Request;
import ext.feishu.oapi.core.api.response.Body;
import ext.feishu.oapi.core.api.tools.IOs;
import ext.feishu.oapi.okhttp3_14.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.Objects;

public class UnmarshalRespSubHandler implements SubHandler {

    private static final Logger log = LoggerFactory.getLogger(UnmarshalRespSubHandler.class);

    @Override
    public <I, O> void handle(Context ctx, Request<I, O> req) throws Exception {
        Response httpResponse = req.getHttpResponse();
        if (req.isResponseStreamReal()) {
            try {
                if (req.getOutput() instanceof OutputStream) {
                    IOs.copy(Objects.requireNonNull(httpResponse.body()).byteStream(), (OutputStream) req.getOutput());
                    req.getResponse().setData(req.getOutput());
                } else {
                    throw new UnsupportedOperationException("when response is stream, request.output instanceof OutputStream");
                }
            } finally {
                Objects.requireNonNull(httpResponse.body()).close();
            }
            return;
        }
        String body = Objects.requireNonNull(httpResponse.body()).string();
        log.debug("[unmarshalResponse] request:{}, response body:{}", req.toString(), body);
        unmarshalResp(req, body);
    }

    public <I, O> void unmarshalResp(Request<I, O> req, String body) {
        body = body.trim();
        Body b = req.getGson().fromJson(body, Body.class);
        req.setRetry(b.retry());
        req.getResponse().setBody(b);
        if (b.getCode() != Body.ErrCodeOk) {
            return;
        }
        if (req.isNotDataField()) {
            Object output = req.getGson().fromJson(body, req.getOutput().getClass());
            req.getResponse().setData((O) output);
        } else {
            if (b.getData() == null || b.getData().size() == 0) {
                return;
            }
            String data = req.getGson().toJson(b.getData());
            Object output = req.getGson().fromJson(data, req.getOutput().getClass());
            req.getResponse().setData((O) output);
        }
    }
}
