package ext.feishu.oapi.sample.api;

import ext.feishu.oapi.core.AppSettings;
import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Domain;
import ext.feishu.oapi.core.api.response.EmptyData;
import ext.feishu.oapi.core.api.response.Response;
import ext.feishu.oapi.core.utils.Jsons;
import ext.feishu.oapi.sample.config.Configs;
import ext.feishu.oapi.service.calendar.v4.CalendarService;

public class CalendarSample {

    // for Cutome APP（自建应用）
    private static final AppSettings appSettings = Config.getInternalAppSettingsByEnv();
    // config with redis store
    // private static final Config config = Configs.getConfigWithRedisStore("https://open.feishu.cn", appSettings);
    // private static final Config config = Configs.getConfig("https://open.feishu.cn", appSettings);
    private static final Config      config      = Configs.getConfig(Domain.FeiShu, appSettings);

    public static void main(String[] args) throws Exception {
        CalendarService calendarService = new CalendarService(config);
        Response<EmptyData> response = calendarService.getCalendarAcls().delete().setCalendarId("CalendarId").setAclId("AclId").execute();
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
    }
}
