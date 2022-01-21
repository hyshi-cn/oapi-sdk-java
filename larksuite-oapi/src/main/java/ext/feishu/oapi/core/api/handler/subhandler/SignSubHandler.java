package ext.feishu.oapi.core.api.handler.subhandler;

import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.api.handler.AccessToken;
import ext.feishu.oapi.core.api.handler.SubHandler;
import ext.feishu.oapi.core.api.request.Request;

public class SignSubHandler implements SubHandler {

    @Override
    public <I, O> void handle(Context ctx, Request<I, O> req) throws Exception {
        switch (req.getAccessTokenType()) {
            case App:
                AccessToken.signApp(ctx, req.getHttpRequestBuilder());
                break;
            case Tenant:
                AccessToken.signTenant(ctx, req.getHttpRequestBuilder());
                break;
            case User:
                AccessToken.signUser(ctx, req.getHttpRequestBuilder());
                break;
        }
    }

}
