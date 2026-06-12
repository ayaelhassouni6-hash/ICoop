package ch.epfl.cs107.icoop.actor;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public final class Rock extends Obstacle{

    private boolean destroyed;

    public Rock(Area area, DiscreteCoordinates position) {
        super(area, position);
        this.destroyed = false;
        this.sprite = new Sprite("rock.1", 1.0f, 1.0f, this);
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void draw(Canvas canvas) {
        if (!destroyed) {
            sprite.draw(canvas);
        }
    }

    @Override
    public boolean takeCellSpace() {
        return !destroyed;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {}


}
