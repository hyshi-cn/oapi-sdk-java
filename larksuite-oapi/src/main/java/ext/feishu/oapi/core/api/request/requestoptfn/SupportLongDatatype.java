package ext.feishu.oapi.core.api.request.requestoptfn;

import ext.feishu.oapi.core.api.request.RequestOpt;
import ext.feishu.oapi.core.api.request.RequestOptFn;

public class SupportLongDatatype implements RequestOptFn {
    @Override
    public void fn(RequestOpt opt) {
        opt.setSupportLongDataType(true);
    }
}
