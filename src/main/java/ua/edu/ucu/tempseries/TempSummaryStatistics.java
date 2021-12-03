package ua.edu.ucu.tempseries;

public class TempSummaryStatistics {
    public final double avgTemp, devTemp, minTemp, maxTemp;

    public TempSummaryStatistics(TemperatureSeriesAnalysis analysis) throws IllegalArgumentException {
        this.avgTemp = analysis.average();
        this.devTemp = analysis.deviation();
        this.minTemp = analysis.min();
        this.maxTemp = analysis.max();
    }

}
