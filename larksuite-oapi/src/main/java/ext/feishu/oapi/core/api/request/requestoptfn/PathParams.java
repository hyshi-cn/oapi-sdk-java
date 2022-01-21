package ext.feishu.oapi.core.api.request.requestoptfn;

import ext.feishu.oapi.core.api.request.RequestOpt;
import ext.feishu.oapi.core.api.request.RequestOptFn;

import java.util.Map;

public class PathParams implements RequestOptFn {

    private Map<String, Object> pathParams;

    public PathParams(Map<String, Object> pathParams) {
        this.pathParams = pathParams;
    }

    @Override
    public void fn(RequestOpt opt) {
        opt.setPathParams(pathParams);
    }
}
