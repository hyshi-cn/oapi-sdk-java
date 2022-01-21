package ext.feishu.oapi.core.api.handler.subhandler;

import ext.feishu.oapi.core.Constants;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.api.exception.ResponseInvalidException;
import ext.feishu.oapi.core.api.handler.SubHandler;
import ext.feishu.oapi.core.api.request.Request;
import ext.feishu.oapi.core.utils.Strings;
import ext.feishu.oapi.okhttp3_14.ResponseBody;

public class ValidateRespSubHandler implements SubHandler {
    @Override
    public <I, O> void handle(Context ctx, Request<I, O> req) throws Exception {
        String contentType = req.getHttpResponse().header(Constants.CONTENT_TYPE);
        if (req.isResponseStream()) {
            if (req.getHttpResponse().code() == 200) {
                req.setResponseStreamReal(true);
                return;
            }
            if (contentType != null && contentType.contains(Constants.APPLICATION_JSON)) {
                req.setResponseStreamReal(false);
                return;
            }
            if (req.getHttpResponse().code() != 200) {
                throw new ResponseInvalidException("response is stream, but status code:" + req.getHttpResponse().code());
            }
            return;
        }
        if (Strings.isEmpty(contentType) || !contentType.contains(Constants.APPLICATION_JSON)) {
            ResponseBody responseBody = req.getHttpResponse().body();
            String body = responseBody == null ? "" : responseBody.string();
            throw new ResponseInvalidException("request id:" + ctx.getRequestID() + ", status code:" + req.getHttpResponse().code() + ", content-type:"
                    + contentType + ", not is:" + Constants.APPLICATION_JSON
                    + ", body:" + body);
        }
    }
}
