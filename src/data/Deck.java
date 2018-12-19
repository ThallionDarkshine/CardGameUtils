package data;

import java.util.*;

/**
 * Created by youngj14 on 12/11/2018.
 */
public abstract class Deck<T extends CardDesc> {
    private DeckDesc<T> desc;
    private List<Card<T>> cards;

    public Deck(DeckDesc<T> desc) {
        instantiate(desc);
    }

    public void instantiate(DeckDesc<T> desc) {
        this.desc = desc;

        cards = new ArrayList<>();

        for (Map.Entry<T, Integer> cardEntry : desc.getCards().entrySet()) {
            for (int i = 0;i < cardEntry.getValue();++i) {
                cards.add(cardEntry.getKey().instantiate());
            }
        }
    }

    public void shuffle(Random rng) {
        Collections.shuffle(cards, rng);
    }

    public List<Card<T>> draw(int n) {
        List<Card<T>> draw = new ArrayList<>();

        if (n > cards.size()) return draw;

        for (int i = 0;i < n;++i) {
            draw.add(cards.remove(0));
        }

        return draw;
    }
}
