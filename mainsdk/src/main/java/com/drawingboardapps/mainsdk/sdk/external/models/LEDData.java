package com.drawingboardapps.mainsdk.sdk.external.models;

/**
 * Created by Zach on 4/11/2017.
 */

public class LEDData {
    public String getNumLightsReplaced() {
        return numLightsReplaced;
    }

    public void setNumLightsReplaced(String numLightsReplaced) {
        this.numLightsReplaced = numLightsReplaced;
    }

    public String getTotalSavingsDollars() {
        return totalSavingsDollars;
    }

    public void setTotalSavingsDollars(String totalSavingsDollars) {
        this.totalSavingsDollars = totalSavingsDollars;
    }

    public String getAvgSavingsDollars() {
        return avgSavingsDollars;
    }

    public void setAvgSavingsDollars(String avgSavingsDollars) {
        this.avgSavingsDollars = avgSavingsDollars;
    }

    public String getAvgPctEnergySavings() {
        return avgPctEnergySavings;
    }

    public void setAvgPctEnergySavings(String avgPctEnergySavings) {
        this.avgPctEnergySavings = avgPctEnergySavings;
    }

    String numLightsReplaced;
    String totalSavingsDollars;
    String avgSavingsDollars;
    String avgPctEnergySavings;

    @Override
    public String toString(){
        return String.format("[numLightsReplaced=%s,totalSavingsDollars=%s,avgSavingsDollars=%s,avgPctEnergySavings=%s]",
                numLightsReplaced,
                totalSavingsDollars,
                avgSavingsDollars,
                avgPctEnergySavings);
    }
}
