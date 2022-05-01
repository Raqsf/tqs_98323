package covidtracker.covidtracker.model;

public class Cases extends Stats {
    private int newCases;
    private int activeCases;
    private int criticalCases;
    private int recovered;

    // When there's no info about some statistic, it's value is -1

    public Cases(int newCases, int activeCases, int criticalCases, int recovered, double oneMPop, int totalCases) {
        super(oneMPop, totalCases);
        this.newCases = newCases;
        this.activeCases = activeCases;
        this.criticalCases = criticalCases;
        this.recovered = recovered;
    }

    public int getNewCases() {
        return newCases;
    }

    public int getActiveCases() {
        return activeCases;
    }

    public int getCriticalCases() {
        return criticalCases;
    }

    public int getRecovered() {
        return recovered;
    }
    
}
