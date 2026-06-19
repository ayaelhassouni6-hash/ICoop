package ch.epfl.cs107.icoop.actor;

import ch.epfl.cs107.icoop.handler.ICoopInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.Collections;
import java.util.List;

public abstract class Projectile extends MovableAreaEntity implements Interactor, Unstoppable {

    private final int speed;
    private int remainingDistance;
    private final static int MOVE_DURATION = 12;

    public Projectile(Area area, Orientation orientation, DiscreteCoordinates position, int speed, int maxDistance) {
        super(area, orientation, position);
        this.speed = speed;
        this.remainingDistance = maxDistance;
    }

    @Override
    public void update(float deltaTime) {
        if (remainingDistance <= 0) {
            getOwnerArea().unregisterActor(this);
            return;
        }
        if (!isDisplacementOccurs()) {
            move(MOVE_DURATION / speed);
            remainingDistance--;
        }
        super.update(deltaTime);
    }

    public void stopCourse() {
        this.remainingDistance = 0;
        resetMotion();
    }

    @Override
    public boolean wantsCellInteraction() { return remainingDistance > 0; }

    @Override
    public boolean wantsViewInteraction() { return false; }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() { return null; }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() { return false; }

    @Override
    public boolean isCellInteractable() { return false; }

    @Override
    public boolean isViewInteractable() { return false; }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICoopInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(getVisitor(), isCellInteraction);
    }

    protected abstract ICoopInteractionVisitor getVisitor();
}