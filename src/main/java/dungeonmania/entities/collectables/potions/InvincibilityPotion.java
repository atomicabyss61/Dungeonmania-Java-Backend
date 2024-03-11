package dungeonmania.entities.collectables.potions;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Player;
import dungeonmania.entities.playerState.InvincibleState;
import dungeonmania.entities.playerState.PlayerState;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Potion {
    public static final int DEFAULT_DURATION = 8;

    public InvincibilityPotion(Position position, int duration) {
        super(position, duration);
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 1, 1, true, true));
    }

    public PlayerState getPlayerState(Player player) {
        return new InvincibleState(player);
    }
}
