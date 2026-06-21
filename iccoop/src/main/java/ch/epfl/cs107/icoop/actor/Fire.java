package ch.epfl.cs107.icoop.actor;

import ch.epfl.cs107.icoop.handler.ICoopInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.Animation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

public class Fire extends Projectile {

    private final Animation animation;

    public Fire(Area area, Orientation orientation, DiscreteCoordinates position, int speed, int maxDistance) {
        super(area, orientation, position, speed, maxDistance);

        this.animation = new Animation("icoop/fire", 7, 1, 1, this, 16, 16, 4, true);
    }

    @Override
    public void update(float deltaTime) {
        animation.update(deltaTime);
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
    }
    @Override
    protected ICoopInteractionVisitor getVisitor() {
        return new FireInteractionVisitor();
    }

   private class FireInteractionVisitor implements ICoopInteractionVisitor {

        @Override
        public void interactWith(ICoopPlayer player, boolean isCellInteraction) {
            if (isCellInteraction) {
                player.takeDamage(DamageType.FIRE, 1);
                stopCourse();
            }
        }

        @Override
        public void interactWith(Foe foe, boolean isCellInteraction) {
            if (isCellInteraction) {
                foe.takeDamage(DamageType.FIRE, 1);
                stopCourse();
            }
        }

        @Override
        public void interactWith(Explosif explosif, boolean isCellInteraction) {
            if (isCellInteraction) {
                explosif.activate();
                stopCourse();
            }
        }
        @Override
        public void interactWith(Obstacle obstacle, boolean isCellInteraction) {
            if (isCellInteraction) {
                stopCourse();
            }
        }
    }
}