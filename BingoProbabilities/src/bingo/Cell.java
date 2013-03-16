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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.number;
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
        final Cell other = (Cell) obj;
        if (this.number != other.number) {
            return false;
        }
        return true;
    }
}