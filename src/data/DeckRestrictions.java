package data;

import java.util.Map;

/**
 * Created by youngj14 on 12/11/2018.
 */
public abstract class DeckRestrictions<T extends CardDesc> {
    public abstract int cardLimit(T card);
    public abstract int minSize();
    public abstract int maxSize();
    public abstract boolean isValid(T card, DeckDesc<T> deck);
    public abstract boolean hasSideDeck();

    public boolean canAdd(DeckDesc<T> deck, T card, int count) {
        if (card == null) return false;

        if (!isValid(card, deck)) return false;
        if (cardLimit(card) >= 0 && cardLimit(card) < count) return false;
        if (maxSize() >= 0 && deck.getSize() > maxSize() - count) return false;
        if (cardLimit(card) >= 0 && deck.getCount(card) > cardLimit(card) - count) return false;

        return true;
    }

    public boolean validDeck(DeckDesc<T> deck) {
        if (deck.getSize() < minSize() || deck.getSize() > maxSize()) return false;

        for (Map.Entry<T, Integer> cardEntry : deck.getCards().entrySet()) {
            if (cardEntry.getValue() > cardLimit(cardEntry.getKey())) return false;
        }

        return true;
    }
}
