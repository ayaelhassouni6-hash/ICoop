package ch.epfl.cs107.icoop.area;


import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

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
    }

    @Override
    public String getTitle() {
        return "Spawn";
    }

}