package bingo.winconditions;

import bingo.Card;

/**
 * Checks for a horizontal line of daubs
 * @author jfritz
 */
public class HorizontalLineEvaluator implements WinConditionEvaluator {

    @Override
    public boolean hasBingo(Card card) {
        for (int y = 0; y < Card.SIZE; y++) {
            boolean rowDaubed = true;
            for (int x = 0; x < Card.SIZE; x++) {
                if (!card.getCell(x, y).isDaubed()) {
                    rowDaubed = false;
                    break;
                }
            }
            
            if (rowDaubed) {
                return true;
            }
        }
        return false;
    }
}
