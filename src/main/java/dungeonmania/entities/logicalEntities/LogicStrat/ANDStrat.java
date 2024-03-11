package dungeonmania.entities.logicalEntities.LogicStrat;

public class ANDStrat extends LogicStrat {
    public ANDStrat(String logic) {
        setLogic(logic);
    }

    public boolean performLogic(int activeAdj, int otherActiveAdj, int activeTick, int numAdj) {
        return activeAdj + otherActiveAdj > 1 && activeAdj + otherActiveAdj == numAdj;
    }
}
