package top.tentalk.auth.configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
@EnableCaching
public class RdsCacheConfiguration implements CachingConfigurer {

    /**
     * applicationName 应用名称
     */
    @Value("${spring.application.name}")
    private String applicationName;

    private RedisCacheConfiguration redisCacheConfiguration(String prefix, Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(ttl)
                .prefixCacheNameWith(prefix)
                // 允许缓存null，可以避免缓存穿透
                // .disableCachingNullValues()
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Primary
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        String prefix = StringUtils.removeEnd(applicationName, "-api") + ":";
        return RedisCacheManager.builder(factory)
                .cacheDefaults(this.redisCacheConfiguration(prefix, Duration.ofMinutes(10)))
                .withInitialCacheConfigurations(this.cacheConfigurationMap(prefix))
                .build();
    }

    @Bean
    public CacheManager commonAuthCacheManager(RedisConnectionFactory factory) {
        String prefix = "common-auth:";
        return RedisCacheManager.builder(factory)
                .cacheDefaults(this.redisCacheConfiguration(prefix, Duration.ofMinutes(10)))
                .withInitialCacheConfigurations(this.cacheConfigurationMap(prefix))
                .build();
    }

    /**
     * redis缓存管理器配置列表； 可以根据业务需要配置不同的过期时间；
     */
    private Map<String, RedisCacheConfiguration> cacheConfigurationMap(String prefix) {
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
        configurationMap.put("m5", this.redisCacheConfiguration(prefix, Duration.ofMinutes(5)));
        configurationMap.put("m10", this.redisCacheConfiguration(prefix, Duration.ofMinutes(10)));
        configurationMap.put("m20", this.redisCacheConfiguration(prefix, Duration.ofMinutes(20)));
        configurationMap.put("m30", this.redisCacheConfiguration(prefix, Duration.ofMinutes(30)));
        return configurationMap;
    }
}
