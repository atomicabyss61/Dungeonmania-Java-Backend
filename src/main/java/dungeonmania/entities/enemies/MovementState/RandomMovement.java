package dungeonmania.entities.enemies.MovementState;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class RandomMovement extends MovementState {
    public RandomMovement(Enemy entity) {
        setSubject(entity);
    }

    public String toString() {
        return "Random";
    }

    public void move(Game game, Position currPos) {
        Position nextPos;
        GameMap map = game.getMap();

        Random randGen = new Random();
        List<Position> pos = currPos.getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(getSubject(), p)).collect(Collectors.toList());
        if (pos.size() == 0) {
            nextPos = currPos;
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
        }

        map.moveTo(getSubject(), nextPos);
    }
}
