package info.vourja.airline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @Expose
    @SerializedName("auth_token")
    private String auth_token;

    @Expose
    @SerializedName("twitter_name")
    private String twitter_name;

    @Expose
    @SerializedName("twitter_token")
    private String twitter_token;

    @Expose
    @SerializedName("twitter_secret")
    private String twitter_secret;

    @Expose
    @SerializedName("twitter_id")
    private long twitter_id;

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getTwitter_name() {
        return twitter_name;
    }

    public void setTwitter_name(String twitter_name) {
        this.twitter_name = twitter_name;
    }

    public String getTwitter_token() {
        return twitter_token;
    }

    public void setTwitter_token(String twitter_token) {
        this.twitter_token = twitter_token;
    }

    public String getTwitter_secret() {
        return twitter_secret;
    }

    public void setTwitter_secret(String twitter_secret) {
        this.twitter_secret = twitter_secret;
    }

    public long getTwitter_id() {
        return twitter_id;
    }

    public void setTwitter_id(long twitter_id) {
        this.twitter_id = twitter_id;
    }
}
