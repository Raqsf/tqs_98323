package covidtracker.covidtracker.model;

public class Cases extends Stats {
    private String newCases;
    private int activeCases;
    private int criticalCases;
    private int recovered;

    // NOTE: new, active, critical and recovered can be null

    public Cases(String newCases, int activeCases, int criticalCases, int recovered, String oneMPop, int totalCases) {
        super(oneMPop, totalCases);
        this.newCases = newCases;
        this.activeCases = activeCases;
        this.criticalCases = criticalCases;
        this.recovered = recovered;
    }

    public Cases(int criticalCases, String oneMPop, int totalCases) {
        super(oneMPop, totalCases);
        this.criticalCases = criticalCases;
    }

    public Cases(String oneMPop, int totalCases) {
        super(oneMPop, totalCases);
    }

    public String getNewCases() {
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
