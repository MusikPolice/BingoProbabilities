package bingoprobabilities;

import bingo.Card;
import bingo.NumberPool;
import bingo.winconditions.HorizontalLineEvaluator;
import bingo.winconditions.WinConditionEvaluator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author jfritz
 */
public final class TestHarness {

    public TestHarness() throws InterruptedException {
        int MIN_BINGOS_AVAILABLE = 1;
        int MAX_BINGOS_AVAILABLE = 400;
        int MIN_CARDS_IN_PLAY = 1;
        int MAX_CARDS_IN_PLAY = 800;
        int NUM_GAMES = 100;

        long startTime = System.currentTimeMillis();
        
        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<Future<BingoGameResultSet>> results = new ArrayList<Future<BingoGameResultSet>>();
        for (int bingosAvailable = MIN_BINGOS_AVAILABLE; bingosAvailable <= MAX_BINGOS_AVAILABLE; bingosAvailable++) {
            for (int cardsInPlay = MIN_CARDS_IN_PLAY; cardsInPlay <= MAX_CARDS_IN_PLAY; cardsInPlay++) {
                results.add(threadPool.submit(new BingoGameSimulator(NUM_GAMES, cardsInPlay, bingosAvailable)));
            }
        }
        
        threadPool.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("Gathered " + results.size() + " sample sets over " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private class BingoGameSimulator implements Callable<BingoGameResultSet> {

        private final int numGames;
        private final int numCards;
        private final int numBingos;

        public BingoGameSimulator(int numGames, int numCards, int numBingos) {
            this.numGames = numGames;
            this.numCards = numCards;
            this.numBingos = numBingos;
        }

        @Override
        public BingoGameResultSet call() throws Exception {
            BingoGameResultSet resultSet = new BingoGameResultSet(numCards, numBingos);
            for (int game = 0; game < numGames; game++) {
                resultSet.addResult(playBingo());
            }
            
            return resultSet;
        }
        
        private BingoGameResult playBingo() {
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
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        TestHarness t = new TestHarness();
    }
}
