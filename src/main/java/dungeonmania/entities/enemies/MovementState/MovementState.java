package dungeonmania.entities.enemies.MovementState;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.util.Position;

public abstract class MovementState {
    private Enemy subject;

    public void setSubject(Enemy entity) {
        subject = entity;
    }

    public Enemy getSubject() {
        return subject;
    }

    public abstract void move(Game game, Position currPos);

    public abstract String toString();
}
