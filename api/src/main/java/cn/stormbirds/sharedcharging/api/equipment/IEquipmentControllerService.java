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
     *
     * @param eqCode 设备唯一标识符
     * @return 状态码 1：成功 0：失败
     */
    int ejectBatteryByDeviceId(String eqCode);

    /**
     * 更新视频操作
     * @param eqCode 设备唯一标识，为空则表示所有设备
     * @param videoFile 视频文件路径
     * @return 1：成功 0：失败
     */
    int updateVideoByPath(String eqCode, String videoFile);

    /**
     * 更新二维码操作
     * @param eqCode 设备唯一标识，为空则表示所有设备
     * @param qrUrl 二维码链接
     * @return 1：成功 0：失败
     */
    int updateQRcode(String eqCode, String qrUrl);

    /**
     * 重启设备
     * @param eqCode 设备唯一标识，为空则表示所有设备
     * @return 1：成功 0：失败
     */
    int restartDevice(String eqCode);

    /**
     * 通过设备唯一标识获取设备二维码
     * @param eqCode 设备唯一标识
     * @return 1：成功 0：失败
     */
    String getQrCode(String eqCode);
}
