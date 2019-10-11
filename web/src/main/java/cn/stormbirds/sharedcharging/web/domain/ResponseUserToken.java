package cn.stormbirds.sharedcharging.web.domain;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.domain
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/10/11 16:34
 */


public class ResponseUserToken {

    /**
     * application : 22bcffa0-8f86-11e6-9df8-516f6df68c6d
     * access_token : YWMtQaHdTOwCEemI0ZEwQEcUXAAAAAAAAAAAAAAAAAAAAAEivP-gj4YR5p34UW9t9oxtAgMAAAFtufaIAABPGgDMqB3qjNJN7M9zO6IwPfkY5eIzAlG0Cb2z9kG3wAF58A
     * expires_in : 5184000
     */

    private String application;
    private String access_token;
    private long expires_in;

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }
}
