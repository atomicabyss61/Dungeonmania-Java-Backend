package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicEntitiesTest {
    @Test
    @DisplayName("Basic switch-wire-bulb with OR")
    public void logic1() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalEntitiesTest_basicOR", "c_basicGoalsTest_exit");

        assertEquals(new Position(5, 1), TestUtils.checkLightBulbInactive(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), TestUtils.checkLightBulbActive(res));
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(5, 1), TestUtils.checkLightBulbActive(res));
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(4, 1), TestUtils.getPlayerPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(5, 1), TestUtils.checkLightBulbInactive(res));
    }

    @Test
    @DisplayName("Basic switch-wire-bulb with AND")
    public void logic21() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalEntitiesTest_intermediateAND", "c_basicGoalsTest_exit");

        assertEquals(new Position(5, 1), TestUtils.checkSwitchDoorClosed(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), TestUtils.checkSwitchDoorClosed(res));
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(5, 1), TestUtils.checkSwitchDoorOpen(res));
    }

    @Test
    @DisplayName("Basic switch-wire-bulb with AND")
    public void logic2() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalEntitiesTest_basicAND", "c_basicGoalsTest_exit");

        assertEquals(new Position(5, 1), TestUtils.getEntityPos(res, "light_bulb_off"));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), TestUtils.getEntityPos(res, "light_bulb_off"));
    }

    @Test
    @DisplayName("Basic switch-wire-bulb with XOR")
    public void logic3() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalEntitiesTest_basicXOR", "c_basicGoalsTest_exit");

        assertEquals(new Position(5, 1), TestUtils.checkLightBulbInactive(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), TestUtils.checkLightBulbActive(res));
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(5, 1), TestUtils.checkLightBulbActive(res));
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(5, 3), TestUtils.getPlayerPos(res));
        assertEquals(new Position(5, 1), TestUtils.checkLightBulbInactive(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(5, 1), TestUtils.checkLightBulbInactive(res));
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(5, 1), TestUtils.checkLightBulbActive(res));
    }

    @Test
    @DisplayName("Basic switch-wire-bulb with CO_AND")
    public void logic4() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalEntitiesTest_basicCO_AND", "c_basicGoalsTest_exit");

        assertEquals(new Position(5, 1), TestUtils.checkLightBulbInactive(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), TestUtils.checkLightBulbActive(res));
    }

    @Test
    @DisplayName("Basic switch-wire-bulb with CO_AND")
    public void logic5() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalEntitiesTest_intermediateCO_AND", "c_basicGoalsTest_exit");

        assertEquals(new Position(5, 1), TestUtils.checkLightBulbInactive(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), TestUtils.checkLightBulbInactive(res));
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(5, 1), TestUtils.checkLightBulbInactive(res));
    }

    @Test
    @DisplayName("Basic switch-door with OR")
    public void logic6() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalEntitiesTest_basicSwitchDoor", "c_basicGoalsTest_exit");

        assertEquals(new Position(5, 1), TestUtils.checkSwitchDoorClosed(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), TestUtils.checkSwitchDoorOpen(res));
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(5, 1), TestUtils.checkSwitchDoorOpen(res));
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(5, 1), TestUtils.getPlayerPos(res));
    }

    @Test
    @DisplayName("Basic switch-door-closed with OR")
    public void logic7() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalEntitiesTest_basicSwitchDoor", "c_basicGoalsTest_exit");

        assertEquals(new Position(5, 1), TestUtils.checkSwitchDoorClosed(res));
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(5, 2), TestUtils.getPlayerPos(res));
    }
}
