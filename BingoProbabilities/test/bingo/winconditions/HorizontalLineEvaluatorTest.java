package bingo.winconditions;



import bingo.Card;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author jfritz
 */
public class HorizontalLineEvaluatorTest {
    
    private final WinConditionEvaluator evaluator;
    
    public HorizontalLineEvaluatorTest() {
        this.evaluator = new HorizontalLineEvaluator();
    }
    
    @Test
    public void noDaubs() {
        Card card = new Card();
        Assert.assertFalse(this.evaluator.hasBingo(card));
    }
    
    @Test
    public void verticalLine() {
        Card card = new Card();
        for (int y = 0; y < Card.SIZE; y++) {
            card.daubCell(0, y);
        }
        Assert.assertFalse(this.evaluator.hasBingo(card));
    }
    
    @Test
    public void firstLine() {
        Card card = new Card();
        for (int x = 0; x < Card.SIZE; x++) {
            card.daubCell(x, 0);
        }
        Assert.assertTrue(this.evaluator.hasBingo(card));
    }
    
    @Test
    public void secondLine() {
        Card card = new Card();
        for (int x = 0; x < Card.SIZE; x++) {
            card.daubCell(x, 1);
        }
        Assert.assertTrue(this.evaluator.hasBingo(card));
    }
    
    @Test
    public void thirdLine() {
        Card card = new Card();
        for (int x = 0; x < Card.SIZE; x++) {
            card.daubCell(x, 2);
        }
        Assert.assertTrue(this.evaluator.hasBingo(card));
    }
    
    @Test
    public void fourthLine() {
        Card card = new Card();
        for (int x = 0; x < Card.SIZE; x++) {
            card.daubCell(x, 3);
        }
        Assert.assertTrue(this.evaluator.hasBingo(card));
    }
    
    @Test
    public void fifthLine() {
        Card card = new Card();
        for (int x = 0; x < Card.SIZE; x++) {
            card.daubCell(x, 4);
        }
        Assert.assertTrue(this.evaluator.hasBingo(card));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
