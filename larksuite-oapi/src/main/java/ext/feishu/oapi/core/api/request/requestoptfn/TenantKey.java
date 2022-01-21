package ext.feishu.oapi.core.api.request.requestoptfn;

import ext.feishu.oapi.core.api.request.RequestOpt;
import ext.feishu.oapi.core.api.request.RequestOptFn;

public class TenantKey implements RequestOptFn {

    private String tenantKey;

    public TenantKey(String tenantKey) {
        this.tenantKey = tenantKey;
    }

    @Override
    public void fn(RequestOpt opt) {
        opt.setTenantKey(tenantKey);
    }
}
