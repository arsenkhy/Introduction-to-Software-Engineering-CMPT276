package ca.cmpt276.as3.model;

/**
 * ButtonCell class models the information about a
 * cell on the board. Data includes the cell's coordinates,
 * imposter value, and if it is revealed.
 */
public class ButtonCell {
    private int row;
    private int column;
    private boolean isImposter;
    private boolean isRevealed;

    // Constructor
    public ButtonCell(int row, int column, boolean isImposter, boolean isRevealed) {
        this.row = row;
        this.column = column;
        this.isImposter = isImposter;
        this.isRevealed = isRevealed;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isImposter() {
        return isImposter;
    }

    // Switch of cell's isImposter value
    public void setImposter(boolean imposter) {
        isImposter = imposter;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }
}

