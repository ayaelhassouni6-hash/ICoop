package ch.epfl.cs107.icoop.area;

import ch.epfl.cs107.icoop.ICoopBehavior;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public abstract class ICoopArea extends Area {
    public final static float DEFAULT_SCALE_FACTOR = 13.f;
    private float cameraScaleFactor = DEFAULT_SCALE_FACTOR;
    /**
     * Area specific callback to initialise the instance
     */
    protected abstract void createArea();

    /**
     * @return the player's spawn position in the area
     */
    public abstract DiscreteCoordinates getPlayerSpawnPosition();
    public DiscreteCoordinates getPlayer2SpawnPosition() {
        DiscreteCoordinates p1Spawn = getPlayerSpawnPosition();
        return new DiscreteCoordinates(p1Spawn.x + 1, p1Spawn.y);
    }
    /**
     * Callback to initialise the instance of the area
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return true if the area is instantiated correctly, false otherwise
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            setBehavior(new ICoopBehavior(window, getTitle()));
            createArea();
            return true;
        }
        return false;
    }
    public void setCameraScaleFactor(float scaleFactor) {
        this.cameraScaleFactor = scaleFactor;
    }

    /**
     * Getter for Tuto2's scale factor
     * @return Scale factor in both the x-direction and the y-direction
     */
    @Override
    public final float getCameraScaleFactor() {
        return cameraScaleFactor;
    }
    @Override
    public boolean isViewCentered() {
        return true; // Obligatoire pour recentrer la vue [cite: 76, 77]
    }

}

