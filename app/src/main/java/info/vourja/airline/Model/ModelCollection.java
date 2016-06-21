package info.vourja.airline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vourja on 2016/06/22.
 */
public class ModelCollection<T> {

    @Expose
    @SerializedName("num_result")
    long num_result;

    @Expose
    @SerializedName("objects")
    List<T> objects;

    public long getNum_result() {
        return num_result;
    }

    public void setNum_result(long num_result) {
        this.num_result = num_result;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }
}
