package cn.stormbirds.sharedcharging.model.users;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author stormbirds
 * @since 2019-09-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SpbUsers对象", description="用户表")
public class SpbUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "唯一用户名")
    private String username;

    @ApiModelProperty(value = "用户登录密码")
    private String password;

    @ApiModelProperty(value = "用户是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "用户是否被锁定")
    private Boolean accountNonLocked;

    @ApiModelProperty(value = "用户上次更新密码时间")
    private LocalDateTime lastPasswordResetDate;

    @ApiModelProperty(value = "用户创建时间")
    private LocalDateTime createdAt;


}
