package ext.feishu.oapi.core.card.handler.subhandler;

import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Constants;
import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.card.handler.ISubHandler;
import ext.feishu.oapi.core.card.mode.Challenge;
import ext.feishu.oapi.core.card.mode.HTTPCard;
import ext.feishu.oapi.core.exception.TokenInvalidException;
import ext.feishu.oapi.core.utils.Jsons;

public class UnmarshalSubHandler implements ISubHandler {
    @Override
    public void handle(Context context, HTTPCard httpCard) throws Exception {
        Challenge challenge = Jsons.DEFAULT_GSON.fromJson(httpCard.getInput(), Challenge.class);
        httpCard.setType(challenge.getType());
        httpCard.setChallenge(challenge.getChallenge());
        if (Constants.URL_VERIFICATION.equals(httpCard.getType())) {
            if (!Config.ByCtx(context).getAppSettings().getVerificationToken().equals(challenge.getToken())) {
                throw new TokenInvalidException(challenge.getToken());
            }
        }
    }
}
