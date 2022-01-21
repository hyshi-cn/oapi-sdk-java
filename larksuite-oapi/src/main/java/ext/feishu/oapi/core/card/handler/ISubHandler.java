package ext.feishu.oapi.core.card.handler;

import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.card.mode.HTTPCard;

public interface ISubHandler {
    void handle(Context context, HTTPCard httpCard) throws Exception;
}
