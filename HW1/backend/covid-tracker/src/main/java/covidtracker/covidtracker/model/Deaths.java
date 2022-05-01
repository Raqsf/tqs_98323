package covidtracker.covidtracker.model;

public class Deaths extends Stats {
    private int newDeaths;

    // When there's no info about some statistic, it's value is -1
    
    public Deaths(int newDeaths, double oneMPop, int totalCases) {
        super(oneMPop, totalCases);
        this.newDeaths = newDeaths;
    }

    public int getNewDeaths() {
        return newDeaths;
    }
}
