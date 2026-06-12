package ch.epfl.cs107.icoop;

import ch.epfl.cs107.icoop.actor.CenterOfMass;
import ch.epfl.cs107.icoop.actor.ElementalEntity;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.icoop.actor.ICoopPlayer;
import ch.epfl.cs107.icoop.area.ICoopArea;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.icoop.area.Spawn;
import ch.epfl.cs107.icoop.area.OrbWay;

import java.util.List;

import static ch.epfl.cs107.icoop.area.ICoopArea.DEFAULT_SCALE_FACTOR;
import static java.lang.Math.max;

public class ICoop extends AreaGame {
    private final String[] areas = {"Spawn", "OrbWay"};
    private ICoopPlayer player;
    private ICoopPlayer player2;
    private int areaIndex;
    private CenterOfMass centerOfMass;
    @Override
    public String getTitle() {
        return "ICoop";
    }
    @Override
    public void end() {
    }
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            addArea(new Spawn());
            addArea(new OrbWay());
            initArea(areas[areaIndex]);
            centerOfMass = new CenterOfMass(player,player2);
            ICoopArea area = (ICoopArea) getCurrentArea();
            area.registerActor(centerOfMass);
            area.setViewCandidate(centerOfMass);
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        /*if (player.isWeak() || player2.isWeak())
             switchArea();
             resetArea();
            return;*/
        Keyboard keyboard = getWindow().getKeyboard();
        if (keyboard.get(KeyBindings.RESET_GAME).isPressed()) {
            resetGame();
            return;
        }
        if (keyboard.get(KeyBindings.RESET_AREA).isPressed()) {
            resetArea();
            return;
        }
        float distance = player.getPosition().sub(player2.getPosition()).getLength();
        float newScaleFactor = max(DEFAULT_SCALE_FACTOR,
                (DEFAULT_SCALE_FACTOR * 0.75f + distance) / 2.0f);
        ICoopArea currentArea = (ICoopArea) getCurrentArea();
        currentArea.setCameraScaleFactor(newScaleFactor);
        if (player.getPendingDestinationArea() != null) {
            handlePlayerTransition(player);
        }
        if (player2.getPendingDestinationArea() != null) {
            handlePlayerTransition(player2);
        }
        super.update(deltaTime);
    }
    private void resetGame() {
        begin(getWindow(), getFileSystem());
    }
    private void resetArea() {
        player.leaveArea();
        player2.leaveArea();

        String currentAreaTitle = getCurrentArea().getTitle();
        getCurrentArea().purgeRegistration();

        if (currentAreaTitle.equals("Spawn")) {
            addArea(new Spawn());
        } else if (currentAreaTitle.equals("OrbWay")) {
            addArea(new OrbWay());
        }

        ICoopArea currentArea = (ICoopArea) setCurrentArea(currentAreaTitle, true);

        player.strengthen();
        player2.strengthen();

        player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());
        player2.enterArea(currentArea, currentArea.getPlayer2SpawnPosition());

        centerOfMass = new CenterOfMass(player, player2);
        currentArea.registerActor(centerOfMass);
        currentArea.setViewCandidate(centerOfMass);
    }
    private void handlePlayerTransition(ICoopPlayer p) {
        String destArea = p.getPendingDestinationArea();
        List<DiscreteCoordinates> arrivalCoords = p.getPendingArrivalCoordinates();
        player.leaveArea();
        player2.leaveArea();
        ICoopArea newArea = (ICoopArea) setCurrentArea(destArea, false);
        if (p==player){
            p.enterArea(newArea, arrivalCoords.get(0));
            player2.enterArea(newArea, arrivalCoords.get(1));}
        if(p==player2){
            p.enterArea(newArea, arrivalCoords.get(1));
            player.enterArea(newArea, arrivalCoords.get(0));}
        player.resetPendingTransition();
        player2.resetPendingTransition();
    }
    private void initArea(String areaKey) {
        ICoopArea area = (ICoopArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        DiscreteCoordinates coords2 = area.getPlayer2SpawnPosition();
        player = new ICoopPlayer(area, Orientation.DOWN, ElementalEntity.Element.FIRE, coords, "icoop/player",KeyBindings.RED_PLAYER_KEY_BINDINGS);
        player2 = new ICoopPlayer(area, Orientation.DOWN, ElementalEntity.Element.WATER, coords2, "icoop/player2",KeyBindings.BLUE_PLAYER_KEY_BINDINGS);
        player.enterArea(area, coords);
        player2.enterArea(area, coords2);
    }
    private void switchArea() {
        player.leaveArea();
        player2.leaveArea();
        areaIndex = (areaIndex == 0) ? 1 : 0;
        ICoopArea currentArea = (ICoopArea) setCurrentArea(areas[areaIndex], false);
        DiscreteCoordinates coords = currentArea.getPlayerSpawnPosition();
        player.enterArea(currentArea, coords);
        player.strengthen();
        DiscreteCoordinates coords2 = currentArea.getPlayer2SpawnPosition();
        player2.enterArea(currentArea, coords2);
        player2.strengthen();
    }
}
