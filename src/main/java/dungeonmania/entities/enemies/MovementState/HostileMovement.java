package dungeonmania.entities.enemies.MovementState;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class HostileMovement extends MovementState {
    public HostileMovement(Enemy entity) {
        setSubject(entity);
    }

    public String toString() {
        return "Hostile";
    }

    public void move(Game game, Position currPos) {
        Position nextPos;
        GameMap map = game.getMap();
        Player player = game.getPlayer();

        nextPos = map.dijkstraPathFind(currPos, player.getPosition(), getSubject());

        map.moveTo(getSubject(), nextPos);
    }
}
