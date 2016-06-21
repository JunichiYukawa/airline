package info.vourja.airline.NetService.Request;

public class RegisterRequest {
    private String token;
    private String secret;
    private String name;
    private long twitter_id;

    public RegisterRequest(String token, String secret, String name, long twitter_id) {
        this.token = token;
        this.secret = secret;
        this.name = name;
        this.twitter_id = twitter_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTwitter_id() {
        return twitter_id;
    }

    public void setTwitter_id(long twitter_id) {
        this.twitter_id = twitter_id;
    }
}