package cn.stormbirds.sharedcharging.web.exception;


import cn.stormbirds.sharedcharging.web.domain.ResultCode;
import cn.stormbirds.sharedcharging.web.domain.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @ Description DefaultExceptionHandler.java
 * 异常处理类
 * controller层异常无法捕获处理，需要自己处理
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/5/5 20:13
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 处理所有自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CustomControllerException.class)
    public ResultJson handleCustomException(CustomControllerException e) {
        log.error(e.getResultJson().getMsg());
        return e.getResultJson();
    }

    /**
     * 处理参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultJson handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(Objects.requireNonNull(e.getBindingResult().getFieldError()).getField() +
                e.getBindingResult().getFieldError().getDefaultMessage());
        return ResultJson.failure(ResultCode.BAD_REQUEST,
                e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
