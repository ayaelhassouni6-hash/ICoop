package ch.epfl.cs107.icoop.actor;

import ch.epfl.cs107.icoop.handler.ICoopInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Animation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;
import java.util.Collections;
import java.util.List;

public class Orb extends ElementalItem {

    private final OrbType type;
    private final Animation animation;

    public Orb(Area area, DiscreteCoordinates position, OrbType type) {
        // L'orbe est orientée vers le HAUT par défaut
        super(area, Orientation.UP, position, type.element);
        this.type = type;

        // 3. Animation (À adapter selon les détails précis de ton annexe 7.3.3)
        // Les valeurs 6, 1, 1, 32, 32 etc. dépendent de ta sprite sheet.
        this.animation = new Animation("icoop/orb", 6, 1, 1, this, 32, 32, 4, true);
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