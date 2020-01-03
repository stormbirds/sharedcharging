package cn.stormbirds.sharedcharging.model.equipment;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * cn.stormbirds.sharedcharging.model.equipment
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2020/1/3 11:20 上午
 */
@Data
@Builder
public class WIFISignals {
    public String ssid;
    public Float SignalStrength;
    public String password;
}
