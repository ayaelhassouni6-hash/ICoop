package ch.epfl.cs107.icoop.area;


import ch.epfl.cs107.icoop.actor.Door;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.signal.logic.Logic;

import java.util.Arrays;
import java.util.List;
public final class Spawn extends ICoopArea {
    Door spawnToOrbWay;
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(13, 6);
    }


    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        List<DiscreteCoordinates> arrivalInOrbWay = Arrays.asList(
                new DiscreteCoordinates(1, 12),
                new DiscreteCoordinates(1, 5)
        );
        DiscreteCoordinates doorMainCell = new DiscreteCoordinates(19, 15);
        spawnToOrbWay = new Door("OrbWay", Logic.TRUE, arrivalInOrbWay, this, doorMainCell, Orientation.RIGHT, new DiscreteCoordinates(19, 16));
        registerActor(spawnToOrbWay);
    }
    public String getTitle() {
        return "Spawn";
    }
}