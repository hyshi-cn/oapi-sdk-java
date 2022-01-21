package ext.feishu.oapi.core.api.handler.subhandler;

import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.api.handler.SubHandler;
import ext.feishu.oapi.core.api.request.Request;

public class InitSubHandler implements SubHandler {
    @Override
    public <I, O> void handle(Context ctx, Request<I, O> req) throws Exception {
        Config conf = Config.ByCtx(ctx);
        req.init(conf.getDomain());
    }
}
