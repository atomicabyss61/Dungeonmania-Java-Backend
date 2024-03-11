package dungeonmania.entities.EntityInterfaces;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public interface OnMovedAway {
    public abstract void onMovedAway(GameMap map, Entity entity);
}
