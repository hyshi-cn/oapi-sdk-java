package ext.feishu.oapi.core.api;

import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.api.request.Request;
import ext.feishu.oapi.core.api.response.Response;

public class DefaultReqCall<I, O> extends ReqCaller<I, O> {

    private final Config        config;
    private final Request<I, O> request;

    public DefaultReqCall(Config config, Request<I, O> request) {
        this.config = config;
        this.request = request;
    }

    @Override
    public Response<O> execute() throws Exception {
        return Api.send(this.getConfig(), this.getRequest());
    }

    public Config getConfig() {
        return config;
    }

    public Request<I, O> getRequest() {
        return request;
    }
}
