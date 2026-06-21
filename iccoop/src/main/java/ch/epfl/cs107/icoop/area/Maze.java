package ch.epfl.cs107.icoop.area;

import ch.epfl.cs107.icoop.actor.*;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.signal.logic.Logic;

public final class Maze extends ICoopArea {

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        registerActor(new WaterWall(this, Orientation.LEFT, new DiscreteCoordinates(4, 35), Logic.TRUE));
        registerActor(new WaterWall(this, Orientation.LEFT, new DiscreteCoordinates(4, 36), Logic.TRUE));

        PressurePlate plate1 = new PressurePlate(this, new DiscreteCoordinates(6, 33));
        registerActor(plate1);

        Logic invertedPlate1 = new Logic() {
            @Override public boolean isOn() { return plate1.isOff(); }
            @Override public boolean isOff() { return plate1.isOn(); }
            @Override public float getIntensity() { return plate1.isOff() ? 1f : 0f; }
        };

        registerActor(new FireWall(this, Orientation.LEFT, new DiscreteCoordinates(6, 35), invertedPlate1));
        registerActor(new FireWall(this, Orientation.LEFT, new DiscreteCoordinates(6, 36), invertedPlate1));

        registerActor(new FireWall(this, Orientation.DOWN, new DiscreteCoordinates(2, 34), Logic.TRUE));
        registerActor(new FireWall(this, Orientation.DOWN, new DiscreteCoordinates(3, 34), Logic.TRUE));

        registerActor(new Explosif(this, new DiscreteCoordinates(6, 25), 4));

        registerActor(new WaterWall(this, Orientation.DOWN, new DiscreteCoordinates(5, 24), Logic.TRUE));
        registerActor(new WaterWall(this, Orientation.DOWN, new DiscreteCoordinates(6, 24), Logic.TRUE));

        PressurePlate plate2 = new PressurePlate(this, new DiscreteCoordinates(9, 25));
        registerActor(plate2);

        Logic invertedPlate2 = new Logic() {
            @Override public boolean isOn() { return plate2.isOff(); }
            @Override public boolean isOff() { return plate2.isOn(); }
            @Override public float getIntensity() { return plate2.isOff() ? 1f : 0f; }
        };

        registerActor(new FireWall(this, Orientation.DOWN, new DiscreteCoordinates(8, 21), invertedPlate2));

        registerActor(new Heart(this, Orientation.UP, new DiscreteCoordinates(15, 18)));
        registerActor(new Heart(this, Orientation.UP, new DiscreteCoordinates(16, 19)));
        registerActor(new Heart(this, Orientation.UP, new DiscreteCoordinates(14, 19)));
        registerActor(new Heart(this, Orientation.UP, new DiscreteCoordinates(14, 17)));

        registerActor(new WaterWall(this, Orientation.DOWN, new DiscreteCoordinates(8, 4), Logic.TRUE));

        registerActor(new FireWall(this, Orientation.DOWN, new DiscreteCoordinates(13, 4), Logic.TRUE));
        registerActor(new HellSkull(this, Orientation.RIGHT, new DiscreteCoordinates(12, 33)));
        registerActor(new HellSkull(this, Orientation.RIGHT, new DiscreteCoordinates(12, 31)));
        registerActor(new HellSkull(this, Orientation.RIGHT, new DiscreteCoordinates(12, 29)));
        registerActor(new HellSkull(this, Orientation.RIGHT, new DiscreteCoordinates(12, 27)));
        registerActor(new HellSkull(this, Orientation.RIGHT, new DiscreteCoordinates(12, 25)));

        registerActor(new HellSkull(this, Orientation.RIGHT, new DiscreteCoordinates(10, 33)));
        registerActor(new HellSkull(this, Orientation.RIGHT, new DiscreteCoordinates(10, 32)));
        registerActor(new HellSkull(this, Orientation.RIGHT, new DiscreteCoordinates(10, 30)));
        registerActor(new HellSkull(this, Orientation.RIGHT, new DiscreteCoordinates(10, 28)));
        registerActor(new HellSkull(this, Orientation.RIGHT, new DiscreteCoordinates(10, 26)));
    }

    @Override
    public String getTitle() {
        return "Maze";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(2, 39);
    }

    @Override
    public DiscreteCoordinates getPlayer2SpawnPosition() {
        return new DiscreteCoordinates(3, 39);
    }
}