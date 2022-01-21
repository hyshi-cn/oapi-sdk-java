package ext.feishu.oapi.core.api;

import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.api.handler.Handler;
import ext.feishu.oapi.core.api.request.Request;
import ext.feishu.oapi.core.api.response.Response;

public class Api {
    public static <I, O> Response<O> send(Config config, Request<I, O> request) throws Exception {
        Context context = new Context();
        config.withContext(context);
        request.withContext(context);
        Handler.DEFAULT.handle(context, request);
        return request.getResponse();
    }
}
