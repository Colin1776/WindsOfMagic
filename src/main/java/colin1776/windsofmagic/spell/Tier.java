package colin1776.windsofmagic.spell;

@SuppressWarnings("unused")
public enum Tier
{
    // TODO rework this, number of spells on staff shouldn't be determined by tier, add fourth tier

    BEGINNER("Beginner", 4),
    ADVANCED("Advanced", 5),
    MASTER("Master", 6);

    private final String name;
    private final int number;

    Tier(String name, int number)
    {
        this.name = name;
        this.number = number;
    }

    public int getNumber()
    {
        return number;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
