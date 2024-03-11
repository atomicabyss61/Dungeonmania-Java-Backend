package dungeonmania.goals;

import dungeonmania.Game;

public class ANDGoal implements Goal {
    private Goal goal1;
    private Goal goal2;

    public ANDGoal(Goal goal1, Goal goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    @Override
    public boolean achieved(Game game) {
        if (game.getPlayer() == null) {
            return false;
        }

        return goal1.achieved(game) && goal2.achieved(game);
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game)) {
            return "";
        }
        return "(" + goal1.toString(game) + " AND " + goal2.toString(game) + ")";
    }

    public Goal getGoal1() {
        return goal1;
    }

    public void setGoal1(Goal goal1) {
        this.goal1 = goal1;
    }

    public Goal getGoal2() {
        return goal2;
    }

    public void setGoal2(Goal goal2) {
        this.goal2 = goal2;
    }

}
