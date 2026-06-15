package ch.epfl.cs107.icoop.area;


import ch.epfl.cs107.icoop.actor.Door;
import ch.epfl.cs107.icoop.actor.Orb;
import ch.epfl.cs107.icoop.actor.OrbType;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.signal.logic.Logic;

import java.util.Arrays;
import java.util.List;
public final class OrbWay extends ICoopArea {

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        List<DiscreteCoordinates> arrivalInSpawn = Arrays.asList(
                new DiscreteCoordinates(18, 16),
                new DiscreteCoordinates(18, 15)
        );
        DiscreteCoordinates mainCell1 = new DiscreteCoordinates(0, 8);
        DiscreteCoordinates[] otherCells1 = {
                new DiscreteCoordinates(0, 7),
                new DiscreteCoordinates(0, 6),
                new DiscreteCoordinates(0, 5),
                new DiscreteCoordinates(0, 4)
        };
        Door doorToSpawn1 = new Door("Spawn", Logic.TRUE, arrivalInSpawn,
                this, mainCell1, Orientation.RIGHT, otherCells1);
        DiscreteCoordinates mainCell2 = new DiscreteCoordinates(0, 14);
        DiscreteCoordinates[] otherCells2 = {
                new DiscreteCoordinates(0, 13),
                new DiscreteCoordinates(0, 12),
                new DiscreteCoordinates(0, 11),
                new DiscreteCoordinates(0, 10)
        };
        Door doorToSpawn2 = new Door("Spawn", Logic.TRUE, arrivalInSpawn,
                this, mainCell2, Orientation.RIGHT, otherCells2);
        registerActor(new Orb(this, new DiscreteCoordinates(17, 12), OrbType.FIRE));
        registerActor(new Orb(this, new DiscreteCoordinates(17, 6), OrbType.WATER));
        registerActor(doorToSpawn1);
        registerActor(doorToSpawn2);
    }

    @Override
    public String getTitle() {
        return "OrbWay";
    }
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(1, 12);
    }
    @Override
    public DiscreteCoordinates getPlayer2SpawnPosition() {
        return new DiscreteCoordinates(1, 5);
    }
}