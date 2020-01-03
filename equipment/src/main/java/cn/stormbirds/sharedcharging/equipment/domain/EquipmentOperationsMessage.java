package cn.stormbirds.sharedcharging.equipment.domain;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * cn.stormbirds.sharedcharging.equipment.domain
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2020/1/2 10:38 上午
 */
@Data
@Builder
public class EquipmentOperationsMessage {
    public EquipmentOperations operations;
    public String EqCode;
    public String payload;

}
