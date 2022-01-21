package ext.feishu.oapi.core;

import ext.feishu.oapi.core.model.OapiRequest;
import ext.feishu.oapi.core.model.OapiResponse;

public interface IWebHookHandler {
    OapiResponse handle(Config config, OapiRequest req);
}
