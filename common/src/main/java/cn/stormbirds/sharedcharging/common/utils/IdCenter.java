package cn.stormbirds.sharedcharging.common.utils;

/**
 * <p>
 * cn.stormbirds.sharedcharging.common.utils
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/27 上午1:08
 */
public enum IdCenter {
    INSTENSE{
        @Override
        public IdCenter init(long dataCenterId, long workerId, String desc) {
            this.setDataCenterId(dataCenterId);
            this.setWorkerId(workerId);
            this.setDesc(desc);
            return INSTENSE;
        }
    };
    private Long dataCenterId ;

    private Long workerId ;

    private String desc ;

    protected void setDataCenterId(long datacenterId){
        this.dataCenterId = datacenterId;
    }

    protected void setWorkerId(long workerId){
        this.workerId = workerId;
    }

    protected void setDesc(String desc){
        this.desc = desc;
    }

    // ==============================Fields===========================================
    /** 开始时间截 (2019-03-15) 这里一旦设定一定不能改变 */
    private final long twepoch = 1552612212000L;


    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = ~(-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = ~(-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = ~(-1L << sequenceBits);

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    public abstract IdCenter init(long datacenterId, long workerId, String desc);

    public String getIdString() {
        return String.valueOf(nextId());
    }

    public long getId() {
        return nextId();
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    private synchronized long nextId() {
        if(dataCenterId==null||workerId==null){
            throw new RuntimeException(String.format("DataCenterId:%d, WorkerId:%d 必须全部设置，不准为空",dataCenterId,workerId));
        }
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;
        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift)|
                (dataCenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SnowflakeIdWorker{");
        sb.append("workerId=").append(workerId);
        sb.append(", datacenterId=").append(dataCenterId);
        sb.append(", remark=").append(desc);
        sb.append(", twepoch=").append(twepoch);
        sb.append(", lastTimestamp=").append(lastTimestamp);
        sb.append(", sequence=").append(sequence);
        sb.append('}');
        return sb.toString();
    }
}
