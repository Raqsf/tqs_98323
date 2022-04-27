package covidtracker.covidtracker.model;

public class Deaths extends Stats {
    private int newDeaths;

    // NOTE: newDeaths can be null 
    
    public Deaths(int newDeaths, double oneMPop, int totalCases) {
        super(oneMPop, totalCases);
        this.newDeaths = newDeaths;
    }

    /* public Deaths(int oneMPop, int totalCases) {
        super(oneMPop, totalCases);
    } */

    public int getNewDeaths() {
        return newDeaths;
    }
}
