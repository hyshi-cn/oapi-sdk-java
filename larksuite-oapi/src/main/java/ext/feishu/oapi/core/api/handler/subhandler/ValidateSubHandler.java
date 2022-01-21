package ext.feishu.oapi.core.api.handler.subhandler;


import ext.feishu.oapi.core.AppType;
import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.api.AccessTokenType;
import ext.feishu.oapi.core.api.exception.AccessTokenTypeInvalidException;
import ext.feishu.oapi.core.api.exception.TenantKeyEmptyException;
import ext.feishu.oapi.core.api.exception.UserAccessTokenKeyEmptyException;
import ext.feishu.oapi.core.api.handler.SubHandler;
import ext.feishu.oapi.core.api.request.Request;
import ext.feishu.oapi.core.utils.Strings;

public class ValidateSubHandler implements SubHandler {
    @Override
    public <I, O> void handle(Context ctx, Request<I, O> req) throws Exception {
        if (req.getAccessTokenType() == AccessTokenType.None) {
            return;
        }
        if (!req.getAccessTokenTypeSet().contains(req.getAccessTokenType())) {
            throw new AccessTokenTypeInvalidException();
        }
        Config conf = Config.ByCtx(ctx);
        if (conf.getAppSettings().getAppType() == AppType.ISV) {
            if (req.getAccessTokenType() == AccessTokenType.Tenant && Strings.isEmpty(req.getTenantKey())) {
                throw new TenantKeyEmptyException();
            }
        }
        if (req.getAccessTokenType() == AccessTokenType.User && Strings.isEmpty(req.getUserAccessToken())) {
            throw new UserAccessTokenKeyEmptyException();
        }
    }
}
