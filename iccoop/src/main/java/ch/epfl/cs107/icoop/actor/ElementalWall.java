package ch.epfl.cs107.icoop.actor;


import ch.epfl.cs107.icoop.handler.ICoopInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

import static java.awt.ComponentOrientation.getOrientation;

public abstract class ElementalWall extends AreaEntity implements ElementalEntity, Interactor {

    private final Logic signal;
    private boolean isDestroyed;
    private final Sprite[] wallSprites;

    public ElementalWall(Area area, Orientation orientation, DiscreteCoordinates position, Logic signal, String spriteName) {
        super(area, orientation, position);
        this.signal = signal;
        this.isDestroyed = false;

        // Extraction des sprites selon la formule exacte de l'EPFL
        this.wallSprites = RPGSprite.extractSprites(spriteName, 4, 1, 1, this, Vector.ZERO, 256, 256);
    }

    // Un mur est actif s'il n'est pas détruit et que son signal est "On"
    public boolean isActive() {
        return !isDestroyed && signal != null && signal.isOn();
    }

    public void destroy() {
        getOwnerArea().unregisterActor(this);
    }

    @Override
    public void draw(Canvas canvas) {
        if (isActive()) {
            wallSprites[getOrientation().ordinal()].draw(canvas);
        }
    }

    @Override
    public boolean takeCellSpace() {
        return false; // Traversable par défaut (selon l'énoncé)
    }

    // --- Logique d'Interactable (Subir l'interaction) ---
    @Override
    public boolean isCellInteractable() {
        return true; // Uniquement par contact
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICoopInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    // --- Logique d'Interactor (Initier l'interaction) ---
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return isActive();
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        // Le mur utilise son propre visiteur interne pour repérer les joueurs
        other.acceptInteraction(new ElementalWallInteractionVisitor(), isCellInteraction);
    }

    // Visiteur interne du mur
    private class ElementalWallInteractionVisitor implements ICoopInteractionVisitor {
        @Override
        public void interactWith(ICoopPlayer player, boolean isCellInteraction) {
            // Si on touche un joueur et que le mur est actif, on lui fait des dégâts
            if (isCellInteraction && isActive()) {
                applyDamage(player);
            }
        }
    }

    // Méthode abstraite pour les dégâts, à définir dans FireWall et WaterWall
    protected abstract void applyDamage(ICoopPlayer player);
}