package dungeonmania.entities.buildables;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.inventory.Inventory;

public class Shield extends Buildable {
    private int durability;
    private double defence;

    public Shield(int durability, double defence) {
        super(null);
        this.durability = durability;
        this.defence = defence;
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
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, defence, 1, 1));
    }

    @Override
    public int getDurability() {
        return durability;
    }

    public String toString() {
        return "shield";
    }

    public boolean buildableStatus(Inventory inv) {
        int wood = inv.count(Wood.class);
        int treasure = inv.count(Treasure.class);
        int keys = inv.count(Key.class);

        return wood >= 2 && (treasure >= 1 || keys >= 1);
    }

    public Buildable checkBuildable(Inventory inv, boolean remove, EntityFactory factory) {
        List<Wood> wood = inv.getEntities(Wood.class);
        List<Treasure> treasure = inv.getEntities(Treasure.class);
        List<Key> keys = inv.getEntities(Key.class);
        List<SunStone> sunStone = inv.getEntities(SunStone.class);

        if (buildableStatus(inv)) {
            if (remove) {
                inv.remove(wood.get(0));
                inv.remove(wood.get(1));
                if (treasure.size() >= 1) {
                    if (sunStone.size() <= 0) {
                        inv.remove(treasure.get(0));
                    }
                } else {
                    inv.remove(keys.get(0));
                }
            }
            return factory.buildShield();
        }
        return null;
    }
}
