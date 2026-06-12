package ch.epfl.cs107.icoop;


import ch.epfl.cs107.icoop.actor.Door;
import ch.epfl.cs107.icoop.actor.ElementalEntity;
import ch.epfl.cs107.icoop.actor.ICoopPlayer;
import ch.epfl.cs107.icoop.area.ICoopArea;
import ch.epfl.cs107.icoop.area.OrbWay;
import ch.epfl.cs107.icoop.area.Spawn;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Window;
import static ch.epfl.cs107.icoop.KeyBindings.RED_PLAYER_KEY_BINDINGS;


public class ICoop extends AreaGame {
    private ICoopPlayer player;
    @Override
    public String getTitle() {
        return "ICoop";
    }

    /**
     * Add all the ICoop areas
     */
    private void createAreas() {
        addArea(new Spawn());
        addArea(new OrbWay());
    }
    private void processPendingTransitions(){
        if( player != null && player.hasPendingDoor()) {
            Door door = player.consumePendingDoor();
            player.leaveArea();
            ICoopArea area = (ICoopArea)setCurrentArea(door.getDestinationArea(), false);
            player.enterArea(area, door.getDestinationPosition());
        }
    }
    /**
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return true if the game begins properly
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            createAreas();
            initArea("Spawn");
            return true;
        }
        return false;
    }

    /**
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        processPendingTransitions();
        super.update(deltaTime);
    }

    @Override
    public void end() {

    }

    /**
     * sets the area named `areaKey` as current area in the game ICoop
     * @param areaKey (String) title of an area
     */
    private void initArea(String areaKey) {
        ICoopArea area = (ICoopArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        DiscreteCoordinates coords2 = area.getPlayer2SpawnPosition();
        player = new ICoopPlayer(area, Orientation.DOWN, ElementalEntity.Element.FIRE, coords, RED_PLAYER_KEY_BINDINGS);
        player.enterArea(area, coords);
        player.centerCamera();
    }

}
