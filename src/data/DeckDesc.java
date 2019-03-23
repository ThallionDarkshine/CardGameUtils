package data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youngj14 on 12/11/2018.
 */
public abstract class DeckDesc<T extends CardDesc> {
    protected String name;
    protected Map<T, Integer> cards;
    protected int size;
    protected DeckDesc<T> sideDeck; // TODO: Work with side deck in some way.
    protected DeckRestrictions<T> restrictions;

    public DeckDesc(DeckRestrictions<T> restrict, String name) {
        this.name = name;
        cards = new HashMap<>();
        size = 0;
        restrictions = restrict;
        sideDeck = null;
    }

    public String getName() {
        return name;
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
