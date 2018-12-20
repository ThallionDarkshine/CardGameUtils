package data;

/**
 * Created by youngj14 on 12/11/2018.
 */
public abstract class CardDesc {
    private String name;

    public CardDesc(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object other) {
        if (!(other instanceof CardDesc)) return false;

        CardDesc oCard = (CardDesc) other;

        return oCard.name.equals(name);
    }

    public abstract Card instantiate();
}
