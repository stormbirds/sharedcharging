package cn.stormbirds.sharedcharging.model.equipment;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * cn.stormbirds.sharedcharging.model.equipment
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2020/1/3 11:49 上午
 */
@Data
@Builder
public class VideoFile {
    public String name;
    public String url;
}
