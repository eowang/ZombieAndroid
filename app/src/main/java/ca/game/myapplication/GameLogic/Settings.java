/* A singleton model to store updated game settings.*/

package ca.game.myapplication.GameLogic;

public class Settings {
    private int col = 6;
    private int row = 4;
    private int zombie_count = 10;

    private static Settings instance;

    public static Settings getInstance(){
        if (instance==null){
            instance = new Settings();
        }
        return instance;
    }


    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getZombie_count() {
        return zombie_count;
    }

    public void setZombie_count(int zombie_count) {
        this.zombie_count = zombie_count;
    }
}

