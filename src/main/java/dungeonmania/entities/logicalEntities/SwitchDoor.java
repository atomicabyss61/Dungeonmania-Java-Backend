package dungeonmania.entities.logicalEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends NonConductor {
    public SwitchDoor(Position position, String entityLogic) {
        super(position.asLayer(Entity.DOOR_LAYER), entityLogic);
    }

    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (isActive() || entity instanceof Spider) {
            return true;
        }
        return false;
    }
}
