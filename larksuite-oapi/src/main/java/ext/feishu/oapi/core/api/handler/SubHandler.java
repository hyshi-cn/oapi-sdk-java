package ext.feishu.oapi.core.api.handler;

import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.api.request.Request;

public interface SubHandler {
    <I, O> void handle(Context ctx, Request<I, O> req) throws Exception;
}
