package ext.feishu.oapi.core.event.handler;

import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.event.model.HTTPEvent;

public interface ISubHandler {
    <E> void handle(Context context, HTTPEvent httpEvent) throws Exception;
}
