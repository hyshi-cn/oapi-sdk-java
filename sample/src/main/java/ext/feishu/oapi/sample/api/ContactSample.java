package ext.feishu.oapi.sample.api;

import ext.feishu.oapi.core.AppSettings;
import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Domain;
import ext.feishu.oapi.core.api.response.EmptyData;
import ext.feishu.oapi.core.api.response.Response;
import ext.feishu.oapi.core.utils.Jsons;
import ext.feishu.oapi.sample.config.Configs;
import ext.feishu.oapi.service.contact.v3.ContactService;
import ext.feishu.oapi.service.contact.v3.model.DepartmentListResult;
import ext.feishu.oapi.service.contact.v3.model.User;
import ext.feishu.oapi.service.contact.v3.model.UserDeleteReqBody;
import ext.feishu.oapi.service.contact.v3.model.UserPatchResult;

public class ContactSample {
    // for Cutome APP（自建应用）
    private static final AppSettings appSettings = Config.getInternalAppSettingsByEnv();
    // config with redis store
    // private static final Config config = Configs.getConfigWithRedisStore("https://open.feishu.cn", appSettings);
    // private static final Config config = Configs.getConfig("https://open.feishu.cn", appSettings);
    private static final Config      config      = Configs.getConfig(Domain.FeiShu, appSettings);

    private static void patch(ContactService contactService) throws Exception {
        User user = new User();
        user.setName("rename");
        Response<UserPatchResult> response = contactService.getUsers().patch(user)
                .setUserId("77bbc392").setUserIdType("user_id").execute();
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
    }

    private static void delete(ContactService contactService) throws Exception {
        UserDeleteReqBody userDeleteReq = new UserDeleteReqBody();
        Response<EmptyData> response = contactService.getUsers().delete(userDeleteReq).setUserIdType("user_id").setUserId("13cgad15").execute();
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
    }

    private static void departmentList(ContactService contactService) throws Exception {
        Response<DepartmentListResult> response = contactService.getDepartments().list().setFetchChild(true).execute();
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
    }

    public static void main(String[] args) throws Exception {
        ContactService contactService = new ContactService(config);
        patch(contactService);
        // delete(contactService);
        //departmentList(contactService);

    }
}
