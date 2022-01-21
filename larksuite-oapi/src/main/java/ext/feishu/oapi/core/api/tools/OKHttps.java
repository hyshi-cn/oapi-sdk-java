package ext.feishu.oapi.core.api.tools;

import ext.feishu.oapi.okhttp3_14.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OKHttps {

    public static final OkHttpClient defaultClient = create(15000, 30000, TimeUnit.MILLISECONDS);

    public static OkHttpClient create(long connectionTimeout, long socketTimeout, TimeUnit timeUnit) {
        return new OkHttpClient.Builder()
                .connectTimeout(connectionTimeout, timeUnit)
                .readTimeout(socketTimeout, timeUnit).build();
    }
}
