package birrur.com.jsonparse;

/**
 * Created by birru on 2/18/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("tsi")
    @Expose
    private Tsi tsi;
    @SerializedName("tsv")
    @Expose
    private Tsv tsv;
    @SerializedName("physics")
    @Expose
    private Physics physics;
    @SerializedName("cooling")
    @Expose
    private Cooling cooling;
    @SerializedName("dashboard")
    @Expose
    private Dashboard dashboard;

    public Tsi getTsi() {
        return tsi;
    }

    public void setTsi(Tsi tsi) {
        this.tsi = tsi;
    }

    public Tsv getTsv() {
        return tsv;
    }

    public void setTsv(Tsv tsv) {
        this.tsv = tsv;
    }

    public Physics getPhysics() {
        return physics;
    }

    public void setPhysics(Physics physics) {
        this.physics = physics;
    }

    public Cooling getCooling() {
        return cooling;
    }

    public void setCooling(Cooling cooling) {
        this.cooling = cooling;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

}