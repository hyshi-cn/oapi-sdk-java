package ext.feishu.oapi.core.api.exception;

import ext.feishu.oapi.core.api.response.Response;

public class AccessTokenObtainException extends RuntimeException {
    private Response response;

    public AccessTokenObtainException(String messagePrefix, Response response) {
        super(messagePrefix + response.toString());
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
