package dungeonmania.entities.buildables;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.util.Position;

public abstract class Buildable extends Entity implements InventoryItem, BattleItem {
    public Buildable(Position position) {
        super(position);
    }

    public abstract Buildable checkBuildable(Inventory inv, boolean remove, EntityFactory factory);

    public abstract boolean buildableStatus(Inventory inv);
}
