package bingo;

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
public class CardTest {
    
    public CardTest() {
    }
    
    @Test
    public void randomCardTest() {
        Card c1 = new Card();
        Card c2 = new Card();
        
        Assert.assertNotEquals(c1, c2);
    }
    
    @Test
    public void copyConstructorTest() {
        Card c1 = new Card();
        Card c2 = new Card(c1);
        
        Assert.assertEquals(c1, c2);
    }
    
    @Test
    public void daubsArentCountedInEqualityCheck() {
        Card c1 = new Card();
        Card c2 = new Card(c1);
        
        for (int i = 0; i < Card.SIZE; i++) {
            c1.daubCell(i, 0);
            c2.daubCell(0, i);
        }
        
        Assert.assertEquals(c1, c2);
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
