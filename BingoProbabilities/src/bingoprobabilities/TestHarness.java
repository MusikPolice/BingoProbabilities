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
    
    public TestHarness() {
        NumberPool balls = new NumberPool(1, 75);
        WinConditionEvaluator evaluator = new HorizontalLineEvaluator();
        
        List<Card> cards = new ArrayList<Card>();
        List<Card> finishedCards = new ArrayList<Card>();
        
        //create 50 random cards
        for (int i = 0; i < 50; i++) {
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
                }
            }
            
            for (Card c : finishedCards) {
                cards.remove(c);
            }
            
            //if there are no more cards in play, the game is over
            if (cards.isEmpty()) {
                break;
            }
        }
        
        //how many balls are left?
        System.out.println("Game ended after " + String.valueOf(balls.getNumberOfPulls()) + " balls were pulled");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestHarness t = new TestHarness();
    }
}
