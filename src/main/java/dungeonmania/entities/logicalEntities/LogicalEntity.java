package dungeonmania.entities.logicalEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

import java.util.concurrent.ThreadLocalRandom;

public abstract class LogicalEntity extends Entity {
    private boolean isActive;
    private boolean activatedCurrTick;
    private boolean checked;
    private int source;
    private int logicID;
    private List<LogicalEntity> observees = new ArrayList<>();

    public LogicalEntity(Position position) {
        super(position);
        isActive = false;
        activatedCurrTick = false;
        checked = false;
        source = -1;
        logicID = ThreadLocalRandom.current().nextInt(1, 100000);
    }

    public void setObservers(List<LogicalEntity> list) {
        observees = list;
    }

    public List<LogicalEntity> getObservees() {
        return observees;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getLogicId() {
        return logicID;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActivatedCurrTick() {
        return activatedCurrTick;
    }

    public void setActivatedCurrTick(boolean activatedCurrTick) {
        this.activatedCurrTick = activatedCurrTick;
    }

    public boolean checked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public abstract void activateEntity(int fromID);

    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }
}
