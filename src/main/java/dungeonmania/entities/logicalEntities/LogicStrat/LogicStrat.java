package dungeonmania.entities.logicalEntities.LogicStrat;

public abstract class LogicStrat {
    private String logic;

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public abstract boolean performLogic(int activeAdj, int otherActiveAdj, int activeTick, int numAdj);
}
