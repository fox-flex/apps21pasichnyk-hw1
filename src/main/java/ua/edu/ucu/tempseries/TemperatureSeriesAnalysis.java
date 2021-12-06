package ua.edu.ucu.tempseries;

import java.util.ArrayList;
import java.util.List;

public class TemperatureSeriesAnalysis {
    private static final int BASE_SIZE = 6, INCREMENT = 2;
    private static final double MIN_TEMP = -273.;
    private double[] temperatureSeries;
    private int size;

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries = new double[BASE_SIZE];
        this.size = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) throws InputMismatchException {
        this.temperatureSeries = new double[Math.max(BASE_SIZE, temperatureSeries.length)];
        for (double temp : temperatureSeries) {
            if (temp > MIN_TEMP) {
                this.temperatureSeries[size++] = temp;
            } else {
                this.size = 0;
                throw new InputMismatchException("Temperature less then " + MIN_TEMP + " is not possible!");
            }
        }
    }


    public int getCapacity() {
        return temperatureSeries.length;
    }

    public int getSize() {
        return size;
    }

    public double average() throws IllegalArgumentException {
        if (size == 0) {
            throw new IllegalArgumentException("Empty temperature series!");
        }
        double res = 0.;
        for (int i=0; i<size; ++i) {
            res += temperatureSeries[i];
        }
        return res / size;
    }

    public double deviation() throws IllegalArgumentException {
        if (size == 0) {
            throw new IllegalArgumentException("Empty temperature series!");
        }
        double res = 0., average = this.average();
        for (int i=0; i<size; ++i) {
            double temperature = temperatureSeries[i];
            double temp = temperature-average;
            res += temp*temp;
        }
        return res / size;
    }

    public double min() throws IllegalArgumentException {
        if (size == 0) {
            throw new IllegalArgumentException("Empty temperature series!");
        }
        double res = Double.POSITIVE_INFINITY;
        for (int i=0; i<size; ++i) {
            if (res > temperatureSeries[i]) {
                res = temperatureSeries[i];
            }
        }
        return res;
    }

    public double max() throws IllegalArgumentException {
        if (size == 0) {
            throw new IllegalArgumentException("Empty temperature series!");
        }
        double res = Double.NEGATIVE_INFINITY;
        for (int i=0; i<size; ++i) {
            if (res < temperatureSeries[i]) {
                res = temperatureSeries[i];
            }
        }
        return res;
    }

    public double findTempClosestToZero() throws IllegalArgumentException {
        return findTempClosestToValue(0.);
    }

    public double findTempClosestToValue(double tempValue) throws IllegalArgumentException {
        if (size == 0) {
            throw new IllegalArgumentException("Empty temperature series!");
        }
        double dist = Double.POSITIVE_INFINITY, res = Double.NEGATIVE_INFINITY;
        for (int i=0; i<size; ++i) {
            double temp = temperatureSeries[i];
            double distNow = Math.abs(temp - tempValue);
            if (Math.abs(distNow - dist) < 1e-4 && res < temp) {
                res = temp;
            } else if (distNow < dist) {
                dist = distNow;
                res = temp;
            }
        }
        return res;
    }

    public double[] findTempsLessThen(double tempValue) {
        List<Double> res = new ArrayList<>();
        for (int i=0; i<size; ++i) {
            if (temperatureSeries[i] < tempValue) {
                res.add(temperatureSeries[i]);
            }
        }
        double[] real = new double[res.size()];
        for (int i=0; i < res.size(); ++i) {
            real[i] = res.get(i);
        }
        return real;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        List<Double> res = new ArrayList<>();
        for (int i=0; i<size; ++i) {
            if (temperatureSeries[i] > tempValue) {
                res.add(temperatureSeries[i]);
            }
        }
        double[] real = new double[res.size()];
        for (int i=0; i < res.size(); ++i) {
            real[i] = res.get(i);
        }
        return real;
    }

    public TempSummaryStatistics summaryStatistics() throws IllegalArgumentException {
        return new TempSummaryStatistics(this);
    }

    public int addTemps(double... temps) throws InputMismatchException {
        int minSize = size + temps.length;
        if (temperatureSeries.length < minSize) {
            double[] tmp = new double[minSize * INCREMENT];
            System.arraycopy(temperatureSeries, 0, tmp, 0, size);
            temperatureSeries  = tmp;
        }
        int add=0;
        for (double temp : temps) {
            if (temp > MIN_TEMP) {
                temperatureSeries[size++] = temp;
                ++add;
            } else {
                size -= add;
                throw new InputMismatchException("Temperature less then " + MIN_TEMP + " is not possible!");
            }
        }
        return add;
    }
}
