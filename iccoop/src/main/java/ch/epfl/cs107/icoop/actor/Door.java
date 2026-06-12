package ch.epfl.cs107.icoop.actor;

import ch.epfl.cs107.icoop.handler.ICoopInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Door extends AreaEntity {
    private final String destinationAreaName;
    private final Logic signal;
    private final List<DiscreteCoordinates> arrivalCoordinates;
    private final List<DiscreteCoordinates> otherCells;


    public Door(String destinationAreaName, Logic signal, List<DiscreteCoordinates> arrivalCoordinates,
                Area owner, DiscreteCoordinates mainCellPosition, Orientation orientation,DiscreteCoordinates CellCoordinates) {

        super(owner, orientation, mainCellPosition);
        this.destinationAreaName = destinationAreaName;
        this.signal = signal;
        this.arrivalCoordinates = arrivalCoordinates;
        this.otherCells = new ArrayList<>(List.of(CellCoordinates));

    }
    public Door(String destinationAreaName, Logic signal, List<DiscreteCoordinates> arrivalCoordinates,
                Area owner, DiscreteCoordinates mainCellPosition, Orientation orientation,
                DiscreteCoordinates ...otherCells) {
        super(owner, orientation, mainCellPosition);
        this.destinationAreaName = destinationAreaName;
        this.signal = signal;
        this.arrivalCoordinates = arrivalCoordinates;
        this.otherCells = new ArrayList<>(Arrays.asList(otherCells));
    }
    public String getDestinationAreaName() {
        return destinationAreaName;
    }
    public Logic getSignal(){
        return signal;
    }
    public List<DiscreteCoordinates> getArrivalCoordinates() {
        return arrivalCoordinates;
    }
    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        List<DiscreteCoordinates> allCells = new ArrayList<>();
        allCells.add(getCurrentMainCellCoordinates());
        allCells.addAll(otherCells);
        return allCells;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICoopInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

}
