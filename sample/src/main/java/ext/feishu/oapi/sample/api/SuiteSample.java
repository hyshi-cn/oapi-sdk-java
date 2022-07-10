package ext.feishu.oapi.sample.api;

import ext.feishu.oapi.core.AppSettings;
import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Domain;
import ext.feishu.oapi.core.api.request.Request;
import ext.feishu.oapi.core.api.response.Response;
import ext.feishu.oapi.core.utils.Jsons;
import ext.feishu.oapi.sample.config.Configs;
import ext.feishu.oapi.service.suite.v1.SuiteService;
import ext.feishu.oapi.service.suite.v1.model.*;
import ext.feishu.oapi.service.suite.v1.model.DocsApiMetaReqBody;
import ext.feishu.oapi.service.suite.v1.model.DocsApiMetaResult;
import ext.feishu.oapi.service.suite.v1.model.DocsApiSearchReqBody;
import ext.feishu.oapi.service.suite.v1.model.DocsApiSearchResult;
import ext.feishu.oapi.service.suite.v1.model.RequestDoc;

public class SuiteSample {
    // for Cutome APP（自建应用）
    private static final AppSettings appSettings = Config.getInternalAppSettingsByEnv();
    // config with redis store
    // private static final Config config = Configs.getConfigWithRedisStore("https://open.feishu.cn", appSettings);
    // private static final Config config = Configs.getConfig("https://open.feishu.cn", appSettings);
    private static final Config      config      = Configs.getConfig(Domain.FeiShu, appSettings);

    public static void main(String[] args) throws Exception {
        SuiteService service = new SuiteService(config);
        DocsApiMetaReqBody body = new DocsApiMetaReqBody();
        RequestDoc requestDoc = new RequestDoc();
        requestDoc.setDocsToken("1233");
        requestDoc.setDocsType("doc");
        body.setRequestDocs(new RequestDoc[]{requestDoc});
        Response<DocsApiMetaResult> response = service.getDocsApis().meta(body, Request.setUserAccessToken("u-n7IgWeg3R1WN4Af3JxGP5f")).execute();
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));

        DocsApiSearchReqBody body2 = new DocsApiSearchReqBody();
        body2.setDocsTypes(new String[]{"doc", "sheet"});
        Response<DocsApiSearchResult> response2 = service.getDocsApis().search(body2, Request.setUserAccessToken("u-n7IgWeg3R1WN4Af3JxGP5f")).execute();
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response2));
    }
}
