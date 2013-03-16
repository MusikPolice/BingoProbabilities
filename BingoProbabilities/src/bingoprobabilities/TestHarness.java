package bingoprobabilities;

import bingo.Card;
import bingo.NumberPool;
import bingo.winconditions.HorizontalLineEvaluator;
import bingo.winconditions.WinConditionEvaluator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author jfritz
 */
public final class TestHarness {

    public TestHarness() throws InterruptedException, ExecutionException {
        int MIN_BINGOS_AVAILABLE = 1;
        int MAX_BINGOS_AVAILABLE = 100;
        int MIN_CARDS_IN_PLAY = 1;
        int MAX_CARDS_IN_PLAY = 100;
        int NUM_GAMES = 100;
        long runTime = 0;

        //print a header
        StringBuffer b = new StringBuffer();
        b.append("cards/bingos");
        for (int bingosAvailable = MIN_BINGOS_AVAILABLE; bingosAvailable <= MAX_BINGOS_AVAILABLE; bingosAvailable++) {
            b.append(",");
            b.append(bingosAvailable);
        }
        writeToFile(b.toString());

        //each line of the output file will be for a different number of cards
        ExecutorService threadPool;
        List<Future<BingoGameResultSet>> results;
        long startTime;
        for (int cardsInPlay = MIN_CARDS_IN_PLAY; cardsInPlay <= MAX_CARDS_IN_PLAY; cardsInPlay++) {

            startTime = System.currentTimeMillis();
            
            //run all of the bingo games for this number of cards with the help of a thread pool
            threadPool = Executors.newCachedThreadPool();
            results = new ArrayList<Future<BingoGameResultSet>>();
            for (int bingosAvailable = MIN_BINGOS_AVAILABLE; bingosAvailable <= MAX_BINGOS_AVAILABLE; bingosAvailable++) {
                results.add(threadPool.submit(new BingoGameSimulator(NUM_GAMES, cardsInPlay, bingosAvailable)));
            }
            threadPool.shutdown();
            threadPool.awaitTermination(1, TimeUnit.HOURS);
            
            //dump results to file to ease memory footprint
            b = new StringBuffer();
            b.append(cardsInPlay);
            for (Future<BingoGameResultSet> result : results) {
                b.append(",");
                b.append(result.get().getMeanNumberOfBallsCalled());
            }
            writeToFile(b.toString());
            
            runTime += (System.currentTimeMillis() - startTime);
            System.out.println("Calculated results for " + cardsInPlay + " cards in play in " + (System.currentTimeMillis() - startTime) + "ms");
        }
        
        System.out.println("Total run time is " + runTime + "ms");
    }

    private void writeToFile(String text) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/home/jfritz/Desktop/results.txt"), true));
            bw.write(text);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            List<Integer> numBallsCalledInEachGame = new ArrayList<Integer>();
            for (int game = 0; game < numGames; game++) {
                numBallsCalledInEachGame.add(playBingo());
            }
            return new BingoGameResultSet(numCards, numBingos, numBallsCalledInEachGame);
        }

        /**
         * Plays a game of bingo
         *
         * @return the number of balls that were called prior to the end of the
         * game
         */
        private int playBingo() {
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
            return balls.getNumberOfPulls();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        TestHarness t = new TestHarness();
    }
}
