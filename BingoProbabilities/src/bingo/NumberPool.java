package bingo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A pool of numbers between 1 and the specified value.
 * Each number in the specified range will appear in the pool once.
 * @author jfritz
 */
public class NumberPool {
    private final List<Integer> pool;
    private int index = -1;
    
    /**
     * Create a new pool with a range lower to upper inclusive
     * @param n 
     */
    public NumberPool(int lower, int upper) {
        this.pool = new ArrayList<Integer>();
        for (int i = lower; i <= upper; i++) {
            this.pool.add(i);
        }
        Collections.shuffle(pool);
    }
    
    /**
     * Get the next number from the pool.
     * @return 
     */
    public int getNext() {
        index++;
        return this.pool.get(index);
    }
    
    /**
     * Returns true if the pool contains more numbers
     * @return 
     */
    public boolean hasNext() {
        return index < this.pool.size();
    }
    
    /**
     * Returns the number of values that have been pulled from this pool since it was created.
     * @return 
     */
    public int getNumberOfPulls() {
        return index + 1;
    }
}
