package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Key extends Collectable {
    private int number;

    public Key(Position position, int number) {
        super(position);
        this.number = number;
    }

    public int getnumber() {
        return number;
    }

    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {

            if (((Player) entity).numKeys() > 0) {
                return;
            }

            if (!((Player) entity).pickUp(this))
                return;
            map.destroyEntity(this);
        }
    }

    public boolean isValid(int num) {
        return num == number;
    }
}
