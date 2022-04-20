package covidtracker.covidtracker.model;

public class Deaths extends Stats {
    private String newDeaths;

    // NOTE: newDeaths can be null 
    
    public Deaths(String newDeaths, String oneMPop, int totalCases) {
        super(oneMPop, totalCases);
        this.newDeaths = newDeaths;
    }

    public Deaths(String oneMPop, int totalCases) {
        super(oneMPop, totalCases);
    }

    public String getNewDeaths() {
        return newDeaths;
    }
}
