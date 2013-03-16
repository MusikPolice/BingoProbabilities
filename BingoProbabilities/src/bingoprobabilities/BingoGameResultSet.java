package bingoprobabilities;
import java.util.List;

/**
 *
 * @author jfritz
 */
public class BingoGameResultSet {
    private final int numCardsActive;
    private final int numBingosAvailable;
    private double mean = 0;
    private double standardDeviation = 0;

    public BingoGameResultSet(int numCardsActive, int numBingosAvailable, List<Integer> numBallsCalledInEachGame) {
        this.numCardsActive = numCardsActive;
        this.numBingosAvailable = numBingosAvailable;
        this.mean = calculateMean(numBallsCalledInEachGame);
        this.standardDeviation = calculateStandardDeviation(numBallsCalledInEachGame);
    }

    public int getNumCardsActive() {
        return numCardsActive;
    }

    public int getNumBingosAvailable() {
        return numBingosAvailable;
    }

    public double getMeanNumberOfBallsCalled() {
        return mean;
    }

    public double getStandardDeviationOfNumberOfBallsCalled() {
        return standardDeviation;
    }
    
    private double calculateMean(List<Integer> data) {
        // mean is just an average
        double m = 0;
        final int n = data.size();
        if (n < 2) {
            return Double.NaN;
        }
        for (int i = 0; i < n; i++) {
            m += data.get(i);
        }
        return m / (double)n;
    }

    private double calculateStandardDeviation(List<Integer> data) {
        // standard deviation is sqrt of sum of (values-mean) squared divided by n
        double m = calculateMean(data);

        // calculate the sum of squares
        double sum = 0;
        for (int i = 0; i < data.size(); i++) {
            final double v = data.get(i) - m;
            sum += v * v;
        }
    
        return Math.sqrt(sum / data.size());
    }
}