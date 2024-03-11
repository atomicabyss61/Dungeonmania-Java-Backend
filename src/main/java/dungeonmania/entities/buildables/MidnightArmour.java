package dungeonmania.entities.buildables;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.inventory.Inventory;

public class MidnightArmour extends Buildable {
    private double defence;
    private double attack;

    public MidnightArmour(double defence, double attack) {
        super(null);
        this.defence = defence;
        this.attack = attack;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, this.attack, this.defence, 1, 1));
    }

    @Override
    public void use(Game game) {

    }

    @Override
    public int getDurability() {
        return Integer.MAX_VALUE;
    }

    public String toString() {
        return "midnight_armour";
    }

    public boolean buildableStatus(Inventory inv) {
        int sunStones = inv.count(SunStone.class);
        int swords = inv.count(Sword.class);

        return swords >= 1 && sunStones >= 1;
    }

    public Buildable checkBuildable(Inventory inv, boolean remove, EntityFactory factory) {
        List<SunStone> sunStone = inv.getEntities(SunStone.class);
        List<Sword> swords = inv.getEntities(Sword.class);

        if (buildableStatus(inv)) {
            if (remove) {
                inv.remove(swords.get(0));
                inv.remove(sunStone.get(0));
            }
            return factory.buildMidnightArmour();
        }
        return null;
    }
}
