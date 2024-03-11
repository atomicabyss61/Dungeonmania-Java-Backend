package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.EntityInterfaces.OnDestroy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends Entity implements Interactable, OnDestroy {
    public static final int DEFAULT_SPAWN_INTERVAL = 0;
    private int spawnInterval;

    public ZombieToastSpawner(Position position, int spawnInterval) {
        super(position);
        this.spawnInterval = spawnInterval;
    }

    public int getSpawnInterval() {
        return spawnInterval;
    }

    public void setSpawnInterval(int spawnInterval) {
        this.spawnInterval = spawnInterval;
    }

    public void spawn(Game game) {
        game.spawnZombie(this);
    }

    @Override
    public void onDestroy(GameMap map) {
        map.unsubscribe(getId());
    }

    @Override
    public void interact(Player player, Game game) {
        player.getInventory().getWeapon().use(game);
        game.getMap().destroyEntity(this);
    }

    @Override
    public boolean isInteractable(Player player) {
        return Position.isAdjacent(player.getPosition(), getPosition()) && player.hasWeapon();
    }
}
