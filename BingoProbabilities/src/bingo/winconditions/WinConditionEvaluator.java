package bingo.winconditions;

import bingo.Card;

/**
 * Describes a WinCondition Evaluator
 * @author jfritz
 */
public interface WinConditionEvaluator {
    public boolean hasBingo(Card card);
}
