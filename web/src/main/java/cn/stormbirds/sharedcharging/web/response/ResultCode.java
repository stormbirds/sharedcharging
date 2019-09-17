package cn.stormbirds.sharedcharging.web.response;

/**
 *
 * @ Description 接口请求状态码
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 2019/5/5 20:11
 *
 */
public enum ResultCode {
    /*
    请求返回状态码和说明信息
     */
    SUCCESS(200, "成功"),

    BAD_REQUEST(400, "参数错误"),
    UNAUTHORIZED(401, "未授权，或者权限不足"),
    LOGIN_ERROR(401, "登陆失败，用户名或密码无效"),
    SMS_CODE_ERROR(401,"验证码无效"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "请求的资源不存在"),
    ERROR_MONEY(404, "钱包余额错误"),
    USER_NOT_FOUND(404, "用户不存在"),
    OPERATE_ERROR(405, "操作失败，请求操作的资源不存在"),
    TIME_OUT(408, "请求超时"),
    REPEAT_ERROR(403, "操作已完成，请勿重复操作"),


    SERVER_ERROR(500, "服务器内部错误"),
;
    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
