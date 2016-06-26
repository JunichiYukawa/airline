package info.vourja.airline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by vourja on 2016/06/22.
 */
public class Line {

    @Expose
    @SerializedName("uuid")
    private UUID uuid;

    @Expose
    @SerializedName("customer_id")
    private int customer_id;

    @Expose
    @SerializedName("number")
    private int number;

    @Expose
    @SerializedName("create_date")
    private String create_date;

    @Expose
    @SerializedName("arrived_date")
    private String arrived_date;

    @Expose
    @SerializedName("pass_date")
    private String pass_date;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getArrived_date() {
        return arrived_date;
    }

    public void setArrived_date(String arrived_date) {
        this.arrived_date = arrived_date;
    }

    public String getPass_date() {
        return pass_date;
    }

    public void setPass_date(String pass_date) {
        this.pass_date = pass_date;
    }
}
