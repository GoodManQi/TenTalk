package top.tentalk.business.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.tentalk.auth.utils.JWTUtil;
import top.tentalk.business.service.IUserService;
import top.tentalk.common.constants.JwtClaimConstants;
import top.tentalk.common.constants.TokenType;
import top.tentalk.common.utils.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements IUserService {

    @Value("${ssv.userLoginAppId}")
    private String jwtSecret;

    @Override
    public String Sign() {

        Map<String, Object> multimap = new HashMap<>();
        multimap.put(JwtClaimConstants.USER_ID, 1111111L);
        multimap.put(JwtClaimConstants.TOKEN_ID, 222222L);
        multimap.put(JwtClaimConstants.REFRESH_TIMES, 2L);
        multimap.put(JwtClaimConstants.CREATED_AT, System.currentTimeMillis());

        String secret = DigestUtils.md5DigestAsHex(jwtSecret.getBytes());

        return JWTUtil.createToken(multimap, TokenType.LOGIN_USER, secret, DateUtil.addHours(new Date(), 2));
    }

}
