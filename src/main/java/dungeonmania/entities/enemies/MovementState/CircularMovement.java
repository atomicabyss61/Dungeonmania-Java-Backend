package dungeonmania.entities.enemies.MovementState;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Boulder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.util.Position;

public class CircularMovement extends MovementState {
    public CircularMovement(Enemy entity) {
        setSubject(entity);
    }

    public String toString() {
        return "Circular";
    }

    public void move(Game game, Position currPos) {
        Spider spider = (Spider) getSubject();

        Position nextPos = spider.getMovementTrajectory().get(spider.getNextPositionElement());
        List<Entity> entities = game.getMap().getEntities(nextPos);
        if (entities != null && entities.size() > 0 && entities.stream().anyMatch(e -> e instanceof Boulder)) {
            spider.setForward(!spider.isForward());
            spider.updateNextPosition();
            spider.updateNextPosition();
        }
        nextPos = spider.getMovementTrajectory().get(spider.getNextPositionElement());
        entities = game.getMap().getEntities(nextPos);
        if (entities == null || entities.size() == 0
                || entities.stream().allMatch(e -> e.canMoveOnto(game.getMap(), spider))) {
            game.getMap().moveTo(spider, nextPos);
            spider.updateNextPosition();
        }
    }
}
