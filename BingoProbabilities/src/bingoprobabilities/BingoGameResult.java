package bingoprobabilities;

/**
 * The results of running a single bingo game
 * @author jfritz
 */
public class BingoGameResult {
    private final int numCardsActive;
    private final int numBingosAvailable;
    private final int numBingosCalled;
    private final int numBallsCalled;

    public BingoGameResult(int numCardsActive, int numBingosAvailable, int numBingosCalled, int numBallsCalled) {
        this.numCardsActive = numCardsActive;
        this.numBingosAvailable = numBingosAvailable;
        this.numBingosCalled = numBingosCalled;
        this.numBallsCalled = numBallsCalled;
    }

    public int getNumCardsActive() {
        return numCardsActive;
    }

    public int getNumBingosAvailable() {
        return numBingosAvailable;
    }    

    public int getNumBingosCalled() {
        return numBingosCalled;
    }

    public int getNumBallsCalled() {
        return numBallsCalled;
    }
}
