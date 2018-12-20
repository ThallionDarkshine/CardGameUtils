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
        if (!isValid(card, deck)) return false;
        if (cardLimit(card) < count) return false;
        if (deck.getSize() > maxSize() - count) return false;
        if (deck.getCount(card) > cardLimit(card) - count) return false;

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
