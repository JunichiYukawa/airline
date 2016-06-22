package info.vourja.airline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vourja on 2016/06/22.
 */
public class ModelCollection<T> {

    @Expose
    @SerializedName("total")
    long total;

    @Expose
    @SerializedName("objects")
    List<T> objects;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }
}
