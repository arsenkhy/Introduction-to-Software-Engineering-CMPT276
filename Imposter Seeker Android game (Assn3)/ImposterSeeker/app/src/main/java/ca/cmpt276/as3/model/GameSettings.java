package ca.cmpt276.as3.model;

/**
 * GameSettings Singleton class models the information about a
 * settings of a game. Data includes the preferences for board sizes,
 * number of imposters, and playedTimes.
 */
public class GameSettings {
    private int rows;
    private int columns;
    private int impostersCount;
    private int playsCounter;

    // Singleton
    private static GameSettings instance;
    private GameSettings() {
        // To prevent anyone form instantiating another such class
    }
    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setImpostersCount(int impostersCount) {
        this.impostersCount = impostersCount;
    }

    public void setPlaysCounter(int playsCounter) {
        this.playsCounter = playsCounter;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getImpostersCount() {
        return impostersCount;
    }

    public int getPlaysCounter() {
        return playsCounter;
    }
}
