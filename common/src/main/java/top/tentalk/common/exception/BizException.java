package top.tentalk.common.exception;


import org.apache.commons.lang3.ArrayUtils;
import top.tentalk.common.constants.ErrorCode;

/**
 * 自定义异常处理类
 * Created by huawei on 2022/4/26.
 */
public class BizException extends RuntimeException {

    private Object[] args;

    private String message;

    private String code;

    public BizException() {
        super();
    }

    public BizException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BizException(String code, String message, Object[] args) {
        this.code = code;
        this.message = message;
        this.args = ArrayUtils.nullToEmpty(args);
    }

    public BizException(ErrorCode iErrorCode) {
        this.code = iErrorCode.getCode();
        this.message = iErrorCode.getDesc();
    }

    public BizException(ErrorCode errorCode, Object[] args) {
        this.code = errorCode.getCode();
        this.message = errorCode.getDesc();
        this.args = ArrayUtils.nullToEmpty(args);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getArgs() {
        if (this.args == null) {
            return new Object[0];
        }
        return args.clone();
    }

    public void setArgs(Object[] args) {
        this.args = ArrayUtils.nullToEmpty(args);
    }
}
