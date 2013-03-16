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

    public BingoGameResultSet(int numCardsActive, int numBingosAvailable, List<Integer> numBallsCalledInEachGame) {
        this.numCardsActive = numCardsActive;
        this.numBingosAvailable = numBingosAvailable;
        this.mean = calculateMean(numBallsCalledInEachGame);
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
}