package ext.feishu.oapi.example;

import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.event.DefaultHandler;
import ext.feishu.oapi.core.event.Event;
import ext.feishu.oapi.core.event.EventServlet;
import ext.feishu.oapi.core.utils.Jsons;
import ext.feishu.oapi.service.application.v1.ApplicationService;
import ext.feishu.oapi.service.application.v1.model.AppOpenEvent;
import ext.feishu.oapi.service.application.v1.model.AppStatusChangeEvent;
import ext.feishu.oapi.service.contact.v3.ContactService;
import ext.feishu.oapi.service.contact.v3.model.UserCreatedEvent;
import ext.feishu.oapi.service.contact.v3.model.UserUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.servlet.annotation.WebServlet;
import java.util.Map;

// "Developer Console" -> "Event Subscriptions", setting Request URL: https://domain/webhook/event
// Inherit EventServlet
@WebServlet("/webhook/event")
public class EventController extends EventServlet {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    // Provide the config instance through InitConfig.java and inject the config instance through dependency
    public EventController(Config config) {
        super(config);
    }

    @PostConstruct
    public void init() {

        Event.setTypeHandler(this.getConfig(), "app_open", new DefaultHandler() {
            @Override
            public void Handle(Context context, Map<String, Object> event) throws Exception {
                // Print the request ID of the request
                log.info("requestId:{}", context.getRequestID());
                // Print event
                log.info("event:{}", event);
            }
        });

        Event.setTypeHandler(this.getConfig(), "user.created_v2", (context, event) -> {
            log.info("requestId:{}", context.getRequestID());
            log.info("event:{}", event);
        });


        ApplicationService applicationService = new ApplicationService(this.getConfig());
        applicationService.setAppOpenEventHandler(new ApplicationService.AppOpenEventHandler() {
            @Override
            public void Handle(Context context, AppOpenEvent event) throws Exception {
                log.info("requestId:{}", context.getRequestID());
                log.info("event:{}", Jsons.DEFAULT_GSON.toJson(event));
            }
        });
        applicationService.setAppStatusChangeEventHandler(new ApplicationService.AppStatusChangeEventHandler() {
            @Override
            public void Handle(Context context, AppStatusChangeEvent event) throws Exception {
                log.info("requestId:{}", context.getRequestID());
                log.info("event:{}", Jsons.DEFAULT_GSON.toJson(event));
            }
        });


        ContactService contactService = new ContactService(this.getConfig());
        contactService.setUserCreatedEventHandler(new ContactService.UserCreatedEventHandler() {
            @Override
            public void Handle(Context context, UserCreatedEvent event) throws Exception {
                log.info("requestId:{}", context.getRequestID());
                log.info("event:{}", Jsons.DEFAULT_GSON.toJson(event));
            }
        });
        contactService.setUserUpdatedEventHandler(new ContactService.UserUpdatedEventHandler() {
            @Override
            public void Handle(Context context, UserUpdatedEvent event) throws Exception {
                log.info("requestId:{}", context.getRequestID());
                log.info("event:{}", Jsons.DEFAULT_GSON.toJson(event));
            }
        });
    }
}
