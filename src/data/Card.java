package data;

/**
 * Created by youngj14 on 12/11/2018.
 */
public abstract class Card<T extends CardDesc> {
    private CardDesc desc;
    public Card(CardDesc desc) {
        this.desc = desc;
    }
}
