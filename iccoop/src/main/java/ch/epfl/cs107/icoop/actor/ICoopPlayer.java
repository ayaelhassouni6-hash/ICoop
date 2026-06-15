package ch.epfl.cs107.icoop.actor;


import ch.epfl.cs107.icoop.handler.ICoopInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.engine.actor.TextGraphics;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.icoop.KeyBindings;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public final class ICoopPlayer extends MovableAreaEntity implements ElementalEntity, Interactor, Interactable {

    private final static int MOVE_DURATION = 5;
    private final static int ANIMATION_DURATION = 4;
    private final Element element;
    private final OrientedAnimation animation;
    private final KeyBindings.PlayerKeyBindings keys;
    private String pendingDestinationArea;
    private List<DiscreteCoordinates> pendingArrivalCoordinates = null;
    private final static int MAX_LIFE = 5;
    private final Health healthBar;
    private final static int IMMUNITY_DURATION = 24;
    private int immunityCounter = 0;
    private DamageType invulnerability = DamageType.NONE;

    /**
     * @param owner       (Area) area to which the player belong
     * @param orientation (Orientation) the initial orientation of the player
     * @param coordinates (DiscreteCoordinates) the initial position in the grid
     * @param spriteName  (String) name of the sprite used as graphical representation
     */
    public ICoopPlayer(Area owner, Orientation orientation, Element element, DiscreteCoordinates coordinates, String spriteName, KeyBindings.PlayerKeyBindings keys) {
        super(owner, orientation, coordinates);
        resetMotion();
        this.keys = keys;
        this.element = element;

        final Vector anchor = new Vector(0, 0);
        final Orientation[] orders = {Orientation.DOWN, Orientation.RIGHT, Orientation.UP, Orientation.LEFT};
        this.animation = new OrientedAnimation(spriteName, ANIMATION_DURATION, this, anchor, orders, 4, 1, 2, 16, 32, true);
        this.healthBar = new Health(this, Transform.I.translated(0, 1.75f), MAX_LIFE, true);
    }

    /**
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */

    @Override
    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        new Health (this , Transform.I. translated (0 , 1.75f), 1 ,
                true );
        moveIfPressed(Orientation.LEFT, keyboard.get(keys.left()));
        moveIfPressed(Orientation.UP, keyboard.get(keys.up()));
        moveIfPressed(Orientation.RIGHT, keyboard.get(keys.right()));
        moveIfPressed(Orientation.DOWN, keyboard.get(keys.down()));
        if (isDisplacementOccurs()) {
            animation.update(deltaTime);
        } else {
            animation.reset();
        }
        if (immunityCounter > 0) {
            immunityCounter--;
        }
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        if (immunityCounter == 0 || immunityCounter % 2 == 0) {
            animation.draw(canvas);
        }
        healthBar.draw(canvas);
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
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    private void moveIfPressed(Orientation orientation, Button b) {
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    public void leaveArea() {
        getOwnerArea().unregisterActor(this);
    }

    public void enterArea(Area area, DiscreteCoordinates position) {
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
        area.registerActor(this);
        area.setViewCandidate(this);
    }

    public boolean isWeak() {
        return !healthBar.isOn();
    }

    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    public void strengthen() {
        healthBar.increase(MAX_LIFE);
        immunityCounter = 0;
    }

    @Override
    public Element element() {
        return element;
    }

    public String getPendingDestinationArea() {
        return pendingDestinationArea;
    }

    public List<DiscreteCoordinates> getPendingArrivalCoordinates() {
        return pendingArrivalCoordinates;
    }

    public void resetPendingTransition() {
        pendingDestinationArea = null;
        pendingArrivalCoordinates = null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(
                getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsViewInteraction() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        return (keyboard.get(keys.useItem()).isPressed());
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(new ICoopPlayerInteractionVisitor(), isCellInteraction);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICoopInteractionVisitor) v).interactWith(this,
                isCellInteraction);
    }

    private class ICoopPlayerInteractionVisitor implements ICoopInteractionVisitor {
        @Override
        public void interactWith(Door door, boolean isCellInteraction) {
            if (isCellInteraction && door.getSignal().isOn()) {
                pendingDestinationArea = door.getDestinationAreaName();
                pendingArrivalCoordinates = door.getArrivalCoordinates();
            }
        }
        @Override
        public void interactWith(Explosif explosif, boolean isCellInteraction) {
            if (isCellInteraction) {
                if (!explosif.isActivated() && !explosif.hasExploded()) {
                    explosif.collect();
                }
            } else {
                explosif.activate();
            }
        }
        @Override
        public void interactWith(ElementalItem item, boolean isCellInteraction) {
            // On vérifie que c'est un contact ET que les éléments correspondent
            if (isCellInteraction && item.element() == ICoopPlayer.this.element()) {
                item.collect();
            }
        }

    }
    public void takeDamage(DamageType type, int amount) {
        if (this.invulnerability == type || this.immunityCounter > 0) {
            return;
        }
        healthBar.decrease(amount);
        this.immunityCounter = IMMUNITY_DURATION;
    }
}