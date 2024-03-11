package dungeonmania.entities.logicalEntities.LogicStrat;

public class XORStrat extends LogicStrat {
    public XORStrat(String logic) {
        setLogic(logic);
    }

    public boolean performLogic(int activeAdj, int otherActiveAdj, int activeTick, int numAdj) {
        return activeAdj + otherActiveAdj == 1;
    }
}
