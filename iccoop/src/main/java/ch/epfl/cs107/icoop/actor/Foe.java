package ch.epfl.cs107.icoop.actor;

import ch.epfl.cs107.icoop.handler.ICoopInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Animation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Foe extends MovableAreaEntity implements Interactor, Interactable {

    private final Health healthBar;
    private final List<DamageType> vulnerabilities;
    private final Animation deathAnimation;
    private boolean isDead;
    private int immunityCounter;
    private final static int IMMUNITY_DURATION = 24;
    private final static int ANIMATION_DURATION = 24;

    public Foe(Area area, Orientation orientation, DiscreteCoordinates position, DamageType... vulnerabilities) {
        super(area, orientation, position);
        this.vulnerabilities = Arrays.asList(vulnerabilities);

        this.healthBar = new Health(this, Transform.I.translated(0, 1.25f), getMaxHp(), false);
        this.isDead = false;
        this.immunityCounter = 0;

        this.deathAnimation = new Animation("icoop/vanish", 7, 2, 2, this, 32, 32, new Vector(-0.5f, 0f), ANIMATION_DURATION/7, false);
    }

    protected abstract int getMaxHp();

    public void takeDamage(DamageType type, int amount) {
        if (isDead || immunityCounter > 0 || !vulnerabilities.contains(type)) {
            return;
        }
        healthBar.decrease(amount);
        immunityCounter = IMMUNITY_DURATION;
        if (!healthBar.isOn()) {
            isDead = true;
        }
    }

    @Override
    public void update(float deltaTime) {
        if (isDead) {
            deathAnimation.update(deltaTime);
            if (deathAnimation.isCompleted()) {
                getOwnerArea().unregisterActor(this);
            }
            return;
        }
        if (immunityCounter > 0) {
            immunityCounter--;
        }
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        if (isDead) {
            deathAnimation.draw(canvas);
        } else {
            if (immunityCounter == 0 || immunityCounter % 2 == 0) {
                drawFoe(canvas);
            }
            healthBar.draw(canvas);
        }
    }

    protected abstract void drawFoe(Canvas canvas);

    @Override
    public boolean takeCellSpace() { return !isDead; }

    @Override
    public boolean isCellInteractable() { return true; }

    @Override
    public boolean isViewInteractable() { return true; }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICoopInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean wantsCellInteraction() { return !isDead; }

    @Override
    public boolean wantsViewInteraction() { return false; } // Les monstres n'interagissent pas à distance par défaut

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() { return null; }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(new FoeInteractionVisitor(), isCellInteraction);
    }

    private class FoeInteractionVisitor implements ICoopInteractionVisitor {
        @Override
        public void interactWith(ICoopPlayer player, boolean isCellInteraction) {
            if (isCellInteraction) {
                player.takeDamage(DamageType.PHYSICAL, 1); // Blesse le joueur d'1 PV
            }
        }
    }
}