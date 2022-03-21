package colin1776.windsofmagic.spell;

@SuppressWarnings("unused")
public enum Lore
{
    NONE("none"),
    FIRE("fire"),
    ICE("ice"),
    HEAVENS("heavens"),
    EARTH("earth");

    private final String name;

    Lore(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
