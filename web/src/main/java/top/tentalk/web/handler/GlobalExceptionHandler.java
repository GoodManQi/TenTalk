package top.tentalk.web.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.tentalk.common.exception.BizException;
import top.tentalk.common.pojo.CommonResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 全局异常处理类
 *
 * @author qjr
 * @since 2023/12/02
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理参数异常
     *
     * @param ex 异常信息
     * @return 处理后的前端错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        CommonResponse<Void> response = new CommonResponse<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            if (errorMessage != null) {
                String[] strings = errorMessage.split(":");
                response.setCode(Integer.parseInt(strings[0]));
                response.setMessage(strings[1]);
            }
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 处理自定义异常
     *
     * @param ex 异常信息
     * @return 处理后的前端错误信息
     */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<CommonResponse<Void>> handleBizExceptions(BizException ex) {
        CommonResponse<Void> response = new CommonResponse<>();
        Properties properties = new Properties();
        // 获取文件的URL对象
        URL url = ClassLoader.getSystemResource("ValidationMessages.properties");

        try {
            // 获取文件的输入流对象
            InputStream stream = url.openStream();
            // 从输入流中加载数据
            properties.load(stream);
            String message = properties.getProperty(ex.getCode());
            String[] strings = message.split(":");
            response.setCode(Integer.parseInt(strings[0]));
            response.setMessage(strings[1]);
        } catch (IOException exception) {
            log.error(exception.getMessage());
            response.setCode(1002);
            response.setMessage("服务器内部错误");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * 处理其他异常
     *
     * @param ex 异常信息
     * @return 处理后的前端错误信息
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleOtherExceptions(Exception ex) {
        log.error(ex.getMessage());
        CommonResponse<Void> response = new CommonResponse<>();
        response.setCode(1002);
        response.setMessage("服务器内部错误");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
