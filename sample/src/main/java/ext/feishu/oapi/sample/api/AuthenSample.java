package ext.feishu.oapi.sample.api;

import ext.feishu.oapi.core.AppSettings;
import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.api.BatchReqCall;
import ext.feishu.oapi.core.api.ReqCallResult;
import ext.feishu.oapi.core.api.request.Request;
import ext.feishu.oapi.core.api.response.Response;
import ext.feishu.oapi.core.utils.Jsons;
import ext.feishu.oapi.sample.config.Configs;
import ext.feishu.oapi.service.authen.v1.AuthenService;
import ext.feishu.oapi.service.authen.v1.model.AuthenAccessTokenReqBody;
import ext.feishu.oapi.service.authen.v1.model.AuthenRefreshAccessTokenReqBody;
import ext.feishu.oapi.service.authen.v1.model.UserAccessTokenInfo;
import ext.feishu.oapi.service.authen.v1.model.UserInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AuthenSample {

    // for Cutome APP（自建应用）
    private static final AppSettings appSettings = Config.getInternalAppSettingsByEnv();
    // config with redis store
    private static final Config      config      = Configs.getConfigWithRedisStore("https://open.feishu.cn", appSettings);
    // private static final Config config = Configs.getConfig("https://open.feishu.cn", appSettings);
    // private static final Config config = Configs.getConfig(Domain.FeiShu, appSettings);

    public static void main(String[] args) throws Exception {
        testAccessToken();
        //testFreshAccessToken();
        //testUserInfo();
    }

    private static void testAccessToken() throws Exception {
        AuthenService service = new AuthenService(config);
        AuthenAccessTokenReqBody body = new AuthenAccessTokenReqBody();
        body.setGrantType("authorization_code");
        body.setCode("sNmRfrs5OZbQpc9fZTqTre");
        AuthenService.AuthenAccessTokenReqCall reqCall = service.getAuthens().accessToken(body);
        Response<UserAccessTokenInfo> response = reqCall.execute();
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
        System.out.println(Jsons.DEFAULT_GSON.toJson(response.getData()));
        System.out.println(response.getRequestID());
    }

    private static void testFreshAccessToken() throws Exception {
        AuthenService service = new AuthenService(config);
        AuthenRefreshAccessTokenReqBody body = new AuthenRefreshAccessTokenReqBody();
        body.setGrantType("refresh_token");
        body.setRefreshToken("[refresh_token]");
        AuthenService.AuthenRefreshAccessTokenReqCall reqCall = service.getAuthens().refreshAccessToken(body);
        Response<UserAccessTokenInfo> response = reqCall.execute();
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
        System.out.println(response.getRequestID());
    }

    private static void testUserInfo() throws Exception {
        AuthenService service = new AuthenService(config);
        AuthenService.AuthenUserInfoReqCall reqCall = service.getAuthens().userInfo(Request.setUserAccessToken("u-7B5D40vAJryhYyf6eigcxf"));
        Response<UserInfo> response = reqCall.execute();
        System.out.println(Jsons.DEFAULT_GSON.toJson(response.getData()));
        System.out.println(response.getRequestID());
    }

    private static void batchAccessToken() {
        AuthenService service = new AuthenService(config);
        AuthenAccessTokenReqBody body = new AuthenAccessTokenReqBody();
        body.setCode("[code]");
        body.setGrantType("authorization_code");
        AuthenService.AuthenAccessTokenReqCall reqCall1 = service.getAuthens().accessToken(body);
        AuthenService.AuthenAccessTokenReqCall reqCall2 = service.getAuthens().accessToken(body);
        BatchReqCall<AuthenAccessTokenReqBody, UserAccessTokenInfo> batchReqCall = new BatchReqCall<>(reqCall1, reqCall2);
        try {
            List<ReqCallResult<AuthenAccessTokenReqBody, UserAccessTokenInfo>> reqCallResults = batchReqCall.call();
            for (ReqCallResult<AuthenAccessTokenReqBody, UserAccessTokenInfo> reqCallResult : reqCallResults) {
                if (reqCallResult.getException() != null) {
                    System.out.println(reqCallResult.getException().getMessage());
                    return;
                }
                System.out.println(reqCallResult.getResponse().getRequestID());
                System.out.println(reqCallResult.getResponse().getHTTPStatusCode());
                System.out.println(Jsons.DEFAULT_GSON.toJson(reqCallResult.getResponse()));
            }
        } catch (ExecutionException | InterruptedException apiException) {
            apiException.printStackTrace();
        }
    }
}
