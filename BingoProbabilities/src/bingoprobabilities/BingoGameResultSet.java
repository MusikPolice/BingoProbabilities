package bingoprobabilities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jfritz
 */
public class BingoGameResultSet {
    private final int numCardsActive;
    private final int numBingosAvailable;
    private final List<BingoGameResult> results = new ArrayList<BingoGameResult>();

    public BingoGameResultSet(int numCardsActive, int numBingosAvailable) {
        this.numCardsActive = numCardsActive;
        this.numBingosAvailable = numBingosAvailable;
    }

    public int getNumCardsActive() {
        return numCardsActive;
    }

    public int getNumBingosAvailable() {
        return numBingosAvailable;
    }
    
    public void addResult(BingoGameResult result) {
        this.results.add(result);
    }
    
    public double getMeanNumBallsCalled() {
        List<Integer> ballsCalled = new ArrayList<Integer>();
        for (BingoGameResult result : results) {
            ballsCalled.add(result.getNumBallsCalled());
        }
        return calculateMean(ballsCalled);
    }
    
    public double getStandardDeviationNumBallsCalled() {
        List<Integer> ballsCalled = new ArrayList<Integer>();
        for (BingoGameResult result : results) {
            ballsCalled.add(result.getNumBallsCalled());
        }
        return calculateStandardDeviation(ballsCalled);
    }
    
    private double calculateMean(List<Integer> data) {
        // mean is just an average
        double mean = 0;
        final int n = data.size();
        if (n < 2) {
            return Double.NaN;
        }
        for (int i = 0; i < n; i++) {
            mean += data.get(i);
        }
        return mean / (double)n;
    }

    private double calculateStandardDeviation(List<Integer> data) {
        // standard deviation is sqrt of sum of (values-mean) squared divided by n
        double mean = calculateMean(data);

        // calculate the sum of squares
        double sum = 0;
        for (int i = 0; i < data.size(); i++) {
            final double v = data.get(i) - mean;
            sum += v * v;
        }
    
        return Math.sqrt(sum / data.size());
    }
}