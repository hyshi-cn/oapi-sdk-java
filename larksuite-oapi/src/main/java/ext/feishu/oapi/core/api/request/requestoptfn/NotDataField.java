package ext.feishu.oapi.core.api.request.requestoptfn;

import ext.feishu.oapi.core.api.request.RequestOpt;
import ext.feishu.oapi.core.api.request.RequestOptFn;

public class NotDataField implements RequestOptFn {

    @Override
    public void fn(RequestOpt opt) {
        opt.setNotDataField(true);
    }
}
