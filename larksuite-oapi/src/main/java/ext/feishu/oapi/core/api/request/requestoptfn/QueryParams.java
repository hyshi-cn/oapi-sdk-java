package ext.feishu.oapi.core.api.request.requestoptfn;

import ext.feishu.oapi.core.api.request.RequestOpt;
import ext.feishu.oapi.core.api.request.RequestOptFn;

import java.util.Map;

public class QueryParams implements RequestOptFn {
    private Map<String, Object> queryParams;

    public QueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
    }

    @Override
    public void fn(RequestOpt opt) {
        opt.setQueryParams(queryParams);
    }
}
