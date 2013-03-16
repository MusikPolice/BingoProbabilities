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
        int NUM_CARDS = 50;
        int BINGOS_AVAILABLE = 40;
        int NUM_GAMES = 100;

        //run a bunch of fake games
        long startTime = System.currentTimeMillis();
        BingoGameResultSet resultSet = new BingoGameResultSet(NUM_CARDS, BINGOS_AVAILABLE);
        for (int i = 0; i < NUM_GAMES; i++) {
            resultSet.addResult(playBingoGame(NUM_CARDS, BINGOS_AVAILABLE));
        }
        System.out.println("Played " + String.valueOf(NUM_GAMES) + " games in " + String.valueOf(System.currentTimeMillis() - startTime) + "ms.");

        //process results
        System.out.println("On average, " + String.valueOf(Math.round(resultSet.getMeanNumBallsCalled())) + " balls were called.");
        System.out.println("Standard deviation is " + String.valueOf(resultSet.getStandardDeviationNumBallsCalled()));
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
