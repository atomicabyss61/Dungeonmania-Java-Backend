package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class SunStoneTest {
    @Test
    @DisplayName("Test SunStone opening door")
    public void testSunStoneOpenDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneOpensDoor", "c_basicGoalsTest_exit");

        // Pick up sunStone
        res = dmc.tick(Direction.DOWN);
        // Check that we have sunStone
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Walk through first door
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        // Check that we still have sunStone
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

    }

    @Test
    @DisplayName("Test SunStone can build Shield")
    public void testSunStoneBuildEntity() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_BuildablesTest_BuildShieldWithSunStone",
                "c_BuildablesTest_BuildShieldWithTreasure");
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        // Pick up Treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // SunStone isn't used
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @DisplayName("Test SunStone cannot bribe mercenary")
    public void testSunStoneBribeMercenarySimple() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneUsedToBribe", "c_sunStoneUsedToBribe");

        // Check that we dont have sunstone
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        // Check that we cant bribe yet
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));

        // Pick up sunStone
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        // Check that we cant bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
    }

    @Test
    @DisplayName("Test SunStone cannot bribe mercenary")
    public void testSunStoneBribeMercenaryComplex() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneUsedToBribe", "c_sunStoneUsedToBribe");

        // Check that we dont have sunstone
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        // Check that we cant bribe yet
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));

        // Pick up sunStone
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        // Check that we cant bribe
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));

        // Pick up treasure
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        // Check that we can bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));

        // Check that we still have sunStone
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @DisplayName("Test SunStone counts towards treasure goal")
    public void testSunStoneTreasureGoal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTreasureGoal", "c_basicGoalsTest_treasure");

        // move player to right
        res = dmc.tick(Direction.RIGHT);

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect sunStone
        res = dmc.tick(Direction.RIGHT);
        int numTreasuresTotal = TestUtils.getInventory(res, "sun_stone").size()
                + TestUtils.getInventory(res, "treasure").size();
        assertEquals(1, numTreasuresTotal);

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect treasure
        res = dmc.tick(Direction.RIGHT);
        numTreasuresTotal = TestUtils.getInventory(res, "sun_stone").size()
                + TestUtils.getInventory(res, "treasure").size();
        assertEquals(2, numTreasuresTotal);

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect treasure
        res = dmc.tick(Direction.RIGHT);

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }
}
