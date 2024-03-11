package dungeonmania.goals;

import dungeonmania.Game;

public interface Goal {
    /**
    * @return true if the goal has been achieved, false otherwise
    */
    public abstract boolean achieved(Game game);

    public abstract String toString(Game game);
}
