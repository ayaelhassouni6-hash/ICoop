package ch.epfl.cs107.icoop.actor;

public enum OrbType {
    WATER(ElementalEntity.Element.WATER, "orb_water_msg", 0), // 0 est un exemple pour le spriteYDelta
    FIRE(ElementalEntity.Element.FIRE, "orb_fire_msg", 32);   // 32 est un exemple pour le spriteYDelta

    public final ElementalEntity.Element element;
    public final String dialogName;
    public final int spriteYDelta;

    OrbType(ElementalEntity.Element element, String dialogName, int spriteYDelta) {
        this.element = element;
        this.dialogName = dialogName;
        this.spriteYDelta = spriteYDelta;
    }
}