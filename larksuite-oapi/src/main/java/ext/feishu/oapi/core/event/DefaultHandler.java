package ext.feishu.oapi.core.event;

import ext.feishu.oapi.core.Context;

import java.util.Map;

public interface DefaultHandler {
    void Handle(Context context, Map<String, Object> event) throws Exception;
}
