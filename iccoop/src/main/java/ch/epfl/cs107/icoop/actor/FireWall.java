package ch.epfl.cs107.icoop.actor;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.signal.logic.Logic;

public class FireWall extends ElementalWall {

    public FireWall(Area area, Orientation orientation, DiscreteCoordinates position, Logic signal) {
        super(area, orientation, position, signal, "fire_wall");
    }

    @Override
    public Element element() {
        return Element.FIRE;
    }

    @Override
    protected void applyDamage(ICoopPlayer player) {
        // Inflige 1 point de dommage de type FEU
        player.takeDamage(DamageType.FIRE, 1);
    }
}