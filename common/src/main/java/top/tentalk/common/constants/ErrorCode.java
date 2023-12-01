package top.tentalk.common.constants;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //通用
    OK("Ok", "成功"),
    MISSING_PARAMETER("Common.ParamNotNull", "不能为空。"),
    INTERNAL_ERROR("Common.InternalError","服务器内部错误");

    private final String code;

    private final String desc;

    ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
