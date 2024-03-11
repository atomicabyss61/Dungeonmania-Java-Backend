package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.EntityInterfaces.OnOverlap;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.entities.logicalEntities.Conductor;
import dungeonmania.entities.logicalEntities.LogicalEntity;
import dungeonmania.entities.logicalEntities.NonConductor;
import dungeonmania.map.GameMap;

public class Bomb extends NonConductor implements InventoryItem, OnOverlap {
    public enum State {
        SPAWNED, INVENTORY, PLACED
    }

    public static final int DEFAULT_RADIUS = 1;
    private State state;
    private int radius;

    public Bomb(Position position, int radius, String logic) {
        super(position, logic);
        state = State.SPAWNED;
        this.radius = radius;
    }

    public void subscribe(LogicalEntity s) {
        List<LogicalEntity> observees = getObservees();
        observees.add(s);
        setObservers(observees);
    }

    public void unsubscribe(LogicalEntity b) {
        List<LogicalEntity> observees = getObservees();
        observees.remove(b);
        setObservers(observees);
    }

    public void notify(GameMap map) {
        explode(map);
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (state != State.SPAWNED)
            return;
        if (entity instanceof Player) {
            if (!((Player) entity).pickUp(this))
                return;
            for (LogicalEntity ent : getObservees()) {
                if (ent instanceof LogicalEntity) {
                    unsubscribe(this);
                }
            }
            map.destroyEntity(this);
        }
        this.state = State.INVENTORY;
    }

    public void onPutDown(GameMap map, Position p) {
        translate(Position.calculatePositionBetween(getPosition(), p));
        map.addEntity(this);
        this.state = State.PLACED;
        List<Position> adjPosList = getPosition().getCardinallyAdjacentPositions();
        adjPosList.stream().forEach(node -> {
            List<Entity> entities = map.getEntities(node).stream().filter(e -> (e instanceof Conductor))
                    .collect(Collectors.toList());
            entities.stream().map(Conductor.class::cast).forEach(s -> s.subscribe(this, map));
            entities.stream().map(Conductor.class::cast).forEach(s -> this.subscribe(s));
        });
    }

    public void explode(GameMap map) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                List<Entity> entities = map.getEntities(new Position(i, j));
                entities = entities.stream().filter(e -> !(e instanceof Player)).collect(Collectors.toList());
                for (Entity e : entities) {
                    if (e instanceof Enemy) {
                        int numEnemiesDestroyed = map.getNumEnemiesDestroyed() + 1;
                        map.setNumEnemiesDestroyed(numEnemiesDestroyed);
                    }
                    map.destroyEntity(e);
                }

            }
        }
    }

    public State getState() {
        return state;
    }
}
