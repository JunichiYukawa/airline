package info.vourja.airline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class AirlineActivity {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("activity_name")
    private String name;

    @Expose
    @SerializedName("activity_location")
    private String location;

    @Expose
    @SerializedName("activity_start_date")
    private Date   start_date;

    @Expose
    @SerializedName("activity_end_date")
    private Date   end_date;

    @Expose
    @SerializedName("activity_description")
    private String description;

    @Expose
    @SerializedName("activity_url")
    private String url;

    @Expose
    @SerializedName("activity_template")
    private String template;

    @Expose
    @SerializedName("activity_lines")
    private List<Line> lines;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> line) {
        this.lines = line;
    }
}
