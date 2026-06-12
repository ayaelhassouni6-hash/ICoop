package ch.epfl.cs107.icoop.area;


import ch.epfl.cs107.icoop.actor.Door;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.signal.logic.Logic;

/**
 * A specific   ICoop area
 */
public final class Spawn extends ICoopArea {

    /**
     * @return the player's spawn position in the area
     */

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(13, 6); // Position du personnage rouge
    }
    @Override
    public DiscreteCoordinates getPlayer2SpawnPosition() {
        return new DiscreteCoordinates(14, 6); // Position du personnage bleu
    }
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new Door(this, Orientation.DOWN, Logic.TRUE, "OrbWay", new DiscreteCoordinates(1, 12), new DiscreteCoordinates(19, 15), new DiscreteCoordinates(19, 16)));
    }

    @Override
    public String getTitle() {
        return "Spawn";
    }

}