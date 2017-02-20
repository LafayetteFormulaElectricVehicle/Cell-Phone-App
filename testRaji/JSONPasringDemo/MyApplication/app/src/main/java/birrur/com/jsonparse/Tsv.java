package birrur.com.jsonparse;

/**
 * Created by birru on 2/18/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tsv {

    @SerializedName("voltage")
    @Expose
    private Double voltage;
    @SerializedName("current")
    @Expose
    private Double current;
    @SerializedName("state_of_charge")
    @Expose
    private Double stateOfCharge;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Double getStateOfCharge() {
        return stateOfCharge;
    }

    public void setStateOfCharge(Double stateOfCharge) {
        this.stateOfCharge = stateOfCharge;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}