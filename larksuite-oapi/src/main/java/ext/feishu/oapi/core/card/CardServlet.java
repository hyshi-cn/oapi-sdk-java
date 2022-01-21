package ext.feishu.oapi.core.card;

import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.OapiServlet;
import ext.feishu.oapi.core.model.OapiRequest;
import ext.feishu.oapi.core.model.OapiResponse;

public class CardServlet extends OapiServlet {

    public CardServlet(Config config) {
        super(config);
    }

    @Override
    public OapiResponse handle(Config config, OapiRequest req) {
        return Card.handle(config, req);
    }
}
