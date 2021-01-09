package ca.cmpt276.as3.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * GameLogic class models the information about a
 * logic of the game. Data includes list of cells,
 * number of rows and columns, scans, and imposters left.
 * It supports randomizing the imposters on the cells.
 */
public class GameLogic {
    private ArrayList<ButtonCell> cells;

    private final int NUM_IMPOSTERS;
    private final int NUM_ROWS;
    private final int NUM_COLUMNS;

    private int scans = 0;
    private int itemsLeft;

    public int getScans() {
        return scans;
    }

    public int getItemsLeft() {
        return itemsLeft;
    }

    public void incrementScans() {
        scans++;
    }

    public void decrementItems() {
        itemsLeft--;
    }

    public GameLogic(ArrayList<ButtonCell> cells, int NUM_ROWS, int NUM_COLUMNS, int NUM_IMPOSTERS) {
        this.cells = cells;
        this.NUM_IMPOSTERS = NUM_IMPOSTERS;
        this.NUM_ROWS = NUM_ROWS;
        this.NUM_COLUMNS = NUM_COLUMNS;
        this.itemsLeft = NUM_IMPOSTERS;
    }

    public void populateCells() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int column = 0; column < NUM_COLUMNS; column++) {
                // Location of the cell in the board is assigned and isImposter set default value of false
                ButtonCell cell = new ButtonCell(row, column, false, false);
                cells.add(cell);
            }
        }

        // Shuffle the list to randomize cells for assigning imposters
        Collections.shuffle(cells);

        // Assign first NUM_IMPOSTERS cells in shuffled list to be an imposter
        for (int cellIndex = 0; cellIndex < NUM_IMPOSTERS; cellIndex++) {
            cells.get(cellIndex).setImposter(true);
        }
    }

    public ButtonCell retrieveCell(int row, int column) {
        int totalCellsInList = NUM_COLUMNS * NUM_ROWS;
        for (int i = 0; i < totalCellsInList; i++) {
            ButtonCell cell = cells.get(i);
            if (cell.getRow() == row & cell.getColumn() == column) {
                return cell;
            }
        }
        return null;
    }

    public int numberForCell(ButtonCell givenCell) {
        int totalImposters = 0;
        for (ButtonCell cell : cells) {
            boolean inSameColumn = givenCell.getColumn() == cell.getColumn();       // Same column
            boolean inSameRow = givenCell.getRow() == cell.getRow();                // Same row


            if ((inSameColumn || inSameRow) &&      // Has to be same row or column
                cell.isImposter() &&                // Has to be an imposter
                !cell.isRevealed()) {               // If not revealed cell
                totalImposters++;
            }
        }
        return totalImposters;
    }
}
