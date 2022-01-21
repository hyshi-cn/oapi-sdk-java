package ext.feishu.oapi.core.event.handler.subhandler;

import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Constants;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.event.IHandler;
import ext.feishu.oapi.core.event.exception.NotFoundHandlerException;
import ext.feishu.oapi.core.event.handler.ISubHandler;
import ext.feishu.oapi.core.event.model.HTTPEvent;
import ext.feishu.oapi.core.utils.Jsons;

public class HandleSubHandler implements ISubHandler {

    @Override
    public <E> void handle(Context context, HTTPEvent httpEvent) throws Exception {
        if (Constants.URL_VERIFICATION.equals(httpEvent.getType())) {
            return;
        }
        Config config = Config.ByCtx(context);
        IHandler<E> handler = IHandler.Hub.GetEventHandler(config, httpEvent.getEventType());
        if (handler == null) {
            throw new NotFoundHandlerException(httpEvent.getEventType());
        }
        E e = handler.getEvent();
        e = (E) Jsons.DEFAULT_GSON.fromJson(httpEvent.getInput().trim(), e.getClass());
        handler.Handle(context, e);
    }
}
