package bingo;

/**
 * Represents a very simple bingo card.
 * @author jfritz
 */
public class Card {
    public static final int SIZE = 5;
    private final Cell[][] cells = new Cell[5][5];
    
    public Card() {
        for (int x = 0; x < Card.SIZE; x++) {
            NumberPool pool = new NumberPool((x*15) + 1, (x+1)*15);
            for (int y = 0; y < Card.SIZE; y++) {
                cells[x][y] = new Cell(pool.getNext());
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }
}
