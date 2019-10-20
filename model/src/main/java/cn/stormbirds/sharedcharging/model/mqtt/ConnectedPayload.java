package cn.stormbirds.sharedcharging.model.mqtt;

/**
 * <p>
 * cn.stormbirds.sharedcharging.model.mqtt
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/10/20 下午7:38
 */
public class ConnectedPayload {
    /**
     * clientid : id1
     * username : u
     * ipaddress : 127.0.0.1
     * connack : 0
     * ts : 1554047291
     * proto_ver : 3
     * proto_name : MQIsdp
     * clean_start : true
     * keepalive : 60
     */

    private String clientid;
    private String username;
    private String ipaddress;
    private int connack;
    private long ts;
    private int proto_ver;
    private String proto_name;
    private boolean clean_start;
    private int keepalive;

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public int getConnack() {
        return connack;
    }

    public void setConnack(int connack) {
        this.connack = connack;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public int getProto_ver() {
        return proto_ver;
    }

    public void setProto_ver(int proto_ver) {
        this.proto_ver = proto_ver;
    }

    public String getProto_name() {
        return proto_name;
    }

    public void setProto_name(String proto_name) {
        this.proto_name = proto_name;
    }

    public boolean isClean_start() {
        return clean_start;
    }

    public void setClean_start(boolean clean_start) {
        this.clean_start = clean_start;
    }

    public int getKeepalive() {
        return keepalive;
    }

    public void setKeepalive(int keepalive) {
        this.keepalive = keepalive;
    }
}
