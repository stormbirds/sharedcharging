package cn.stormbirds.sharedcharging.web.exception;


import cn.stormbirds.sharedcharging.web.response.ResultJson;

/**
 *
 * @ Description 自定义异常，配合异常处理类DefaultExceptionHandler 在controller中返回未知异常
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/5/5 17:54
 *
 */
public class CustomControllerException extends RuntimeException{
    private static final long serialVersionUID = -8592312925888367630L;
    private ResultJson resultJson;

    public CustomControllerException(ResultJson resultJson) {
        this.resultJson = resultJson;
    }

    public ResultJson getResultJson() {
        return resultJson;
    }
}
