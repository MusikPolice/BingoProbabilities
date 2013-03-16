package bingo;

/**
 * Represents a cell on a bingo card
 * @author jfritz
 */
public class Cell {
    private final int number;
    private boolean daubed = false;

    public Cell(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public boolean isDaubed() {
        return daubed;
    }

    public void setDaubed(boolean daubed) {
        this.daubed = daubed;
    }
}