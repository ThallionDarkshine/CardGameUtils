package analysis;

import data.CardDesc;
import data.DeckDesc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by youngj14 on 12/13/2018.
 */
public class CardPairingData<T extends CardDesc> {
    private T c1, c2;
    private CardData<T> d1, d2;
    private int validDecks;
    private int validWithC1, validWithC2;
    private int usage;
    private float adjustedUsage;

    public CardPairingData(T c1, T c2) {
        this.c1 = c1;
        this.c2 = c2;
        validDecks = 0;
        validWithC1 = validWithC2 = 0;
        usage = 0;
        adjustedUsage = 0f;
        d1 = new CardData<>(c1);
        d2 = new CardData<>(c2);
    }

    public void addDeckData(DeckDesc<T> deck) {
        d1.addDeckData(deck);
        d2.addDeckData(deck);

        boolean valid = deck.canAdd(c1, 0) && deck.canAdd(c2, 0);
        boolean used = deck.getCards().containsKey(c1) && deck.getCards().containsKey(c2);

        if (valid) {
            ++validDecks;

            if (deck.getCards().containsKey(c1)) ++validWithC1;
            if (deck.getCards().containsKey(c2)) ++validWithC2;
        }

        if (used) {
            float au = 1.0f;

            au *= deck.getCount(c1) / (float) deck.getSize();
            au *= deck.getCount(c2) / (float) deck.getSize();

            adjustedUsage += au;
            ++usage;
        }
    }

    public float getUsageProportion() {
        return usage / validDecks;
    }

    public void outputBasicAnalysis() {
        float c1_w_c2, c2_w_c1;

        c1_w_c2 = usage / validWithC2;
        c2_w_c1 = usage / validWithC1;

        System.out.println("Used together in: " + getUsageProportion());
        System.out.println("Card 1 used in: " + d1.getUsageProportion());
        System.out.println("Card 2 used in: " + d2.getUsageProportion());
        System.out.println("Card 1 used in w/Card 2: " + c1_w_c2);
        System.out.println("Card 1 used in w/Card 2: " + c2_w_c1);
    }
}
