package ext.feishu.oapi.core.event.v1;

import ext.feishu.oapi.core.AppType;
import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.Keys;
import ext.feishu.oapi.core.event.Event;
import ext.feishu.oapi.core.event.IHandler;

import java.util.concurrent.TimeUnit;

public class AppTicketEventHandler implements IHandler<AppTicketEvent> {

    public static void register(Config config) {
        if (config.getAppSettings().getAppType().equals(AppType.Internal)) {
            return;
        }
        Event.setTypeHandler(config, "app_ticket", new AppTicketEventHandler());
    }

    @Override
    public AppTicketEvent getEvent() {
        return new AppTicketEvent();
    }

    @Override
    public void Handle(Context context, AppTicketEvent event) throws Exception {
        Config config = Config.ByCtx(context);
        config.getStore().put(Keys.appTicketKey(config.getAppSettings().getAppID()), event.getEvent().getAppTicket(), 12, TimeUnit.HOURS);
    }
}
