package ch.epfl.cs107.icoop.actor;


import ch.epfl.cs107.icoop.handler.ICoopInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class PressurePlate extends AreaEntity implements Logic {

    private final Sprite sprite;
    private boolean isPressed;
    private float timer;

    public PressurePlate(Area area, DiscreteCoordinates position) {
        super(area, Orientation.DOWN, position);
        this.sprite = new Sprite("GroundPlateOff", 1, 1.f, this);
        this.isPressed = false;
    }
    public void press() {
        this.isPressed = true;
        this.timer = 0.1f;
    }
    @Override
    public void update(float deltaTime) {
        if (timer > 0) {
            timer -= deltaTime;
            if (timer <= 0) {
                isPressed = false;
            }
        }
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
    @Override
    public boolean isOn() { return isPressed; }

    @Override
    public boolean isOff() { return !isPressed; }

    @Override
    public float getIntensity() { return isPressed ? 1.0f : 0.0f; }

    // --- Paramètres physiques ---
    @Override
    public boolean takeCellSpace() { return false; } // Traversable

    @Override
    public boolean isCellInteractable() { return true; } // Interaction par contact

    @Override
    public boolean isViewInteractable() { return false; }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICoopInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
}