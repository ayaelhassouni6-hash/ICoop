package ch.epfl.cs107.icoop.actor;

public enum OrbType {
    WATER(ElementalEntity.Element.WATER, "orb_water_msg", 0),
    FIRE(ElementalEntity.Element.FIRE, "orb_fire_msg", 64);

    public final ElementalEntity.Element element;
    public final String dialogName;
    public final int spriteYDelta;

    OrbType(ElementalEntity.Element element, String dialogName, int spriteYDelta) {
        this.element = element;
        this.dialogName = dialogName;
        this.spriteYDelta = spriteYDelta;
    }
}