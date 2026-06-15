package ch.epfl.cs107.icoop.actor;

import ch.epfl.cs107.icoop.handler.ICoopInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public abstract class ICoopCollectable extends CollectableAreaEntity {

    public ICoopCollectable(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
    }

    @Override
    public boolean takeCellSpace() {
        return false; // Par défaut, on peut marcher dessus
    }

    @Override
    public boolean isCellInteractable() {
        return true; // Par défaut, on interagit par contact
    }

    @Override
    public boolean isViewInteractable() {
        return false; // Par défaut, pas d'interaction à distance
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICoopInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    /**
     * Méthode appelée pour ramasser l'objet et le faire disparaître de l'aire.
     */
    public void collect() {
        getOwnerArea().unregisterActor(this);
    }
}
