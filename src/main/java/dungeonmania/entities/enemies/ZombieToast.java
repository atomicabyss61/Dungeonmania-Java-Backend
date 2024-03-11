package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.enemies.MovementState.MovementState;
import dungeonmania.entities.enemies.MovementState.RandomMovement;
import dungeonmania.entities.enemies.MovementState.RetreatMovement;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;
    // private Random randGen = new Random();

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
    }

    public MovementState findMovementStrategy(Game game) {
        GameMap map = game.getMap();

        if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            return new RetreatMovement(this);
        }
        return new RandomMovement(this);
    }
}
