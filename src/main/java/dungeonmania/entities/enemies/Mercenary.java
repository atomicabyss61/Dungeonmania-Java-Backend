package dungeonmania.entities.enemies;

import java.util.stream.IntStream;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.SunStone;
// import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.enemies.MovementState.AlliedMovement;
import dungeonmania.entities.enemies.MovementState.HostileMovement;
import dungeonmania.entities.enemies.MovementState.MovementState;
import dungeonmania.entities.enemies.MovementState.RandomMovement;
import dungeonmania.entities.enemies.MovementState.RetreatMovement;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private boolean isAdjacentToPlayer = false;

    private int sceptreDuration = 0;
    private boolean isBribed = false;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double allyAttack, double allyDefence) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.allyAttack = allyAttack;
        this.allyDefence = allyDefence;
    }

    public boolean isAllied() {
        if (sceptreDuration > 0) {
            return true;
        }

        return allied;
    }

    public boolean isAdjacentToPlayer() {
        return isAdjacentToPlayer;
    }

    public void setIsAdjacentToPlayer(boolean state) {
        isAdjacentToPlayer = state;
    }

    public MovementState findMovementStrategy(Game game) {
        GameMap map = game.getMap();

        if (isAllied()) {
            return new AlliedMovement(this);
        } else if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            return new RetreatMovement(this);
        } else if (map.getPlayer().getEffectivePotion() instanceof InvisibilityPotion) {
            return new RandomMovement(this);
        }
        return new HostileMovement(this);
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied)
            return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        int amount = player.countEntityOfType(Treasure.class) - player.countEntityOfType(SunStone.class);
        return ((isInBribeRad(player.getPosition()) && amount >= bribeAmount) || (sceptreDuration > 0));
    }

    public boolean isInBribeRad(Position pos) {
        return IntStream.rangeClosed(getPosition().getX() - bribeRadius, getPosition().getX() + bribeRadius)
                .anyMatch(i -> IntStream
                        .rangeClosed(getPosition().getY() - bribeRadius, getPosition().getY() + bribeRadius)
                        .anyMatch(j -> i == pos.getX() && j == pos.getY()));

    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        // System.out.println("bribe");
        for (int i = 0; i < bribeAmount; i++) {
            player.bribeUsingTreasure();
        }
    }

    @Override
    public void interact(Player player, Game game) {
        // System.out.println("interact");
        Sceptre sceptre = player.getUnusedSceptre();
        if (sceptre != null) {
            allied = true;
            sceptreDuration = sceptre.getDuration();
            sceptre.setUsed(true);
            // System.out.println("INTERACTION OCCURS NOW");
        } else {
            allied = true;
            bribe(player);
            setBribed(true);
            if (!isAdjacentToPlayer && Position.isAdjacent(player.getPosition(), getPosition()))
                isAdjacentToPlayer = true;
        }

    }

    @Override
    public void move(Game game) {
        // checkSceptre
        if (getMovementStrategy() == null) {
            setMovementStrategy(findMovementStrategy(game));
        }

        if (!getMovementStrategy().toString().equals(findMovementStrategy(game).toString())) {
            setMovementStrategy(findMovementStrategy(game));
        }

        // performs the move
        getMovementStrategy().move(game, getPosition());

        if (sceptreDuration > 0) {
            sceptreDuration--;
            if (sceptreDuration == 0 && !isBribed) {
                allied = false;
            }
        }

    }

    @Override
    public boolean isInteractable(Player player) {
        if (!allied && canBeBribed(player)) {
            return true;
        } else if (player.getUnusedSceptre() != null) {
            return true;
        } else if (allied && !isBribed && canBeBribed(player)) {
            return true;
        }

        return false;
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        if (!allied && sceptreDuration == 0)
            return super.getBattleStatistics();
        return new BattleStatistics(0, allyAttack, allyDefence, 1, 1);
    }

    public int getSceptreDuration() {
        return sceptreDuration;
    }

    public void setSceptreDuration(int sceptreDuration) {
        this.sceptreDuration = sceptreDuration;
    }

    public boolean isBribed() {
        return isBribed;
    }

    public void setBribed(boolean isBribed) {
        this.isBribed = isBribed;
    }

}
