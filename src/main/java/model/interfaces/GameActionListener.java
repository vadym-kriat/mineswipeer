package model.interfaces;

public interface GameActionListener {
    void update(int i, int j);

    void endGame(int i, int j);

    void winGame();
}
