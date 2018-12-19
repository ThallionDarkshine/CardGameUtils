package analysis;

import data.CardDesc;
import data.DeckDesc;
import org.apache.commons.math3.distribution.NormalDistribution;

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
        float s_c1_w_c2, s_c2_w_c1;
        float a_s_c1_w_c2, a_s_c2_w_c1;
        float s;
        float z, se, p;
        float p_c1, p_c2;
        NormalDistribution distr = new NormalDistribution(0, 1);

        c1_w_c2 = usage / validWithC2;
        c2_w_c1 = usage / validWithC1;

        s_c1_w_c2 = c1_w_c2 / d1.getUsageProportion();
        s_c2_w_c1 = c2_w_c1 / d2.getUsageProportion();

        a_s_c1_w_c2 = s_c1_w_c2 - 1;
        a_s_c2_w_c1 = s_c2_w_c1 - 1;

        s = (a_s_c1_w_c2 * validWithC2 + a_s_c2_w_c1 * validWithC1) / (validWithC1 + validWithC2 - validDecks);

        p = 2 * usage / (validDecks + validWithC2);
        se = (float) Math.sqrt((double) (p * (1 - p) * (1 / validDecks + 1 / validWithC2)));
        z = -Math.abs((c1_w_c2 - getUsageProportion()) / se);
        p_c1 = (float) distr.cumulativeProbability(z);

        p = 2 * usage / (validDecks + validWithC1);
        se = (float) Math.sqrt((double) (p * (1 - p) * (1 / validDecks + 1 / validWithC1)));
        z = -Math.abs((c2_w_c1 - getUsageProportion()) / se);
        p_c2 = (float) distr.cumulativeProbability(z);

        System.out.println("Used together in: " + getUsageProportion());
        System.out.println("Card 1 used in: " + d1.getUsageProportion());
        System.out.println("Card 2 used in: " + d2.getUsageProportion());
        System.out.println("Card 1 used in w/Card 2: " + c1_w_c2);
        System.out.println("Card 1 used in w/Card 2: " + c2_w_c1);
        System.out.println("Card 1 synergy w/Card 2: " + s_c1_w_c2);
        System.out.println("Card 2 synergy w/Card 1: " + s_c2_w_c1);
        System.out.println("Card 1 adjusted synergy w/Card 2: " + a_s_c1_w_c2);
        System.out.println("Card 2 adjusted synergy w/Card 1: " + a_s_c2_w_c1);
        System.out.println("Magical synergy number: " + s);
        System.out.println("Card 1 p-value: " + p_c1);
        System.out.println("Card 2 p-value: " + p_c2);
    }
}
