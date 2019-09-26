package cn.stormbirds.sharedcharging.model.users;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author stormbirds
 * @since 2019-09-20
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="SpbUsers对象", description="用户表")
public class SpbUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "唯一用户名")
    private String username;

    @ApiModelProperty(value = "用户登录密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty(value = "用户是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "用户是否被锁定")
    @TableField(value = "")
    private Boolean accountNonLocked;

    @ApiModelProperty(value = "用户上次更新密码时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastPasswordResetDate;

    @ApiModelProperty(value = "用户创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;


}
