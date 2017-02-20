package birrur.com.jsonparse;

/**
 * Created by birru on 2/18/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tsi {

    @SerializedName("Traction")
    @Expose
    private Integer traction;

    public Integer getTraction() {
        return traction;
    }

    public void setTraction(Integer newTrac) {
        this.traction = newTrac;
    }

}