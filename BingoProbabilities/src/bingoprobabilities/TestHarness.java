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
        List<Future<Double>> results;
        long startTime;
        for (int cardsInPlay = MIN_CARDS_IN_PLAY; cardsInPlay <= MAX_CARDS_IN_PLAY; cardsInPlay++) {

            startTime = System.currentTimeMillis();
            
            //run all of the bingo games for this number of cards with the help of a thread pool
            threadPool = Executors.newCachedThreadPool();
            results = new ArrayList<Future<Double>>();
            for (int bingosAvailable = MIN_BINGOS_AVAILABLE; bingosAvailable <= MAX_BINGOS_AVAILABLE; bingosAvailable++) {
                results.add(threadPool.submit(new BingoGameSimulator(NUM_GAMES, cardsInPlay, bingosAvailable)));
            }
            threadPool.shutdown();
            threadPool.awaitTermination(1, TimeUnit.HOURS);
            
            //dump results to file to ease memory footprint
            b = new StringBuffer();
            b.append(cardsInPlay);
            for (Future<Double> result : results) {
                b.append(",");
                b.append(Math.round(result.get()));
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

    private class BingoGameSimulator implements Callable<Double> {

        private final int numGames;
        private final int numCards;
        private final int numBingos;
        private final List<Card> cards;
        private final NumberPool balls;
        private final WinConditionEvaluator evaluator;

        public BingoGameSimulator(int numGames, int numCards, int numBingos) {
            this.numGames = numGames;
            this.numCards = numCards;
            this.numBingos = numBingos;
            this.cards = new ArrayList<Card>();
            this.balls = new NumberPool(1, 75);
            this.evaluator = new HorizontalLineEvaluator();
        }

        @Override
        public Double call() throws Exception {
            //create some random cards
            for (int i = 0; i < numCards; i++) {
                Card c = new Card();
                this.cards.add(c);
            }
            
            int sum = 0;
            for (int game = 0; game < numGames; game++) {
                sum += playBingo();
                
                //reset the cards and number pool between games
                for (Card c : this.cards) {
                    c.reset();
                }
                this.balls.reset();
            }
            return (double)sum/(double)numGames;
        }

        /**
         * Plays a game of bingo
         *
         * @return the number of balls that were called prior to the end of the
         * game
         */
        private int playBingo() {
            //pull all 75 balls
            int bingos = 0;
            boolean cardsInPlay;
            int ball;
            while (this.balls.hasNext()) {
                ball = this.balls.getNext();

                //daub each card. if the card has a bingo, remove it from the game
                cardsInPlay = false;
                for (Card c : this.cards) {
                    if (c.hasBingo()) {
                        continue;
                    } else {
                        cardsInPlay = true;
                    }
                    
                    c.daubCell(ball);
                    if (this.evaluator.hasBingo(c)) {
                        c.setHasBingo(true);
                        bingos++;
                    }
                }

                //if there are no more cards in play, the game is over
                if (!cardsInPlay) {
                    break;
                }

                //if all of the bingos have been earned, the game is over
                if (bingos >= this.numBingos) {
                    break;
                }
            }

            //return the game results to caller
            return this.balls.getNumberOfPulls();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        TestHarness t = new TestHarness();
    }
}
