package ext.feishu.oapi.core.card.handler.subhandler;

import ext.feishu.oapi.core.Constants;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.card.handler.ISubHandler;
import ext.feishu.oapi.core.card.mode.HTTPCard;
import ext.feishu.oapi.core.card.mode.Header;
import ext.feishu.oapi.core.model.OapiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitSubHandler implements ISubHandler {

    private static final Logger log = LoggerFactory.getLogger(InitSubHandler.class);

    @Override
    public void handle(Context context, HTTPCard httpCard) throws Exception {
        OapiRequest request = httpCard.getRequest();
        context.set(Constants.HTTP_HEADER, request.getHeader());
        String timestamp = request.getHeader().getFirstValue(Header.X_LARK_REQUEST_TIMESTAMP);
        String nonce = request.getHeader().getFirstValue(Header.X_LARK_REQUEST_NONCE);
        String signature = request.getHeader().getFirstValue(Header.X_LARK_SIGNATURE);
        String refreshToken = request.getHeader().getFirstValue(Header.X_REFRESH_TOKEN);
        Header header = new Header(timestamp, nonce, signature, refreshToken);
        httpCard.setHeader(header);
        httpCard.setInput(httpCard.getRequest().getBody());
        log.debug("[init] header:{}, card: {}", header, httpCard.getInput());
    }
}
