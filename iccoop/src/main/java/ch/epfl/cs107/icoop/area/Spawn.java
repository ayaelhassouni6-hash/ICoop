package ch.epfl.cs107.icoop.area;


import ch.epfl.cs107.icoop.actor.Door;
import ch.epfl.cs107.icoop.actor.Explosif;
import ch.epfl.cs107.icoop.actor.Rock;
import ch.epfl.cs107.icoop.handler.DialogHandler;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Dialog;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.signal.logic.Logic;

import java.util.Arrays;
import java.util.List;
public final class Spawn extends ICoopArea {
    Door spawnToOrbWay;
    private final DialogHandler dialogHandler;
    private boolean welcomeShown = false;
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(13, 6);
    }
    public Spawn(DialogHandler dialogHandler) {
        this.dialogHandler = dialogHandler;
    }
    public void setWelcomeShown() {
        this.welcomeShown = true;
    }
    @Override
    public void update(float deltaTime) {
        if (!welcomeShown) {
            dialogHandler.publish(new Dialog("welcome"));
            welcomeShown = true; // On bloque pour les prochaines fois
        }
        super.update(deltaTime);
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
        spawnToOrbWay = new Door("OrbWay", Logic.TRUE, arrivalInOrbWay,
                this, doorMainCell, Orientation.RIGHT,
                new DiscreteCoordinates(19, 16));
        Explosif bomb1 = new Explosif(this, new DiscreteCoordinates(11, 10), 4);
        Explosif bomb2 = new Explosif(this, new DiscreteCoordinates(15, 8), 3);
        Explosif bomb3 = new Explosif(this, new DiscreteCoordinates(18, 4), 2);
        Rock rock = new Rock(this, new DiscreteCoordinates(10, 10));
        registerActor(bomb1);
        registerActor(bomb2);
        registerActor(bomb3);
        registerActor(rock);
        registerActor(spawnToOrbWay);
    }
    public String getTitle() {
        return "Spawn";
    }
}