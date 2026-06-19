package ch.epfl.cs107.icoop.actor;

import ch.epfl.cs107.icoop.handler.ICoopInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Animation;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import java.util.Collections;
import java.util.List;

public class Orb extends ElementalItem {

    private final OrbType type;
    private final Animation animation;

    public Orb(Area area, DiscreteCoordinates position, OrbType type) {
        super(area, Orientation.UP, position, type.element);
        this.type = type;
        int ANIMATION_DURATION = 24;
        int ANIMATION_FRAMES = 6;
        Sprite[] sprites = new Sprite[ANIMATION_FRAMES];
        for (int i = 0; i < ANIMATION_FRAMES; i++) {
            sprites[i] = new RPGSprite("icoop/orb", 1, 1, this, new RegionOfInterest(i * 32, type.spriteYDelta, 32, 32));
        }
        this.animation = new Animation(ANIMATION_DURATION / ANIMATION_FRAMES, sprites, true);
    }

    public OrbType getType() {
        return type;
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
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICoopInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
}