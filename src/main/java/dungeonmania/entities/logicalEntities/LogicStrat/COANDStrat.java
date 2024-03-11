package dungeonmania.entities.logicalEntities.LogicStrat;

public class COANDStrat extends LogicStrat {
    public COANDStrat(String logic) {
        setLogic(logic);
    }

    public boolean performLogic(int activeAdj, int otherActiveAdj, int activeTick, int numAdj) {
        return activeAdj + otherActiveAdj > 1 && activeTick > 1;
    }
}
