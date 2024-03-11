package dungeonmania.entities.enemies.MovementState;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class RetreatMovement extends MovementState {
    public RetreatMovement(Enemy entity) {
        setSubject(entity);
    }

    public String toString() {
        return "Retreat";
    }

    public void move(Game game, Position currPos) {
        GameMap map = game.getMap();

        Position plrDiff = Position.calculatePositionBetween(map.getPlayer().getPosition(), currPos);

        Position moveX = (plrDiff.getX() >= 0) ? Position.translateBy(currPos, Direction.RIGHT)
                : Position.translateBy(currPos, Direction.LEFT);
        Position moveY = (plrDiff.getY() >= 0) ? Position.translateBy(currPos, Direction.UP)
                : Position.translateBy(currPos, Direction.DOWN);
        Position offset = currPos;

        if (plrDiff.getY() == 0 && map.canMoveTo(getSubject(), moveX)) {
            offset = moveX;
        } else if (plrDiff.getX() == 0 && map.canMoveTo(getSubject(), moveY)) {
            offset = moveY;
        } else if (Math.abs(plrDiff.getX()) >= Math.abs(plrDiff.getY())) {
            if (map.canMoveTo(getSubject(), moveX)) {
                offset = moveX;
            } else if (map.canMoveTo(getSubject(), moveY)) {
                offset = moveY;
            } else {
                offset = currPos;
            }
        } else {
            if (map.canMoveTo(getSubject(), moveY)) {
                offset = moveY;
            } else if (map.canMoveTo(getSubject(), moveX)) {
                offset = moveX;
            } else {
                offset = currPos;
            }
        }

        map.moveTo(getSubject(), offset);
    }
}
