package ext.feishu.oapi.core.api.response;

import ext.feishu.oapi.core.Context;
import ext.feishu.oapi.core.model.OapiHeader;

public class Response<Data> {
    private transient final Context context;

    private int    code;
    private String msg;
    private Error  error;
    private Data   data;

    public Response(Context context) {
        this.context = context;
        this.code = 0;
        this.msg = "";
    }

    public void setBody(Body body) {
        this.code = body.getCode();
        this.msg = body.getMsg();
        this.error = body.getError();
    }

    public OapiHeader getHeader() {
        return this.context.getHeader();
    }

    public String getRequestID() {
        return this.context.getRequestID();
    }

    public int getHTTPStatusCode() {
        return this.context.getHTTPStatusCode();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Error getError() {
        return error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "http status code:" + getHTTPStatusCode() + ", request id:" + getRequestID() + ", response:{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", error=" + error +
                ", data=omit..." +
                '}';
    }
}
