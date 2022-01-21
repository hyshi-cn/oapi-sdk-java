package ext.feishu.oapi.example;

import ext.feishu.oapi.core.AppSettings;
import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Domain;
import ext.feishu.oapi.example.config.Configs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitConfig {

    private static final Logger log = LoggerFactory.getLogger(InitConfig.class);

    @Bean
    public Config config() {

        // for Cutome APP（自建应用）
        AppSettings appSettings = Config.getInternalAppSettingsByEnv();
        // config with redis store
        // Config config = Configs.getConfigWithRedisStore(Domain.FeiShu, appSettings);
        // Config config = Configs.getConfig("https://open.feishu.cn", appSettings);
        Config config = Configs.getConfig(Domain.FeiShu, appSettings);
        log.debug("config:{}", config);
        return config;

    }
}
