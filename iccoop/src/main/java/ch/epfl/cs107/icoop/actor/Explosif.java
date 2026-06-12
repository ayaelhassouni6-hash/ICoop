package ch.epfl.cs107.icoop.actor;

import ch.epfl.cs107.play.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Animation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Explosif extends AreaEntity implements Interactable, Interactor {

    private Animation animation;
    private int retardateur;
    private boolean isActivated;
    private boolean hasExploded;
    private static final int ANIMATION_DURATION = 24;

    public Explosif(Area area, DiscreteCoordinates position, int retardateur) {
        super(area, Orientation.DOWN, position);
        this.retardateur = retardateur*24;
        this.isActivated = false;
        this.hasExploded = false;

        this.animation = new Animation("icoop/explosive", 2, 1, 1, this , 16 , 16 ,
                ANIMATION_DURATION /2 , true);

    }

    public void activate() {
        if (!isActivated && !hasExploded) {
            isActivated = true;
        }
    }

    public boolean hasExploded() {
        return hasExploded;
    }

    public boolean isActivated() {
        return isActivated;
    }

    @Override
    public void update(float deltaTime) {
        if (isActivated && !hasExploded) {
            retardateur--;
            if (retardateur <= 0) {
                hasExploded = true;
                this.animation = new Animation("icoop/explosion", 7, 1, 1, this , 32 , 32 ,
                        ANIMATION_DURATION /7 , false);
            }
        }
        animation.update(deltaTime);
        if(animation.isCompleted()) {
            getOwnerArea().unregisterActor(this);
        }
        super.update(deltaTime);
    }


    @Override
    public void draw(Canvas canvas) {
        if (!animation.isCompleted()) {
            animation.draw(canvas);
        }
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return !isActivated && !hasExploded;
    }

    @Override
    public boolean isViewInteractable() {
        return !hasExploded;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
    }

    @Override
    public boolean wantsCellInteraction() {
        return hasExploded;
    }

    @Override
    public boolean wantsViewInteraction() {
        return hasExploded;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        if (other instanceof Rock) {
            ((Rock) other).setDestroyed(true);
        }
        if (other instanceof ICoopPlayer) {
            ((ICoopPlayer) other).takeDamage(DamageType.PHYSICAL, 2);
        }
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        DiscreteCoordinates currentPos = getCurrentMainCellCoordinates();
        List<DiscreteCoordinates> explosionCells = new ArrayList<>();
        explosionCells.add(currentPos);
        explosionCells.add(new DiscreteCoordinates(currentPos.x + 1, currentPos.y));
        explosionCells.add(new DiscreteCoordinates(currentPos.x - 1, currentPos.y));
        explosionCells.add(new DiscreteCoordinates(currentPos.x, currentPos.y + 1));
        explosionCells.add(new DiscreteCoordinates(currentPos.x, currentPos.y - 1));
        return explosionCells;
    }
}
