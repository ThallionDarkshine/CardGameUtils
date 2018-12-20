package analysis;

import data.CardDesc;
import data.DeckDesc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ThallionDarkshine on 12/19/2018.
 */
public class CardSynergyAnalysis {
    public static <T extends CardDesc> void analyzeCardSynergy(List<DeckDesc<T>> decks) {
        Map<T, Map<T, CardPairingData>> pairings = new HashMap<>();

        for (int i = 0;i < decks.size();++i) {
            List<T> cards = new ArrayList<T>(decks.get(i).getCards().keySet());

            for (int j = 0;j < cards.size() - 1;++j) {
                for (int k = j + 1;k < cards.size();++k) {
                    T c1 = cards.get(j);
                    T c2 = cards.get(k);

                    if (c1.getName().compareTo(c2.getName()) < 0) {
                        T tmp = c1;
                        c1 = c2;
                        c2 = tmp;
                    }

                    if (!pairings.containsKey(c1)) {
                        pairings.put(c1, new HashMap<>());
                    }

                    if (!pairings.get(c1).containsKey(c2)) {
                        CardPairingData pairing = new CardPairingData<>(c1, c2);

                        for (int l = 0;l < i;++l) {
                            pairing.addDeckData(decks.get(l));
                        }

                        pairings.get(c1).put(c2, pairing);
                    }

                    pairings.get(c1).get(c2).addDeckData(decks.get(i));
                }
            }
        }

        for (Map<T, CardPairingData> m : pairings.values()) {
            for (CardPairingData pairing : m.values()) {
                pairing.outputBasicAnalysis();
            }
        }
    }
}
