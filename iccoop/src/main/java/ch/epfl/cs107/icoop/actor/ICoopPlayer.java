package ch.epfl.cs107.icoop.actor;

/**
 * A ICoopPlayer is a player for the ICoop game.
 */
import ch.epfl.cs107.icoop.KeyBindings;
import ch.epfl.cs107.icoop.handler.ICoopInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import static ch.epfl.cs107.play.math.Orientation.*;


public final class ICoopPlayer extends MovableAreaEntity implements ElementalEntity, Interactor {
    private final Element element;
    private final static int ANIMATION_DURATION = 4;
    private final OrientedAnimation animation;
    private final KeyBindings.PlayerKeyBindings keys;
    private final ICoopPlayerInteractionHandler interactionHandler;
    private Door pendingDoor;

    /**
     * @param owner (Area) area to which the player belong
     * @param orientation (Orientation) the initial orientation of the player
     * @param coordinates (DiscreteCoordinates) the initial position in the grid
     * //@param spriteName (String) name of the sprite used as graphical representation
     */
    public ICoopPlayer(Area owner, Orientation orientation, Element element, DiscreteCoordinates coordinates, KeyBindings.PlayerKeyBindings keys) {
        super(owner, orientation, coordinates);
        this.keys = keys;
        this.element = element;
        this.interactionHandler = new ICoopPlayerInteractionHandler();
        String spriteName = (element == Element.FIRE) ? "icoop/player" : "icoop/player2";
        final Orientation[] orders = {DOWN, RIGHT, UP, LEFT};
        this.animation = new OrientedAnimation(spriteName, ANIMATION_DURATION, this, Vector.ZERO, orders, 4, 1, 2, 16, 32, true);
        resetMotion();
    }

    public boolean hasPendingDoor() {
        return pendingDoor != null;
    }
    public Door consumePendingDoor() {
        Door d = pendingDoor;
        pendingDoor = null;
        return d;
    }
    @Override
    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        moveIfPressed(LEFT, keyboard.get(keys.left()));
        moveIfPressed(DOWN, keyboard.get(keys.down()));
        moveIfPressed(UP, keyboard.get(keys.up()));
        moveIfPressed(RIGHT, keyboard.get(keys.right()));
        if (isDisplacementOccurs()) {
            animation.update(deltaTime);
        }
        else{
            animation.reset();
        }


        super.update(deltaTime);
    }

    /**
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
        //message.draw(canvas);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }
    @Override
    public Element element() {
        return element;
    }
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(
                getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }
    @Override
    public boolean wantsCellInteraction() {
        return true;
    }
    @Override
    public boolean wantsViewInteraction() {
        return getOwnerArea().getKeyboard().get(keys.useItem()).isDown();
    }
    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(interactionHandler, isCellInteraction);
    }
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICoopInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    private class ICoopPlayerInteractionHandler implements ICoopInteractionVisitor {
        @Override
        public void interactWith(Door door, boolean isCellInteraction) {
            if (isCellInteraction && door.getSignal().isOn()) {
                pendingDoor = door;
            }
        }
    }

    /**
     * Orientate and Move this player in the given orientation if the given button is down
     *
     * @param orientation (Orientation): given orientation, not null
     * @param b           (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, Button b) {
        if (b.isDown() && !isDisplacementOccurs()) {
            orientate(orientation);
            move(ANIMATION_DURATION);

        }
    }

    /**
     * Leave an area by unregister this player
     */
    public void leaveArea() {
        getOwnerArea().unregisterActor(this);
    }

    /**
     * makes the player entering a given area
     * @param area     (Area):  the area to be entered, not null
     * @param position (DiscreteCoordinates): initial position in the entered area, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position) {
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
        area.registerActor(this);
        area.setViewCandidate(this);
    }

    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }



}
