package birrur.com.jsonparse;

/**
 * Created by birru on 2/18/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dashboard {

    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("rpm")
    @Expose
    private Integer rpm;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getRpm() {
        return rpm;
    }

    public void setRpm(Integer rpm) {
        this.rpm = rpm;
    }

}