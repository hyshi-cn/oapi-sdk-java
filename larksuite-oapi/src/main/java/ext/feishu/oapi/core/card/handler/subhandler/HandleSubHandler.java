package ext.feishu.oapi.core.card.handler.subhandler;

import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Constants;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.card.IHandler;
import ext.feishu.oapi.core.card.exception.NotFoundHandlerException;
import ext.feishu.oapi.core.card.handler.ISubHandler;
import ext.feishu.oapi.core.card.mode.Card;
import ext.feishu.oapi.core.card.mode.HTTPCard;
import ext.feishu.oapi.core.utils.Jsons;

public class HandleSubHandler implements ISubHandler {
    @Override
    public void handle(Context context, HTTPCard httpCard) throws Exception {
        if (Constants.URL_VERIFICATION.equals(httpCard.getType())) {
            return;
        }
        Config config = Config.ByCtx(context);
        IHandler handler = IHandler.Hub.getHandler(config);
        if (handler == null) {
            throw new NotFoundHandlerException();
        }
        Card card = Jsons.DEFAULT_GSON.fromJson(httpCard.getInput().trim(), Card.class);
        httpCard.setOutput(handler.handle(context, card));
    }
}
