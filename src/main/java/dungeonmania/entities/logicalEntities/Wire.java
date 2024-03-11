package dungeonmania.entities.logicalEntities;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Wire extends Conductor {
    public Wire(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }
}
