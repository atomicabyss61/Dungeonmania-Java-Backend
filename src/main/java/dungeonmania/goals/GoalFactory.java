package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoalFactory {
    public static Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        JSONArray subgoals;
        switch (jsonGoal.getString("goal")) {
        case "enemies":
            int enemyGoal = config.optInt("enemy_goal");
            return new EnemyGoal(enemyGoal);
        case "AND":
            subgoals = jsonGoal.getJSONArray("subgoals");
            return new ANDGoal(createGoal(subgoals.getJSONObject(0), config),
                    createGoal(subgoals.getJSONObject(1), config));
        case "OR":
            subgoals = jsonGoal.getJSONArray("subgoals");
            return new ORGoal(createGoal(subgoals.getJSONObject(0), config),
                    createGoal(subgoals.getJSONObject(1), config));
        case "exit":
            return new ExitGoal();
        case "boulders":
            return new BouldersGoal();
        case "treasure":
            int treasureGoal = config.optInt("treasure_goal");
            return new TreasureGoal(treasureGoal);
        default:
            return null;
        }
    }
}
