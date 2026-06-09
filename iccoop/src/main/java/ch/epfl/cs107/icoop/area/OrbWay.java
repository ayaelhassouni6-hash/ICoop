package ch.epfl.cs107.icoop.area;


import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * A specific ICoop area
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
    }

    @Override
    public String getTitle(){
        return "OrbWay";
    }

}