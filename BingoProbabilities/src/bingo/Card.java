package bingo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a very simple bingo card.
 * @author jfritz
 */
public class Card {
    public static final int SIZE = 5;
    
    private final Cell[][] cells = new Cell[5][5];
    private final Map<Integer, Cell> cellIndex = new HashMap<Integer, Cell>();
    
    /**
     * Creates a new random bingo card
     */
    public Card() {
        for (int x = 0; x < Card.SIZE; x++) {
            NumberPool pool = new NumberPool((x*15) + 1, (x+1)*15);
            for (int y = 0; y < Card.SIZE; y++) {
                cells[x][y] = new Cell(pool.getNext());
                cellIndex.put(cells[x][y].getNumber(), cells[x][y]);
            }
        }
    }
    
    /**
     * Creates a new bingo card with the same cell layout as the specified card.
     * Note that daubs are not copied.
     * @param other 
     */
    public Card(Card other) {
        for (int x = 0; x < Card.SIZE; x++) {
            for (int y = 0; y < Card.SIZE; y++) {
                cells[x][y] = new Cell(other.getCell(x, y).getNumber());
                cellIndex.put(cells[x][y].getNumber(), cells[x][y]);
            }
        }
    }
    
    public boolean daubCell(int number) {
        Cell cell = cellIndex.get(number);
        if (cell != null) {
            cell.setDaubed(true);
            return true;
        }
        return false;
    }
    
    public boolean daubCell(int x, int y) {
        cells[x][y].setDaubed(true);
        return true;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Arrays.deepHashCode(this.cells);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (!Arrays.deepEquals(this.cells, other.cells)) {
            return false;
        }
        return true;
    }   
}