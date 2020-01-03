package cn.stormbirds.sharedcharging.equipment.domain;

import cn.stormbirds.sharedcharging.model.equipment.PowerBank;
import cn.stormbirds.sharedcharging.model.equipment.VideoFile;
import cn.stormbirds.sharedcharging.model.equipment.WIFISignals;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * cn.stormbirds.sharedcharging.equipment.domain
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2020/1/3 10:56 上午
 */
@Data
@Builder
public class EquipmentInfo {
    public String eqCode;
    public String mac;
    public String imei;
    public List<WIFISignals> wifiSignalsList;
    public List<PowerBank> powerBanks;
    public List<VideoFile> videoFileList;
}
