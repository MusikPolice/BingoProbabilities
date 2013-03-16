package bingoprobabilities;

/**
 * The results of running a single bingo game
 * @author jfritz
 */
public class BingoGameResult {
    private final int numBingosCalled;
    private final int numBallsCalled;

    public BingoGameResult(int numBingosCalled, int numBallsCalled) {
        this.numBingosCalled = numBingosCalled;
        this.numBallsCalled = numBallsCalled;
    }

    public int getNumBingosCalled() {
        return numBingosCalled;
    }

    public int getNumBallsCalled() {
        return numBallsCalled;
    }
}