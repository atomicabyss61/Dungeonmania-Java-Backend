package dungeonmania.entities.logicalEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LightBulb extends NonConductor {
    public LightBulb(Position position, String entityLogic) {
        super(position.asLayer(Entity.CHARACTER_LAYER), entityLogic);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof Spider;
    }
}
