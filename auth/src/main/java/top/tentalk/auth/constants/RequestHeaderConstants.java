package top.tentalk.auth.constants;

/**
 * header名字常量
 */
public class RequestHeaderConstants {
    
    // JWT token header key
    public static final String AUTH_TOKEN = "Authentication-Token";

    /**
     * 网关分组: myAdminApi
     */
    public static final String GROUP_ADMIN = "myAdminApi";

    /**
     * 网关分组: bsa
     */
    public static final String GROUP_BSA = "bsa";

    /**
     * request id
     */
    public static final String REQUEST_ID = "X-B3-TraceId";

    public static final String REQUEST_UUID = "uuid";

    /**
     * 请求头: groupName
     */
    public static final String HEAD_GROUP = "groupName";

    /**
     * 请求头: user-agent
     */
    public static final String HEAD_AGENT = "user-agent";

    /**
     * 请求头: referer
     */
    public static final String HEAD_REFERER = "referer";

    /**
     * 请求头: Req-Forwarded-Id
     */
    public static final String REQ_FORWARDED_ID = "Req-Forwarded-Id";
}
