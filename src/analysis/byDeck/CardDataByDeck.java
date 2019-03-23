package analysis.byDeck;

import data.CardDesc;
import data.DeckDesc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youngj14 on 12/13/2018.
 */
public class CardDataByDeck<T extends CardDesc> {
    private T card;
    private int usage;
    private float adjustedUsage;
    private int validDecks;
    private List<Integer> copies;

    public CardDataByDeck(T card) {
        this.card = card;
        usage = 0;
        adjustedUsage = 0f;
        validDecks = 0;
        copies = new ArrayList<>();
    }

    public void addDeckData(DeckDesc<T> deck) {
        if (deck.getCards().containsKey(card)) {
            ++usage;
            adjustedUsage += deck.getCount(card) / (float) deck.getSize();
            copies.add(deck.getCount(card));
        }

        if (deck.canAdd(card, 0)) {
            ++validDecks;
        }
    }

    public int getUsage() {
        return usage;
    }

    public float getUsageProportion() {
        return usage / (float) validDecks;
    }

    public float getAdjustedUsageProportion() {
        return adjustedUsage / validDecks;
    }
}
