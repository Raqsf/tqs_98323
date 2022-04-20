package covidtracker.covidtracker.model;

public class Stats {
    private String oneMPop;     //cases per 1 million population
    private int total;

    // NOTE: oneMPop and total can be null

    public Stats(String oneMPop, int total) {
        this.oneMPop = oneMPop;
        this.total = total;
    }

    public Stats() {}

    public String getOneMPop() {
        return oneMPop;
    }

    public int getTotal() {
        return total;
    }
}
