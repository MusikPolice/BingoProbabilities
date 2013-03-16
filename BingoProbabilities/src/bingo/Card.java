package bingo;

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
    
    public Card() {
        for (int x = 0; x < Card.SIZE; x++) {
            NumberPool pool = new NumberPool((x*15) + 1, (x+1)*15);
            for (int y = 0; y < Card.SIZE; y++) {
                cells[x][y] = new Cell(pool.getNext());
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
}
