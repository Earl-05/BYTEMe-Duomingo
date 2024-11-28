package Game;

public abstract class GameBase {
    protected int difficulty;
    protected String language;

    public GameBase(int difficulty, String language) {
        this.difficulty = difficulty;
        this.language = language;
    }

    public abstract int playGame();
}
