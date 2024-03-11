package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class MidnightArmourTest {
    @Test
    @DisplayName("Test midnight armour being built around zombies")
    public void buildMidnightArmourAroundZombies() {

        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_basicMidnightArmourBuildWithZombies", "c_basicMidnightArmourBuildWithZombies");

        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
    }

    @Test
    @DisplayName("Test midnight armour successfully")
    public void buildMidnightSuccessfully() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicMidnightArmourBuild", "c_basicMidnightArmourBuild");

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());
    }

    @Test
    @DisplayName("Test midnight armour buff")
    public void midnightArmourBuffTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourBuff", "c_basicMidnightArmourBuild");
        List<EntityResponse> entities = res.getEntities();
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        entities = res.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities, "spider") == 0);
    }
}
