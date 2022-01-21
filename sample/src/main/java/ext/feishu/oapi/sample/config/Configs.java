package ext.feishu.oapi.sample.config;

import ext.feishu.oapi.core.AppSettings;
import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.DefaultStore;
import ext.feishu.oapi.core.Domain;

public class Configs {

    public static Config getConfig(Domain domain, AppSettings appSettings) {
        return new Config(domain, appSettings, new DefaultStore());
    }

    public static Config getConfig(String domain, AppSettings appSettings) {
        return new Config(domain, appSettings, new DefaultStore());
    }

    /**
     * by redis store access token
     *
     * @param domain
     * @param appSettings
     * @return
     */
    public static Config getConfigWithRedisStore(Domain domain, AppSettings appSettings) {
        return new Config(domain, appSettings, new RedisStore());
    }

    public static Config getConfigWithRedisStore(String domain, AppSettings appSettings) {
        return new Config(domain, appSettings, new RedisStore());
    }

}
