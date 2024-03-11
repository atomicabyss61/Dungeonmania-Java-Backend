package dungeonmania.entities.logicalEntities;

import java.util.List;

import dungeonmania.entities.Switch;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Conductor extends LogicalEntity {
    public Conductor(Position position) {
        super(position);
    }

    public void notifyAdjacent(int source) {
        for (LogicalEntity e : getObservees()) {
            if ((!e.checked() || !(e instanceof Conductor)) && !(e instanceof Switch && !e.isActive())) {
                e.activateEntity(source);
            }
        }
    }

    public void activateEntity(int fromID) {

        // Checking for activated logical entities, aswell as entities activated this tick.
        int activeAdj = 0;
        int otherActiveAdj = 0;
        for (LogicalEntity e : getObservees()) {
            if (e.isActive() && e.checked() && e instanceof Conductor) {
                if (e.getSource() == fromID || e.getSource() == 0) {
                    activeAdj++;
                }
            } else if (e.isActive() && e instanceof Conductor) {
                if (e.getSource() != fromID && e.getSource() != -1) {
                    otherActiveAdj++;
                }
            }
        }

        // updating state of LogicalEntity based on conditions
        if (activeAdj + otherActiveAdj > 0) {
            if (!isActive()) {
                setActivatedCurrTick(true);
            }
            setActive(true);
            setSource(fromID);
        } else if (this instanceof Wire) {
            setActive(false);
            setSource(-1);
        }

        setChecked(true);

        // notifying oberservers.
        notifyAdjacent(fromID);
    }

    public void subscribe(Bomb b) {
        List<LogicalEntity> observees = getObservees();
        observees.add(b);
        setObservers(observees);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        this.subscribe(bomb);
        if (isActive()) {
            for (LogicalEntity ent : getObservees()) {
                if (ent instanceof Bomb) {
                    ((Bomb) ent).notify(map);
                }
            }
        }
    }

    public void unsubscribe(Bomb b) {
        List<LogicalEntity> observees = getObservees();
        observees.remove(b);
        setObservers(observees);
    }
}
