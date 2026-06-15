package ch.epfl.cs107.icoop.handler;

import ch.epfl.cs107.icoop.ICoopBehavior;
import ch.epfl.cs107.icoop.actor.*;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;


public interface ICoopInteractionVisitor extends AreaInteractionVisitor {
    default void interactWith(ICoopBehavior.ICoopCell cell, boolean isCellInteraction) {}
    default void interactWith(ICoopPlayer player, boolean isCellInteraction) {}
    default void interactWith(Door door, boolean isCellInteraction) {}
    default void interactWith(ICoopCollectable collectable, boolean isCellInteraction) {}
    default void interactWith(Explosif explosif, boolean isCellInteraction) {}
    default void interactWith(ElementalItem item, boolean isCellInteraction) {}
}
