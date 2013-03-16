package bingoprobabilities;

import bingo.Card;
import bingo.NumberPool;
import bingo.winconditions.HorizontalLineEvaluator;
import bingo.winconditions.WinConditionEvaluator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jfritz
 */
public final class TestHarness {

    public TestHarness() {
        int MIN_BINGOS_AVAILABLE = 1;
        int MAX_BINGOS_AVAILABLE = 400;
        int MIN_CARDS_IN_PLAY = 1;
        int MAX_CARDS_IN_PLAY = 800;
        int NUM_GAMES = 100;

        long startTime = System.currentTimeMillis();
        List<BingoGameResultSet> overallResults = new ArrayList<BingoGameResultSet>();
        for (int bingosAvailable = MIN_BINGOS_AVAILABLE; bingosAvailable <= MAX_BINGOS_AVAILABLE; bingosAvailable++) {
            for (int cardsInPlay = MIN_CARDS_IN_PLAY; cardsInPlay <= MAX_CARDS_IN_PLAY; cardsInPlay++) {
                BingoGameResultSet result = new BingoGameResultSet(cardsInPlay, bingosAvailable);
                for (int game = 0; game < NUM_GAMES; game++) {
                    result.addResult(playBingoGame(cardsInPlay, bingosAvailable));
                }
                overallResults.add(result);
                System.out.println(bingosAvailable + " Bingos Available + " + cardsInPlay + " Cards in Play = " + result.getMeanNumBallsCalled() + " Balls Called.");
            }
        }
        
        System.out.println("Gathered " + overallResults.size() + " sample sets over " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private BingoGameResult playBingoGame(int numCards, int numBingos) {
        if (numCards < numBingos) {
            throw new IllegalArgumentException("Not enough cards to get all of the bingos");
        }

        NumberPool balls = new NumberPool(1, 75);
        WinConditionEvaluator evaluator = new HorizontalLineEvaluator();

        List<Card> cards = new ArrayList<Card>();
        List<Card> finishedCards = new ArrayList<Card>();
        int bingos = 0;

        //create some random cards
        for (int i = 0; i < numCards; i++) {
            Card c = new Card();
            cards.add(c);
        }

        //pull all 75 balls
        while (balls.hasNext()) {
            int ball = balls.getNext();

            //daub each card. if the card has a bingo, remove it from the game
            for (Card c : cards) {
                c.daubCell(ball);

                if (evaluator.hasBingo(c)) {
                    finishedCards.add(c);
                    bingos++;
                }
            }

            for (Card c : finishedCards) {
                cards.remove(c);
            }

            //if there are no more cards in play, the game is over
            if (cards.isEmpty()) {
                break;
            }

            //if all of the bingos have been earned, the game is over
            if (bingos >= numBingos) {
                break;
            }
        }

        //return the game results to caller
        return new BingoGameResult(Math.min(bingos, numBingos), balls.getNumberOfPulls());
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestHarness t = new TestHarness();
    }
}
