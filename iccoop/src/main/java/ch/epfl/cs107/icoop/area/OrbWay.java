package ch.epfl.cs107.icoop.area;


import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

/**
 * A specific Tuto2 area
 */
public final class OrbWay extends ICoopArea {

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(1, 12);
    }
    @Override
    public DiscreteCoordinates getPlayer2SpawnPosition() {
        return new DiscreteCoordinates(1, 5);
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new SimpleGhost(new Vector(20, 10), "ghost.2"));
    }

    @Override
    public String getTitle() {
        return "OrbWay";
    }

}