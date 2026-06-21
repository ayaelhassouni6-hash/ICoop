package ch.epfl.cs107.icoop.actor;

import ch.epfl.cs107.icoop.handler.ICoopInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.random.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;

public class HellSkull extends Foe {

    private final OrientedAnimation animation;
    private float timer;

    public HellSkull(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, DamageType.PHYSICAL, DamageType.WATER);

        Orientation[] orders = {Orientation.DOWN, Orientation.LEFT, Orientation.UP, Orientation.RIGHT};
        this.animation = new OrientedAnimation("icoop/flameskull", 4, this, Vector.ZERO, orders, 3, 3, 4, 32, 32, true);
        this.timer = RandomGenerator.getInstance().nextFloat(0.5f, 2.0f);
    }

    @Override
    protected int getMaxHp() {
        return 2;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (!wantsCellInteraction()) {
            return;
        }

        animation.update(deltaTime);
        timer -= deltaTime;

        if (timer <= 0) {
            shootFire();
            timer = RandomGenerator.getInstance().nextFloat(0.5f, 2.0f);
        }
    }

    @Override
    protected void drawFoe(Canvas canvas) {
        animation.draw(canvas);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(new HellSkullInteractionVisitor(), isCellInteraction);
    }
    private void shootFire() {
        DiscreteCoordinates targetCell = getCurrentMainCellCoordinates().jump(getOrientation().toVector());
        Fire fire = new Fire(getOwnerArea(), getOrientation(), targetCell, 2, 5);
        if (getOwnerArea().canEnterAreaCells(fire, Collections.singletonList(targetCell))) {
            getOwnerArea().registerActor(fire);
        }
    }

    private class HellSkullInteractionVisitor implements ICoopInteractionVisitor {
        @Override
        public void interactWith(ICoopPlayer player, boolean isCellInteraction) {
            if (isCellInteraction) {
                player.takeDamage(DamageType.FIRE, 1);
            }
        }
    }
}