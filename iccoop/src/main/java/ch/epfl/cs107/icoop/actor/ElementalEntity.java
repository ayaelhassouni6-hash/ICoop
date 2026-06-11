package ch.epfl.cs107.icoop.actor;


public interface ElementalEntity {
    public enum Element {
        FIRE,
        WATER,
        NONE
    }
    Element element();
}