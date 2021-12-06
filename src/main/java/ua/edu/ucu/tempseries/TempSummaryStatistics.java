package ua.edu.ucu.tempseries;

public class TempSummaryStatistics {
    private final double averageTemp, deviation, minTemp, maxTemp;

    public TempSummaryStatistics(TemperatureSeriesAnalysis analysis) throws IllegalArgumentException {
        this.averageTemp = analysis.average();
        this.deviation = analysis.deviation();
        this.minTemp = analysis.min();
        this.maxTemp = analysis.max();
    }

    public double getAverage() {
        return averageTemp;
    }

    public double getDeviation() {
        return deviation;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }
}
