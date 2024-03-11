package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class EnemyGoalTest {
    @Test
    @DisplayName("Testing basic enemy goal")
    public void enemyGoal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicGoalsTest_enemy", "c_basicGoalsTest_enemy");

        // Check that goal is enemy
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // Player is at (1, 1), Spider is at (3, 1)
        // Move 1 step right
        res = dmc.tick(Direction.RIGHT);
        // Mercenary should be destroyed (goal complete)
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
    }

    @Test

    @DisplayName("Testing enemy goal with spawner and multiple enemies")
    public void enemyGoalZombieSpawner() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicGoalsTest_enemyWithSpawner", "c_basicGoalsTest_enemyWithSpawner");

        // Check that goal is enemy
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // Get spawnerId
        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

        // Move 1 step right
        res = dmc.tick(Direction.RIGHT);
        // Spider should be destroyed (goal still incomplete)
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // Move 1 step right
        res = dmc.tick(Direction.RIGHT);
        // Mercenary should be destroyed (goal still incomplete)
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // Move through sword and onto spawner
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        // Destroy spawner
        res = dmc.interact(spawnerId);

        // Should be removed
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
    }

    @Test
    @DisplayName("Testing enemy and exit goals")
    public void enemyAndExit() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_enemyAndExitGoal", "c_basicGoalsTest_enemy");
        // Check that goal is enemy and exit
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // Get spawnerId
        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

        // Move 1 step right to (3,0)
        res = dmc.tick(Direction.RIGHT);
        // Spider should be destroyed (goal still incomplete)
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        // assertTrue(TestUtils.getGoals(res).contains(":exit"));
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // Move through sword and onto spawner (6, 0)
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        //res = dmc.tick(Direction.RIGHT);
        // Destroy spawner
        res = dmc.interact(spawnerId);

        // Check that enemies goal is gone but exit is still there
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // Exit and check that its no longer a goal to (1,0)
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        assertFalse(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(""));
    }

    @Test
    @DisplayName("Testing enemy or exit goals")
    public void enemyOrExit() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_enemyOrExitGoal", "c_basicGoalsTest_enemy");

        // Complete exit goal but not enemy goal
        res = dmc.tick(Direction.LEFT);
        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @DisplayName("Testing Enemy Goal using Bomb")
    public void enemyGoalUsingBomb() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bombEnemyGoal", "c_bombTest_placeBombRadius10");
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertEquals(1, TestUtils.getEntities(res, "treasure").size());
        assertEquals(1, TestUtils.getEntities(res, "mercenary").size());

        // Activate Switch
        res = dmc.tick(Direction.RIGHT);

        // Pick up Bomb
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "bomb").size());

        // Place Cardinally Adjacent
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(TestUtils.getInventory(res, "bomb").get(0).getId());

        // Check Bomb exploded with radius 10
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
        assertEquals(0, TestUtils.getEntities(res, "bomb").size());
        assertEquals(0, TestUtils.getEntities(res, "mercenary").size());

    }
}
