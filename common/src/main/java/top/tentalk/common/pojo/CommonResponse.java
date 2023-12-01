package top.tentalk.common.pojo;

import lombok.Data;

/**
 * 公共响应类
 *
 * @param <T>
 */
@Data
public class CommonResponse<T> {

    private int code; // 错误码
    private String message; // 错误信息
    private T data; // 返回数据

    public CommonResponse() {
        this.code = 0;
        this.message = "success";
    }

    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}