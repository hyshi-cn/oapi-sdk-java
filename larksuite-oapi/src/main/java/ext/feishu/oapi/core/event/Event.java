package ext.feishu.oapi.core.event;

import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.event.handler.Handler;
import ext.feishu.oapi.core.event.model.HTTPEvent;
import ext.feishu.oapi.core.event.v1.AppTicketEventHandler;
import ext.feishu.oapi.core.model.OapiRequest;
import ext.feishu.oapi.core.model.OapiResponse;

public class Event {
    private static volatile boolean inited = false;

    public static void setTypeHandler(Config config, String eventType, IHandler handler) {
        IHandler.Hub.setEventTypeHandler(config, eventType, handler);
    }

    public static void setTypeHandler(Config config, String eventType, DefaultHandler handler) {
        IHandler.Hub.setEventTypeHandler(config, eventType, handler);
    }

    private static void init(Config config) {
        if (!inited) {
            synchronized (Event.class) {
                if (!inited) {
                    AppTicketEventHandler.register(config);
                    inited = true;
                }
            }
        }
    }

    public static OapiResponse handle(Config config, OapiRequest request) {
        init(config);
        Context context = new Context();
        config.withContext(context);
        OapiResponse response = new OapiResponse();
        HTTPEvent httpEvent = new HTTPEvent(request, response);
        Handler.DEFAULT.handle(context, httpEvent);
        return response;
    }
}
