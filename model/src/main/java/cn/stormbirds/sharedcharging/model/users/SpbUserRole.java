package cn.stormbirds.sharedcharging.model.users;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户权限关系表
 * </p>
 *
 * @author stormbirds
 * @since 2019-09-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SpbUserRole对象", description="用户权限关系表")
public class SpbUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "权限关系对应用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "权限关系对应权限ID")
    @TableField("role_id")
    private Long roleId;


}
