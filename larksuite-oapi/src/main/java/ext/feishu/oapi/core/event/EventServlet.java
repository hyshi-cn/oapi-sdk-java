package ext.feishu.oapi.core.event;

import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.OapiServlet;
import ext.feishu.oapi.core.model.OapiRequest;
import ext.feishu.oapi.core.model.OapiResponse;

public class EventServlet extends OapiServlet {

    public EventServlet(Config config) {
        super(config);
    }

    @Override
    public OapiResponse handle(Config config, OapiRequest req) {
        return Event.handle(config, req);
    }
}
