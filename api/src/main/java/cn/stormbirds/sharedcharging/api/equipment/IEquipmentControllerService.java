package cn.stormbirds.sharedcharging.api.equipment;

/**
 * <p>
 * cn.stormbirds.sharedcharging.api.equipment
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/10/20 下午10:57
 */
public interface IEquipmentControllerService {
    /**
     * 通过设备标识符租借
     * @param eqCode 设备唯一标识符
     * @return 状态码
     */
    public int rentPowerBank(String eqCode);
}
