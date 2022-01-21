package ext.feishu.oapi.core.api.request.requestoptfn;

import ext.feishu.oapi.core.api.request.RequestOpt;
import ext.feishu.oapi.core.api.request.RequestOptFn;

public class UserAccessToken implements RequestOptFn {

    private String userAccessToken;

    public UserAccessToken(String userAccessToken) {
        this.userAccessToken = userAccessToken;
    }

    @Override
    public void fn(RequestOpt opt) {
        opt.setUserAccessToken(userAccessToken);
    }
}
