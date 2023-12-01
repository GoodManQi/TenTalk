package top.tentalk.auth.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.tentalk.auth.annotation.RequiredLogin;
import top.tentalk.auth.constants.RequestHeaderConstants;
import top.tentalk.auth.utils.JWTUtil;
import top.tentalk.common.constants.JwtClaimConstants;
import top.tentalk.common.constants.TokenType;

import java.util.Map;

@Component
@Slf4j
public class JwtAuthInterceptor implements HandlerInterceptor {

    private static final String loginPrefix = "user:login:token:";

    private static final String thisUserRolePrefix = "this:user:role:";
    private static final String jwtLockPrefix = "jwt:user:token:";

    @Value("${ssv.userLoginAppId}")
    private String jwtSecret;

    @Value("${jwt.intervalTime}")
    private int intervalTime;

    @Value("${jwt.expireTime}")
    private int expireTime;

    @Value("${jwt.endTime}")
    private int endTime;

    @Resource
    private transient StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request,
                             @Nonnull HttpServletResponse response,
                             @Nonnull Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 优先使用方法上的权限配置
        RequiredLogin anno;
        if (((HandlerMethod) handler).getMethod().isAnnotationPresent(RequiredLogin.class)) {
            anno = ((HandlerMethod) handler).getMethodAnnotation(RequiredLogin.class);
        } else {
            anno = ((HandlerMethod) handler).getBeanType().getAnnotation(RequiredLogin.class);
        }
        if (anno == null) {
            return true;
        }


        return checkToken(request,response);
    }


    /**
     * 对jwt token进行校验
     */
    public boolean checkToken(HttpServletRequest request, HttpServletResponse response) {
        // 头部获取token进行判断
        String token = request.getHeader(RequestHeaderConstants.AUTH_TOKEN);
        // 如果用户信息为空,说明未登录,跳转登陆页
        if (StringUtils.isBlank(token)) {
            log.info("jwt token is empty");
            return false;
        }
        log.info("jwt token: {}", token);

        // JWT校验token
        String secret = DigestUtils.md5DigestAsHex(jwtSecret.getBytes());
        DecodedJWT jwt;
        try {
            jwt = JWTUtil.verifyToken(token, TokenType.LOGIN_USER, secret);
        } catch (JWTVerificationException e) {
            log.warn("jwt token verify failed: {}", e.getMessage());
            return false;
        }

        // 获取jwt token中的用户信息
        Map<String, Claim> claims = jwt.getClaims();
        Long userId = claims.get(JwtClaimConstants.USER_ID).asLong();
        log.info("get userId from jwt, userId: {}", userId);

        if (userId > 0) {
            // 将用户id写入request
            request.setAttribute(JwtClaimConstants.USER_ID, userId);
            MDC.put("uid", String.valueOf(userId));
        } else {
            return false;
        }
//        Integer userType = claims.get(JwtClaimConstants.USER_TYPE).asInt();
//        request.setAttribute(JwtClaimConstants.USER_TYPE, userType);

        // 从redis内读取用户当前的活跃token，并比对是否一致
//        Long tokenId = claims.get(JwtClaimConstants.TOKEN_ID).asLong();
//        if (tokenId <= 0) {
//            return false;
//        }
//        log.info("get tokenId from jwt, tokenId: {}", tokenId);
//        String redisTokenIdStr = stringRedisTemplate.opsForValue().get(loginPrefix + userId);
//        log.info("redisTokenIdStr: {}", redisTokenIdStr);
//        if (StringUtils.isEmpty(redisTokenIdStr)) {
//            return false;
//        }
//        Long redisTokenId = Long.valueOf(redisTokenIdStr);
//        if (redisTokenId <= 0 || !redisTokenId.equals(tokenId)) {
//            log.info("redisTokenId: {}, tokenId: {}", redisTokenId, tokenId);
//            return false;
//        }

        // 将jwt数据写入request
        request.setAttribute(JwtClaimConstants.JWT_DATA, jwt);
        return true;
    }

    @Override
    public void postHandle(@Nonnull HttpServletRequest request,
                           @Nonnull HttpServletResponse response,
                           @Nonnull Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request,
                                @Nonnull HttpServletResponse response,
                                @Nonnull Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
