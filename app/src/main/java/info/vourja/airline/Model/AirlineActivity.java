package info.vourja.airline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirlineActivity {

    @Expose
    @SerializedName("id")
    private long id;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
