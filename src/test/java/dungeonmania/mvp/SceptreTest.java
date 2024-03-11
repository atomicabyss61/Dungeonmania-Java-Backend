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

public class SceptreTest {
    @Test
    @DisplayName("Test Sceptre on Mercenary")
    public void basicSceptreUse() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicSceptreUse", "c_basicSceptreUse");
        String mercenaryId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // Check that you cannot bribe
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercenaryId));

        // Collect everything to build
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        // Build sceptre
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));

        // Use sceptre
        res = assertDoesNotThrow(() -> dmc.interact(mercenaryId));

        // Check that Mercenary is under mind control
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        List<EntityResponse> entities = res.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities, "mercenary") == 1);

        // Check that Mercenary is not under mind control after time is over
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        entities = res.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities, "mercenary") == 0);
    }

    @Test
    @DisplayName("Test Build Sceptre Using Arrows")
    public void buildSceptreUsingArrows() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_buildSceptreUsingArrows", "c_buildSceptreUsingArrows");

        // Collect everything to build
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        // Build sceptre
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
    }

    @Test
    @DisplayName("Test Build Sceptre Using 2 Sunstones")
    public void buildSceptreUsing2SunStones() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_buildSceptreUsing2SunStones", "c_buildSceptreUsingArrows");

        // Collect everything to build
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        // Build sceptre
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
    }
}
