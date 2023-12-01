package top.tentalk.auth.utils;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import top.tentalk.common.constants.ErrorCode;
import top.tentalk.common.constants.JwtClaimConstants;
import top.tentalk.common.constants.TokenType;
import top.tentalk.common.exception.BizException;
import top.tentalk.common.utils.DateUtil;

@Slf4j
public class JWTUtil {

    public static final String ISSUER = "top.tentalk";

    /**
     * JWT生成Token.
     *
     * @param userId 绑定的用户id
     * @param type   token类型
     * @param secret 加密使用的密钥
     */
    public static String createTokenWithDate(Long userId, TokenType type, String secret, Date date) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    //声明token的颁发者
                    .withIssuer(ISSUER)
                    //有效期
                    .withExpiresAt(date)
                    //添加token类型
                    .withSubject(type.name())
                    //添加用户id
                    .withClaim(JwtClaimConstants.USER_ID, userId)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            log.warn(exception.getMessage());
            throw (BizException) new BizException(ErrorCode.INTERNAL_ERROR).initCause(exception);
        }
    }

    /**
     * JWT生成Token.
     *
     * @param userId 绑定的用户id
     * @param type   token类型
     * @param secret 加密使用的密钥
     */
    public static String createToken(Long userId, TokenType type, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    //声明token的颁发者
                    .withIssuer(ISSUER)
                    //有效期为一个小时
                    .withExpiresAt(DateUtil.addHours(new Date(), 1))
                    //添加token类型
                    .withSubject(type.name())
                    //添加用户id
                    .withClaim("userId", userId)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            log.warn(exception.getMessage());
            throw (BizException) new BizException(ErrorCode.INTERNAL_ERROR).initCause(exception);
        }
    }

    /**
     * JWT生成Token.
     *
     * @param claim  token携带的信息
     * @param type   token类型
     * @param secret 加密使用的密钥
     */
    public static String createToken(Map<String, Object> claim, TokenType type, String secret, Date tokenExpireDate) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    //声明token的颁发者
                    .withIssuer(ISSUER)
                    //有效期为一个小时
                    .withExpiresAt(tokenExpireDate)
                    //添加token类型
                    .withSubject(type.name())
                    //添加用户id
                    .withClaim(JwtClaimConstants.USER_ID, (long) claim.get(JwtClaimConstants.USER_ID))
                    .withClaim(JwtClaimConstants.TOKEN_ID, (long) claim.get(JwtClaimConstants.TOKEN_ID))
                    .withClaim(JwtClaimConstants.REFRESH_TIMES, Long.parseLong(String.valueOf(claim.get(JwtClaimConstants.REFRESH_TIMES))))
                    .withClaim(JwtClaimConstants.CREATED_AT, (long) claim.get(JwtClaimConstants.CREATED_AT))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            log.warn(exception.getMessage());
            throw (BizException) new BizException(ErrorCode.INTERNAL_ERROR).initCause(exception);
        }
    }

    /**
     * 解密Token
     *
     * @param secret 加密使用的密钥
     */
    public static DecodedJWT verifyToken(String token, TokenType type, String secret) {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withSubject(type.name()).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw (BizException) new BizException(ErrorCode.INTERNAL_ERROR).initCause(e);
        }
        return jwt;
    }

    /**
     * 根据Token获取user_id
     *
     * @param secret 加密使用的密钥
     * @return user_id
     */
    public static Long getAppUID(String token, TokenType type, String secret) {
        Map<String, Claim> claims = verifyToken(token, type, secret).getClaims();
        return claims.get("userId").asLong();
    }
}