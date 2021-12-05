package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class TemperatureSeriesAnalysisTest {
    final String msg = "%^$&^!#@%$!&@$#^!@&$#%^!%^@#";
    final double delta = 0.000001;
    TemperatureSeriesAnalysis analysis, analysis0, analysis1;
    @Before
    public void setUp() throws InputMismatchException {
        analysis0 = new TemperatureSeriesAnalysis(new double[]{});
        analysis1 = new TemperatureSeriesAnalysis(new double[]{100.});
        analysis = new TemperatureSeriesAnalysis(new double[]{-1.0, 1.});
    }

    @Test(expected = InputMismatchException.class)
    public void testConstructor() throws InputMismatchException {
        new TemperatureSeriesAnalysis(new double[]{-662.0});
    }

    @Test
    public void testAverage() throws IllegalArgumentException {
        assertEquals(0., analysis.average(), delta);
        assertEquals(100., analysis1.average(), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() throws IllegalArgumentException {
        analysis0.average();
    }

    @Test
    public void testDeviation() throws IllegalArgumentException {
        assertEquals(0., analysis1.deviation(), delta);
        assertEquals(1., analysis.deviation(), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeviationWithEmptyArray() throws IllegalArgumentException {
        analysis0.deviation();
    }

    @Test
    public void testMin() throws IllegalArgumentException {
        assertEquals(100., analysis1.min(), delta);
        assertEquals(-1., analysis.min(), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinWithEmptyArray() throws IllegalArgumentException {
        analysis0.min();
    }

    @Test
    public void testMax() throws IllegalArgumentException {
        assertEquals(100., analysis1.max(), delta);
        assertEquals(1., analysis.max(), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxWithEmptyArray() throws IllegalArgumentException {
        analysis0.max();
    }

    @Test
    public void testFindTempClosestToZero() throws IllegalArgumentException {
        assertEquals(100., analysis1.findTempClosestToZero(), delta);
        assertEquals(1., analysis.findTempClosestToZero(), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempClosestToZeroWithEmptyArray() throws IllegalArgumentException {
        analysis0.findTempClosestToZero();
    }

    @Test
    public void testFindTempClosestToValue() throws IllegalArgumentException {
        assertEquals(100., analysis1.findTempClosestToValue(0.5), delta);
        assertEquals(-1., analysis.findTempClosestToValue(-0.00005), delta);
        assertEquals(1., analysis.findTempClosestToValue(0.5), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempClosestToValueWithEmptyArray() throws IllegalArgumentException {
        analysis0.findTempClosestToValue(10.);
    }

    @Test
    public void testFindTempsLessThen() {
        assertEquals(0, analysis1.findTempsLessThen(100).length);
        double[] res = analysis1.findTempsLessThen(101);
        assertEquals(1, res.length);
        assertEquals(100, res[0], delta);
        res = analysis.findTempsLessThen(2);
        assertEquals(2, res.length);
        assertEquals(-1., res[0], delta);
        assertEquals(1., res[1], delta);
    }

    @Test
    public void testFindTempsGreaterThen() {
        assertEquals(0, analysis1.findTempsGreaterThen(100).length);
        double[] res = analysis1.findTempsGreaterThen(99);
        assertEquals(1, res.length);
        assertEquals(100, res[0], delta);
        res = analysis.findTempsGreaterThen(-2);
        assertEquals(2, res.length);
        assertEquals(-1., res[0], delta);
        assertEquals(1., res[1], delta);
    }


    @Test
    public void testSummaryStatistics() throws IllegalArgumentException {
        TempSummaryStatistics res = analysis.summaryStatistics();
        assertEquals(analysis.average(), res.avgTemp, delta);
        assertEquals(analysis.deviation(), res.devTemp, delta);
        assertEquals(analysis.min(), res.minTemp, delta);
        assertEquals(analysis.max(), res.maxTemp, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSummaryStatisticsWithEmptyArray() throws IllegalArgumentException {
        TempSummaryStatistics res = analysis0.summaryStatistics();
    }

    @Test
    public void testAddTemps() throws InputMismatchException {
        assertEquals(2, analysis.getSize());
        assertEquals(6, analysis.getCapacity());
        analysis.addTemps(1.);
        assertEquals(6, analysis.getCapacity());
        analysis.addTemps(1., 1., 1.);
        assertEquals(6, analysis.getSize());
        assertEquals(6, analysis.getCapacity());
        analysis.addTemps(1.);
        assertEquals(7, analysis.getSize());
        assertEquals(14, analysis.getCapacity());
    }
}
