package dungeonmania.entities.buildables;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.inventory.Inventory;

public class Bow extends Buildable {
    private int durability;

    public Bow(int durability) {
        super(null);
        this.durability = durability;
    }

    @Override
    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.getPlayer().remove(this);
        }
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 2, 1));
    }

    @Override
    public int getDurability() {
        return durability;
    }

    public String toString() {
        return "bow";
    }

    public boolean buildableStatus(Inventory inv) {
        int wood = inv.count(Wood.class);
        int arrows = inv.count(Arrow.class);

        return wood >= 1 && arrows >= 3;
    }

    public Buildable checkBuildable(Inventory inv, boolean remove, EntityFactory factory) {
        List<Wood> wood = inv.getEntities(Wood.class);
        List<Arrow> arrows = inv.getEntities(Arrow.class);

        if (buildableStatus(inv)) {
            if (remove) {
                inv.remove(wood.get(0));
                inv.remove(arrows.get(0));
                inv.remove(arrows.get(1));
                inv.remove(arrows.get(2));
            }
            return factory.buildBow();
        }
        return null;
    }
}
