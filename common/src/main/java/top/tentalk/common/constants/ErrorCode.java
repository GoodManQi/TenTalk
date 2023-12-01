package top.tentalk.common.constants;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //权限微服务
    AUTH_FAILURE_INVALID_SECRET_ID("AuthFailure.InvalidSecretId", "密钥非法（不是云 API 密钥类型）。"),
    AUTH_FAILURE_SECRET_ID_NOT_FOUND("AuthFailure.SecretIdNotFound", "密钥不存在。"),
    AUTH_FAILURE_SIGNATURE_EXPIRE("AuthFailure.SignatureExpire", "签名过期。"),
    AUTH_FAILURE_SIGNATURE_FAILURE("AuthFailure.SignatureFailure", "签名错误。"),
    AUTH_FAILURE_TOKEN_FAILURE("AuthFailure.TokenFailure", "token 错误。"),
    AUTH_FAILURE_GET_ROLE_FAILED("AuthFailure.GetRoleFailed", "获取角色失败。"),
    AUTH_FAILURE_NOT_PERMISSION("AuthFailure.NotPermission", "没有权限。"),

    // 角色管理
    AUTH_ROLE_DELFAI_BY_USER("AuthFailure.RoleNotDel", "角色下还有管理员，不能删除。"),
    AUTH_ROLE_DELFAI_BY_DEF("AuthFailure.RoleNotDelAdmin", "超级管理员角色不能删除。"),

    AUTH_ROLE_STATUS_ERROR("AuthFailure.RoleStatusError", "角色被禁用。"),
    AUTH_ROLE_BY_NAME_NULL("AuthFailure.RoleNameNull", "角色名称不能为空。"),
    AUTH_ROLE_BY_MODULE_NULL("AuthFailure.RoleModuleNull", "角色模块不能为空。"),
    AUTH_ROLE_NAME_EXIST("AuthFailure.RoleNameExist", "角色名已存在。"),
    AUTH_USER_UPDATE_ROLE_NULL("AuthFailure.UserRoleNull", "至少保留一个角色。"),

    AUTH_USER_ROLE_CHECK_ADMIN_NUT_ROLE("AuthFailure.UserRoleCheckNotRole", "不能在超管账户上增加角色。"),
    AUTH_USER_ROLE_ADD_NUT_ROLE("AuthFailure.UserRoleAddNotRole", "没有对应的角色。"),

    //注册登录微服务
    ACCOUNT_PARAMS_RENAME_INVALID("Account.ParamsRenameInvalid", "{0}参数不合法"),
    ACCOUNT_PHONE_VERIFICATION_CODE_HAS_EXPIRED("Account.PhoneVerificationCodeHasExpired", "手机号验证码已过期"),
    ACCOUNT_EMAIL_VERIFICATION_CODE_HAS_EXPIRED("Account.EmailVerificationCodeHasExpired", "邮箱验证码已过期"),
    ACCOUNT_MISSING_PARAMETER("Account.MissingParameter", "缺少参数错误"),
    ACCOUNT_EMAIL_DOES_NOT_EXIST("Account.EmailDoesNotExist", "当前邮箱不存在"),
    ACCOUNT_VERIFICATION_CODE_ERROR("Account.VerificationCodeError", "验证码错误"),
    ACCOUNT_PHONE_DOES_NOT_EXIST("Account.PhoneDoesNotExist", "当前手机号不存在"),
    ACCOUNT_VERIFICATION_CODE_LIMIT_EXCEEDED("Account.VerificationCodeLimitExceeded", "验证码错误输入已达上限，请24小时后重试！"),
    ACCOUNT_REDUCED_NUMBER_INPUT_ERRORS("Account.ReducedNumberInputErrors", "验证码错误输入{0} 次，您还有{1} 次机会"),
    ACCOUNT_PLEASE_BIND_WECHAT("Account.PleaseBindWechat", "请绑定微信"),
    ACCOUNT_UNABLE_GET_WECHAT_TOKEN("Account.UnableGetWechatToken", "无法获取用户微信token"),
    ACCOUNT_THIS_WECHAT_ALREADY_EXISTS("Account.ThisWechatAlreadyExists", "当前微信已绑定平台"),
    ACCOUNT_ERROR_GETTING_WECHAT("Account.ErrorGettingWechat", "微信获取用户信息错误"),
    ACCOUNT_GET_TEMPORARY_TOKEN_EXCEPTION("Account.GetTemporaryTokenException", "获取临时token异常"),
    ACCOUNT_PHONE_FORMAT_ERROR("Account.PhoneFormatError", "请输入正确的手机号"),
    ACCOUNT_UPLOAD_PATH_DOES_NOT_EXIST("Account.UploadPathDoesNotExist", "上传路径类型不存在"),
    ACCOUNT_UPLOAD_TYPE_DOES_NOT_EXIST("Account.UploadTypeDoesNotExist", "上传类型不存在"),
    ACCOUNT_CANNOT_BE_LESS_THAN_ONE("Account.CannotBeLessThanOne", "最小值不能小于1"),
    ACCOUNT_CANNOT_BE_GREATER_THAN_TEN("Account.CannotBeGreaterThanTen", "最大值不能大于10"),
    ACCOUNT_NOT_LOGGED_IN("Account.NotLoggedIn", "请登录"),
    ACCOUNT_NOT_EXIST_FAILED("Account.NotExistFailed", "账号不存在。"),
    ACCOUNT_PWD_FAILED("Account.PwdFailed", "密码不正确。"),
    ACCOUNT_FROZEN_FAILED("Account.FrozenFailed", "该账号已被冻结。"),
    ACCOUNT_STATUS_FAILED("Account.StatusFailed", "该账号已被停用。"),
    ACCOUNT_INCORRECT_NAME("Account.IncorrectName", "姓氏/名称不正确"),
    ACCOUNT_EMAIL_ALREADY_EXISTS("Account.EmailAlreadyExists", "邮箱已存在"),
    ACCOUNT_THIS_PHONE_NUMBER_HAS_BEEN_BOUND("Account.ThisPhoneNumberHasBeenBound", "当前手机号已经被绑定"),
    ACCOUNT_NO_INVITATION_RECORD_FOUND("Account.NoInvitationRecordFound", "未查询到邀请记录,请检查邮箱地址是否正确"),
    ACCOUNT_INVITATION_INFORMATION_HAS_EXPIRED("Account.InvitationInformationHasExpired", "邀请信息已失效"),
    ACCOUNT_MOBILE_PHONE_NUMBER_MISMATCH("Account.MobilePhoneNumberMismatch", "手机号与预录入手机号不匹配"),
    ACCOUNT_REPEATED_MODIFICATION("Account.RepeatedModification", "新联系方式不能与之前的重复"),
    ACCOUNT_USER_IDENTITY_ERROR("Account.UserIdentityError", "已被提名邮箱勿重复绑定"),
    ACCOUNT_ID_CARD_INCORRECT("Account.IdCardIncorrect", "证件号码不正确"),
    ACCOUNT_ALREADY_SUBMITTED("Account.AlreadySubmitted", "原邮箱账号申诉已提交，请耐心等待申诉结果"),
    ACCOUNT_LINK_HAS_EXPIRED("Account.LinkHasExpired", "链接已失效"),
    ACCOUNT_INSTITUTION_MAILBOX("Account.InstitutionMailbox", "该邮箱为提名机构账号，暂不支持作为个人申报账号，请更换邮箱"),
    ACCOUNT_REPEATED_EMAIL("Account.RepeatedEmail", "与被提名人邮箱不一致"),
    ACCOUNT_EMAIL_PREFIX_INCORRECTLY_ENTERED("Account.EmailPrefixIncorrectlyEntered", "提名邮箱前缀错误输入5次，您还有5次机会"),
    ACCOUNT_EMAIL_PREFIX_ERROR_INPUT_UPPER_LIMIT("Account.EmailPrefixErrorInputUpperLimit", "提名邮箱前缀错误输入已达上限，请24小时后重试"),
    ACCOUNT_PLEASE_BIND_REGISTERED_EMAIL("Account.PleaseBindRegisteredEmail", "请绑定已注册邮箱"),
    ACCOUNT_ACCOUNT_IS_ACTIVATED("Account.AccountIsActivated", "提名机构账号已激活成功，无需二次注册，请前往官网登录"),
    ACCOUNT_MECHANISM_REPEATED_EMAIL("Account.mechanismRepeatedEmail", "邮箱前缀输入与预录入不一致，若有疑问请联系项目组"),
    ACCOUNT_MECHANISM_PREFIX_INCORRECTLY_ENTERED("Account.mechanismPrefixIncorrectlyEntered", "邮箱前缀错误输入5次，您还有5次机会"),
    ACCOUNT_MECHANISM_PREFIX_ERROR_INPUT_UPPER_LIMIT("Account.mechanismPrefixErrorInputUpperLimit", "邮箱前缀错误输入已达上限，请24小时后重试"),
    ACCOUNT_ACCOUNT_IS_DISABLED("Account.AccountIsDisabled", "当前后台不可使用，请联系contact@newcornerstone.org.cn"),
    ACCOUNT_PLEASE_CONTACT_THE_STAFF("Account.PleaseContactTheStaff", "请联系秘书处"),
    ACCOUNT_WRONG_ACCESS("Account.WrongAccess", "该账号为专家账号,暂不支持作为个人申报账号,请更换邮箱"),
    ACCOUNT_NUMBER_VERIFICATION_CODE_REQUESTS("Account.NumberVerificationCodeRequests", "获取验证码达20次，您还有10次机会"),

    //申报微服务
    DECLARE_DECLARATION_RECORD_ALREADY_EXISTS("Declare.DeclarationRecordAlreadyExists", "申报记录已存在。"),

    //运营后台
    ADMIN_TIME_EXCEEDS_ERROR("Admin.TimeExceedsError", "时间误差超过30秒"),
    ADMIN_FAIL_TO_PASS_THE_SIGNATURE_VERIFICATION("Admin.FailToPassTheSignatureVerification", "验签不通过"),

    //通用
    OK("Ok", "成功"),
    FAILED_OPERATION("FailedOperation", "操作失败。"),
    INTERNAL_ERROR("InternalError", "内部错误。"),
    INVALID_ACTION("InvalidAction", "接口不存在。"),
    INVALID_PARAMETER("InvalidParameter", "参数错误。"),
    INVALID_PARAMETER_VALUE("InvalidParameterValue", "参数取值错误。"),
    MISSING_PARAMETER("MissingParameter", "缺少参数错误。"),
    NO_SUCH_VERSION("NoSuchVersion", "接口版本不存在。"),
    REQUEST_LIMIT_EXCEEDED("RequestLimitExceeded", "请求的次数超过了频率限制。"),
    UNAUTHORIZED_OPERATION("UnauthorizedOperation", "未授权操作。"),
    UNKNOWN_PARAMETER("UnknownParameter", "未知参数错误。"),
    UNSUPPORTED_OPERATION("UnsupportedOperation", "操作不支持。"),
    UNSUPPORTED_PROTOCOL("UnsupportedProtocol", "HTTPS 请求方法错误，只支持 GET 和 POST 请求。"),
    RESPONSE_TIME_OUT_HYSTRIX_ERR("ResponseTimeOutHystrixRrr", "服务响应超时。"),
    JSON_FORMAT_ERR("JsonFormatErr", "json数据格式不正确。"),
    USER_ROLE_NOT_EXIST("UserRoleNotExist", "用户没有分配角色。"),
    USER_NOT_EXIST("UserNotExist", "用户不存在。"),
    SQL_EXECUTE_ERR("SqlExecuteErr", "sql执行异常。"),
    TIMESTAMP_DIFF_TIMEOUT("TimestampDiffTimeout", "时间戳误差超出允许范围。"),
    TIMESTAMP_NOT_VALID("TimestampNotValid", "头时间戳不是一个有效时间值。"),
    PASS_ID_MUST_NOT_EMPTY("PassIdMustNotEmpty", "头passId不能为空。"),
    RECORD_NOT_EXIST("RecordNotExist", "记录不存在。"),
    HANDLER_MUST_NOT_NULL("HandlerMustNotNull", "异常处理回调函数不能为空。"),
    REQUEST_OUT_LIMIT_NEED_SAFE_TICKET("RequestOutLimitNeedSafeTicket", "需要安全ticket验证。"),
    VERITY_SAFE_TICKET_FAIL("VeritySafeTicketFail", "验证安全ticket失败。"),
    API_INTERNAL_INVOKE_HEADER_KEY_NOT_EXIST("ApiInternalInvokeHeaderKeyNotExist", "内部服务调用缺失约定的头部key。"),
    API_INTERNAL_INVOKE_VALIDATION_FAIL("ApiInternalInvokeValidationFail", "内部服务调用验证失败。"),
    DESERIALIZATION_FAIL("DeserializationFail", "JacksonUtils反序列化失败。"),
    DB_COMMUNICATION_ERR("DbCommunicationErr", "数据库连接异常。"),
    ADD_DUPLICATION_ERR("AddDuplicationErr", "数据添加重复。"),
    DATA_OVER_TIME_ERR("DataOverTimeErr", "数据过期。"),
    PARAMS_RENAME_INVALID("ParamsRenameInvalid", "参数不合法,出现重名。"),
    UNAUTHORIZED("Unauthorized", "非法访问。"),
    NOT_FOUND("NotFound", "请求的资源不存在。"),
    TOO_MANY_REQUESTS("TooManyRequests", "获取验证码达上限,请24小时后重试"),
    IncorrectEmail("IncorrectEmail", "邮箱格式不正确"),
    FrequentRequests("FrequentRequests", "请勿频繁获取验证码"),
    ERROR_UPLOADING_PICTURE("ErrorUploadingPicture", "上传图片有误"),
    FILE_SIZE_EXCEEDS_LIMIT("FileSizeExceedsLimit", "文件大小超过限制"),
    PLEASE_UPLOAD_ID_PHOTO_FRONT("PleaseUploadIdPhotoFront", "请上传证件图片"),
    PLEASE_UPLOAD_ID_PHOTO_REVERSE("PleaseUploadIdPhotoReverse", "请上传证件图片"),
    KMS_FAIL_CODE("KmsFailCode", "kms内部错误"),
    COS_FAIL_CODE("CosFailCode", "Cos内部错误");


    private final String code;

    private final String desc;

    ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
