package colin1776.windsofmagic.spell;

public enum Tier
{
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

    public int getNumberOfSpells()
    {
        return number;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
