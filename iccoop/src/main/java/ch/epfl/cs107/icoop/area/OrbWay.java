package ch.epfl.cs107.icoop.area;


import ch.epfl.cs107.icoop.actor.*;
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

        registerActor(doorToSpawn1);
        registerActor(doorToSpawn2);
        registerActor(new Orb(this, new DiscreteCoordinates(17, 12), OrbType.FIRE));
        registerActor(new Orb(this, new DiscreteCoordinates(17, 6), OrbType.WATER));
        registerActor(new Heart(this, Orientation.UP, new DiscreteCoordinates(8, 4)));
        registerActor(new Heart(this, Orientation.UP, new DiscreteCoordinates(10, 6)));
        registerActor(new Heart(this, Orientation.UP, new DiscreteCoordinates(5, 13)));
        registerActor(new Heart(this, Orientation.UP, new DiscreteCoordinates(10, 11)));

        PressurePlate plate1 = new PressurePlate(this, new DiscreteCoordinates(5, 7)); // Plaque pour le grand mur de feu (en haut)
        PressurePlate plate2 = new PressurePlate(this, new DiscreteCoordinates(5, 10)); // Plaque pour le grand mur d'eau (en bas)
        registerActor(plate1);
        registerActor(plate2);
        Logic invertedPlate1 = new Logic() {
            @Override
            public boolean isOn() {
                return plate1.isOff();
            }
            @Override
            public boolean isOff() {
                return plate1.isOn();
            }
            @Override
            public float getIntensity() {
                return plate1.isOff() ? 1f : 0f;
            }
        };
        Logic invertedPlate2 = new Logic() {
            @Override
            public boolean isOn() {
                return plate2.isOff();
            }
            @Override
            public boolean isOff() {
                return plate2.isOn();
            }
            @Override
            public float getIntensity() {
                return plate2.isOff() ? 1f : 0f;
            }
        };
        for (int i = 0; i <= 4; i++) {
            registerActor(new FireWall(this, Orientation.LEFT, new DiscreteCoordinates(12, 10 + i), invertedPlate1));
        }
        for (int i = 0; i <= 4; i++) {
            registerActor(new WaterWall(this, Orientation.LEFT, new DiscreteCoordinates(12, 3 + i), invertedPlate2));
        }
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