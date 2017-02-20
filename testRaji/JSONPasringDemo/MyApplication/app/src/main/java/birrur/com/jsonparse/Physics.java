package birrur.com.jsonparse;

/**
 * Created by birru on 2/18/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Physics {

    @SerializedName("cruise_control")
    @Expose
    private Boolean cruiseControl;

    public Boolean getCruiseControl() {
        return cruiseControl;
    }

    public void setCruiseControl(Boolean cruiseControl) {
        this.cruiseControl = cruiseControl;
    }

}