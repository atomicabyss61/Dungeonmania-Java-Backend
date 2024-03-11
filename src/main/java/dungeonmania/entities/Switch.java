package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.EntityInterfaces.OnMovedAway;
import dungeonmania.entities.EntityInterfaces.OnOverlap;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.logicalEntities.Conductor;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends Conductor implements OnOverlap, OnMovedAway {
    private List<Bomb> bombs;

    public Switch(Position position) {
        // change the logic condition.
        super(position.asLayer(Entity.ITEM_LAYER));
        bombs = new ArrayList<>();
    }

    public void subscribe(Bomb b) {
        bombs.add(b);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (isActive()) {
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    public void unsubscribe(Bomb b) {
        bombs.remove(b);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            setActive(true);
            bombs.stream().forEach(b -> b.notify(map));
            setChecked(true);
            setSource(0);
            notifyAdjacent(getLogicId());
        }
    }

    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            setActive(false);
            setChecked(true);
            setSource(-1);
            notifyAdjacent(getLogicId());
        }
    }
}
