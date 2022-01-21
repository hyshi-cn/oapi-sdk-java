package ext.feishu.oapi.core.card;

import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.card.handler.Handler;
import ext.feishu.oapi.core.card.mode.HTTPCard;
import ext.feishu.oapi.core.model.OapiRequest;
import ext.feishu.oapi.core.model.OapiResponse;

public class Card {

    public static void setHandler(Config config, IHandler handler) {
        IHandler.Hub.register(config, handler);
    }

    public static OapiResponse handle(Config config, OapiRequest request) {
        Context context = new Context();
        config.withContext(context);
        OapiResponse response = new OapiResponse();
        HTTPCard httpCard = new HTTPCard(request, response);
        Handler.DEFAULT.handle(context, httpCard);
        return response;
    }
}
