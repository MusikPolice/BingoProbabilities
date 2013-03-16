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
public class TestHarness {
    
    public static final int NUM_CARDS = 50;
    public static final int BINGOS_AVAILABLE = 40;
    
    public TestHarness() {
        if (NUM_CARDS < BINGOS_AVAILABLE) {
            throw new IllegalArgumentException("Not enough cards to get all of the bingos");
        }
        
        NumberPool balls = new NumberPool(1, 75);
        WinConditionEvaluator evaluator = new HorizontalLineEvaluator();
        
        List<Card> cards = new ArrayList<Card>();
        List<Card> finishedCards = new ArrayList<Card>();
        int bingos = 0;
        
        //create some random cards
        for (int i = 0; i < NUM_CARDS; i++) {
            Card c = new Card();
            cards.add(c);
        }
        
        //pull all 75 balls
        while (balls.hasNext()) {
            int ball = balls.getNext();
            
            //daub each card. if the card has a bingo, remove it from the game
            for(Card c : cards) {    
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
            if (bingos >= BINGOS_AVAILABLE) {
                break;
            }
        }
        
        //how many balls are left?
        System.out.println("Game ended after " + String.valueOf(balls.getNumberOfPulls()) + " balls with " + String.valueOf(Math.min(bingos, BINGOS_AVAILABLE)) + " bingos");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestHarness t = new TestHarness();
    }
}
