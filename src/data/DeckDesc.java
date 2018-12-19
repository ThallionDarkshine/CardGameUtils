package data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youngj14 on 12/11/2018.
 */
public abstract class DeckDesc<T extends CardDesc> {
    private Map<T, Integer> cards;
    private int size;
    private DeckDesc<T> sideDeck;
    private DeckRestrictions<T> restrictions;

    // TODO: Work with side deck in some way.

    public DeckDesc(DeckRestrictions<T> restrict) {
        cards = new HashMap<>();
        size = 0;
        restrictions = restrict;
        sideDeck = null;
    }

    public int getSize() {
        return size;
    }

    public int getCount(T card) {
        return cards.containsKey(card) ? cards.get(card) : 0;
    }

    public Map<T, Integer> getCards() {
        return Collections.unmodifiableMap(cards);
    }

    public boolean add(T card, int count) {
        boolean canAdd = restrictions.canAdd(this, card, count);

        if (canAdd) {
            int iCount = cards.containsKey(card) ? cards.get(card) : 0;

            cards.put(card, iCount + count);
        }

        return canAdd;
    }

    public boolean canAdd(T card, int count) {
        return restrictions.canAdd(this, card, count);
    }
}
