package analysis.byDeck;

import data.CardDesc;
import data.DeckDesc;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * Created by youngj14 on 12/13/2018.
 */
public class CardPairingDataByDeck<T extends CardDesc> {
    private T c1, c2;
    private CardDataByDeck<T> d1, d2;
    private int validDecks;
    private int validWithC1, validWithC2;
    private int usage;
    private float adjustedUsage;

    public CardPairingDataByDeck(T c1, T c2) {
        this.c1 = c1;
        this.c2 = c2;
        validDecks = 0;
        validWithC1 = validWithC2 = 0;
        usage = 0;
        adjustedUsage = 0f;
        d1 = new CardDataByDeck<>(c1);
        d2 = new CardDataByDeck<>(c2);
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
        return usage / (float) validDecks;
    }

    public void outputBasicAnalysis() {
        if (usage < 10) return;

        float c1_w_c2 = 0, c2_w_c1 = 0;
        float s_c1_w_c2 = 0, s_c2_w_c1 = 0;
        float a_s_c1_w_c2 = 0, a_s_c2_w_c1 = 0;
        float s = 0;
        float z, se, p;
        float p_c1 = 0, p_c2 = 0;
        NormalDistribution distr = new NormalDistribution(0, 1);

        if (validWithC2 != 0) {
            c1_w_c2 = usage / (float) validWithC2;
            s_c1_w_c2 = c1_w_c2 / d1.getUsageProportion();
            a_s_c1_w_c2 = s_c1_w_c2 - 1;

            p = 2 * usage / (float) (validDecks + validWithC2);
            se = (float) Math.sqrt((double) (p * (1 - p) * (1f / validDecks + 1f / validWithC2)));
            z = -Math.abs((c1_w_c2 - getUsageProportion()) / se);
            System.out.println(p + ", " + se + ", " + z);
            p_c1 = (float) distr.cumulativeProbability(z);
        }

        if (validWithC1 != 0) {
            c2_w_c1 = usage / (float) validWithC1;
            s_c2_w_c1 = c2_w_c1 / d2.getUsageProportion();
            a_s_c2_w_c1 = s_c2_w_c1 - 1;

            p = 2 * usage / (float) (validDecks + validWithC1);
            se = (float) Math.sqrt((double) (p * (1 - p) * (1f / validDecks + 1f / validWithC1)));
            z = -Math.abs((c2_w_c1 - getUsageProportion()) / se);
            System.out.println(p + ", " + se + ", " + z);
            p_c2 = (float) distr.cumulativeProbability(z);
        }

        if (validWithC1 != 0 && validWithC2 != 0) {
            s = (a_s_c1_w_c2 * validWithC2 + a_s_c2_w_c1 * validWithC1) / (validWithC1 + validWithC2);
        }

        System.out.println();
        System.out.println(c1.getName() + " and " + c2.getName());
        System.out.println("-------------------------");
        System.out.println("Used together in: " + getUsageProportion());
        System.out.println("Card 1 used in: " + d1.getUsageProportion());

        if (validWithC2 != 0) {
            System.out.println("Card 1 used in w/Card 2: " + c1_w_c2);
            System.out.println("Card 1 synergy w/Card 2: " + s_c1_w_c2);
            System.out.println("Card 1 adjusted synergy w/Card 2: " + a_s_c1_w_c2);
            System.out.println("Card 1 p-value: " + p_c1);
        }

        System.out.println("Card 2 used in: " + d2.getUsageProportion());

        if (validWithC1 != 0) {
            System.out.println("Card 2 used in w/Card 1: " + c2_w_c1);
            System.out.println("Card 2 synergy w/Card 1: " + s_c2_w_c1);
            System.out.println("Card 2 adjusted synergy w/Card 1: " + a_s_c2_w_c1);
            System.out.println("Card 2 p-value: " + p_c2);
        }

        if (validWithC1 != 0 && validWithC2 != 0) {
            System.out.println("Magical synergy number: " + s);
        }

        System.out.println(d1.getUsage() + ", " + validWithC1);
        System.out.println(d2.getUsage() + ", " + validWithC2);

        System.out.println("-------------------------");
        System.out.println();
    }
}
