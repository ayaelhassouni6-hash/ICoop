package ch.epfl.cs107.icoop.actor;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.signal.logic.Logic;

public class WaterWall extends ElementalWall {

    public WaterWall(Area area, Orientation orientation, DiscreteCoordinates position, Logic signal) {
        super(area, orientation, position, signal, "water_wall");
    }

    @Override
    public Element element() {
        return Element.WATER;
    }

    @Override
    protected void applyDamage(ICoopPlayer player) {
        // Inflige 1 point de dommage de type EAU
        player.takeDamage(DamageType.WATER, 1);
    }
}
