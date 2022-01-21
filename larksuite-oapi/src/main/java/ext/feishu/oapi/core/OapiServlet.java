package ext.feishu.oapi.core;

import ext.feishu.oapi.core.model.OapiRequest;
import ext.feishu.oapi.core.model.OapiResponse;
import ext.feishu.oapi.core.utils.Servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class OapiServlet extends HttpServlet implements IWebHookHandler {

    private final Config config;

    public OapiServlet(Config config) {
        this.config = config;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OapiRequest request = Servlets.toRequest(req);
        OapiResponse response = handle(this.config, request);
        Servlets.writeResponse(response, resp);
    }

    public Config getConfig() {
        return config;
    }
}
