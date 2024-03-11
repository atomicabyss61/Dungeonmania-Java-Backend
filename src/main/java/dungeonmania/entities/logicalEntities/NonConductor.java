package dungeonmania.entities.logicalEntities;

import dungeonmania.entities.logicalEntities.LogicStrat.ANDStrat;
import dungeonmania.entities.logicalEntities.LogicStrat.COANDStrat;
import dungeonmania.entities.logicalEntities.LogicStrat.LogicStrat;
import dungeonmania.entities.logicalEntities.LogicStrat.ORStrat;
import dungeonmania.entities.logicalEntities.LogicStrat.XORStrat;
import dungeonmania.util.Position;

public class NonConductor extends LogicalEntity {
    private LogicStrat state;

    public NonConductor(Position position, String logic) {
        super(position);
        state = findStrategy(logic);
    }

    private LogicStrat findStrategy(String logic) {
        if (logic.equals("AND")) {
            return new ANDStrat(logic);
        } else if (logic.equals("OR")) {
            return new ORStrat(logic);
        } else if (logic.equals("CO_AND")) {
            return new COANDStrat(logic);
        } else if (logic.equals("XOR")) {
            return new XORStrat(logic);
        }
        return null;
    }

    public LogicStrat getLogicStrat() {
        return state;
    }

    public void activateEntity(int fromID) {

        // Checking for activated logical entities, aswell as entities activated this tick.
        int activeAdj = 0;
        int activeTick = 0;
        int otherActiveAdj = 0;
        int numAdj = 0;
        for (LogicalEntity e : getObservees()) {
            if (e instanceof Conductor) {
                numAdj++;
            }

            if (e.isActive() && e.checked()) {
                if (e.getSource() == fromID || e.getSource() == 0) {
                    activeAdj++;
                    if (e.isActivatedCurrTick()) {
                        activeTick++;
                    }
                }
            } else if (e.isActive()) {
                if (e.getSource() != fromID && e.getSource() != -1) {
                    otherActiveAdj++;
                }
            }
        }

        // updating state of LogicalEntity based on conditions
        if (getLogicStrat() != null && getLogicStrat().performLogic(activeAdj, otherActiveAdj, activeTick, numAdj)) {
            if (!isActive()) {
                setActivatedCurrTick(true);
            }
            setActive(true);
            setSource(fromID);
        } else {
            setActive(false);
            setSource(-1);
        }

        setChecked(true);
    }
}
