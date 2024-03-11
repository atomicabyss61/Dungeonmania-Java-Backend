package dungeonmania.entities.EntityInterfaces;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public interface OnOverlap {
    public abstract void onOverlap(GameMap map, Entity entity);
}
