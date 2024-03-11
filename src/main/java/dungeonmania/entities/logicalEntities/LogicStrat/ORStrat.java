package dungeonmania.entities.logicalEntities.LogicStrat;

public class ORStrat extends LogicStrat {
    public ORStrat(String logic) {
        setLogic(logic);
    }

    public boolean performLogic(int activeAdj, int otherActiveAdj, int activeTick, int numAdj) {
        return activeAdj + otherActiveAdj > 0;
    }
}
