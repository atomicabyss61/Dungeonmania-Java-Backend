package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.EntityInterfaces.OnDestroy;
import dungeonmania.entities.EntityInterfaces.OnOverlap;
import dungeonmania.entities.enemies.MovementState.MovementState;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Enemy extends Entity implements Battleable, OnOverlap, OnDestroy {
    private BattleStatistics battleStatistics;
    private MovementState moveStrat;

    public Enemy(Position position, double health, double attack) {
        super(position.asLayer(Entity.CHARACTER_LAYER));
        battleStatistics = new BattleStatistics(health, attack, 0, BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER,
                BattleStatistics.DEFAULT_ENEMY_DAMAGE_REDUCER);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof Player;
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return battleStatistics;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player && !((Player) entity).isInvincible()) {
            Player player = (Player) entity;
            map.getGame().battle(player, this);
        }
    }

    @Override
    public void onDestroy(GameMap map) {
        map.unsubscribe(getId());
    }

    public void setMovementStrategy(MovementState strat) {
        moveStrat = strat;
    }

    public MovementState getMovementStrategy() {
        return moveStrat;
    }

    public void move(Game game) {

        if (getMovementStrategy() == null) {
            setMovementStrategy(findMovementStrategy(game));
        }

        if (!getMovementStrategy().toString().equals(findMovementStrategy(game).toString())) {
            setMovementStrategy(findMovementStrategy(game));
        }

        // performs the move
        getMovementStrategy().move(game, getPosition());
    }

    public abstract MovementState findMovementStrategy(Game game);
}
