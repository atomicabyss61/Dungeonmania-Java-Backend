package dungeonmania.entities.buildables;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.inventory.Inventory;

public class Sceptre extends Buildable {
    private int duration;
    private boolean isUsed;

    public Sceptre(int duration) {
        super(null);
        this.duration = duration;
        this.isUsed = false;
    }

    @Override
    public void use(Game game) {
        this.setUsed(true);
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 1, 1));
    }

    @Override
    public int getDurability() {
        return Integer.MAX_VALUE;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String toString() {
        return "sceptre";
    }

    public boolean buildableStatus(Inventory inv) {
        int wood = inv.count(Wood.class);
        int arrows = inv.count(Arrow.class);
        int sunStones = inv.count(SunStone.class);
        int treasure = inv.count(Treasure.class);
        int keys = inv.count(Key.class);

        return (wood >= 1 || arrows >= 2) && (keys >= 1 || treasure >= 2) && (sunStones >= 1);
    }

    public Buildable checkBuildable(Inventory inv, boolean remove, EntityFactory factory) {
        List<Wood> wood = inv.getEntities(Wood.class);
        List<Arrow> arrows = inv.getEntities(Arrow.class);
        List<Treasure> treasure = inv.getEntities(Treasure.class);
        List<Key> keys = inv.getEntities(Key.class);
        List<SunStone> sunStone = inv.getEntities(SunStone.class);

        if (buildableStatus(inv)) {
            if (remove) {
                if (wood.size() >= 1) {
                    inv.remove(wood.get(0));
                } else {
                    inv.remove(arrows.get(0));
                    inv.remove(arrows.get(1));
                }

                inv.remove(sunStone.get(0));

                if (sunStone.size() >= 2) {
                    inv.remove(sunStone.get(0));
                } else if (keys.size() >= 1) {
                    inv.remove(keys.get(0));
                } else {
                    if (sunStone.size() <= 1) {
                        inv.remove(treasure.get(0));
                    }
                }
            }
            return factory.buildSceptre();
        }
        return null;
    }
}
