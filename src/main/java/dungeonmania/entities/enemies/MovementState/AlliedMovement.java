package dungeonmania.entities.enemies.MovementState;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class AlliedMovement extends MovementState {
    public AlliedMovement(Enemy entity) {
        setSubject(entity);
    }

    public String toString() {
        return "Allied";
    }

    public void move(Game game, Position currPos) {
        Position nextPos;
        GameMap map = game.getMap();
        Mercenary enemy = (Mercenary) getSubject();
        Player player = map.getPlayer();

        nextPos = enemy.isAdjacentToPlayer() ? player.getPreviousDistinctPosition()
                : map.dijkstraPathFind(currPos, player.getPosition(), getSubject());

        if (!enemy.isAdjacentToPlayer() && Position.isAdjacent(player.getPosition(), nextPos)) {
            enemy.setIsAdjacentToPlayer(true);
        }

        map.moveTo(getSubject(), nextPos);
    }
}
