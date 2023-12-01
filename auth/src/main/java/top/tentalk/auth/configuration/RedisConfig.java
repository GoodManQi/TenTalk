package top.tentalk.auth.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Configuration
public class RedisConfig {
    
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;;

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }

    @Bean(name = "basicRedisLockRegistry")
    public RedisLockRegistry redisLockRegistry() {
        // 设置分布式锁，且过期时间120秒
        // biz-lock为在redis内key的前缀，可以自定义
        // return new RedisLockRegistry(redisConnectionFactory, "biz-lock", 120000L);
        // 设置分布式锁，默认过期时间60秒
        return new RedisLockRegistry(redisConnectionFactory, "biz-lock");
    }
}
