package dungeonmania.goals;

import dungeonmania.Game;

public class EnemyGoal implements Goal {
    private int goal;

    public EnemyGoal(int goal) {
        this.goal = goal;
    }

    @Override
    public boolean achieved(Game game) {
        if (game.getPlayer() == null) {
            return false;
        }
        return ((game.getNumEnemiesDestroyed() >= goal) && (game.getNumSpawners() <= 0));
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game)) {
            return "";
        }
        return ":enemies";
    }
}
