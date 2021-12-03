package ua.edu.ucu.tempseries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

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

    public double average() throws IllegalArgumentException {
        try {
            return Arrays.stream(temperatureSeries).sum() / size;
        } catch (ArithmeticException err) {
            throw new IllegalArgumentException("Empty temperature series!");
        }
    }

    public double deviation() throws IllegalArgumentException {
        try {
            double res = 0., average = this.average();
            for (double temperature : temperatureSeries) {
                double temp = temperature-average;
                res += temp*temp;
            }
            return res / size;
        } catch (ArithmeticException err) {
            throw new IllegalArgumentException("Empty temperature series!");
        }
    }

    public double min() throws IllegalArgumentException {
        OptionalDouble res = Arrays.stream(temperatureSeries).min();
        if (res.isPresent()) {
            return res.getAsDouble();
        } else {
            throw new IllegalArgumentException("Empty temperature series!");
        }
    }

    public double max() throws IllegalArgumentException {
        OptionalDouble res = Arrays.stream(temperatureSeries).max();
        if (res.isPresent()) {
            return res.getAsDouble();
        } else {
            throw new IllegalArgumentException("Empty temperature series!");
        }
    }

    public double findTempClosestToZero() throws IllegalArgumentException {
        return findTempClosestToValue(0.);
    }

    public double findTempClosestToValue(double tempValue) throws IllegalArgumentException {
        if (temperatureSeries.length == 0)
            throw new IllegalArgumentException("Empty temperature series!");
        double dist = Double.POSITIVE_INFINITY, res = Double.NEGATIVE_INFINITY;
        for (double temp  : temperatureSeries) {
            double distNow = Math.abs(temp-tempValue);
            if (Math.abs(distNow - dist) < 1e-4) {
                res = Double.max(res, temp);
            } else if (distNow < dist) {
                dist = distNow;
                res = temp;
            }
        }
        return res;
    }

    public double[] findTempsLessThen(double tempValue) {
        List<Double> res = new ArrayList<>();
        for (double temp : temperatureSeries) {
            if (temp < tempValue)
                res.add(temp);
        }
        return res.stream().mapToDouble(d -> d).toArray();
    }

    public double[] findTempsGreaterThen(double tempValue) {
        List<Double> res = new ArrayList<>();
        for (double temp : temperatureSeries) {
            if (temp > tempValue)
                res.add(temp);
        }
        return res.stream().mapToDouble(d -> d).toArray();
    }

    public TempSummaryStatistics summaryStatistics() throws IllegalArgumentException {
        return new TempSummaryStatistics(this);
    }

    public int addTemps(double... temps) throws InputMismatchException {
        int minSize = size + temps.length;
        if (temperatureSeries.length <= minSize) {
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
