package covidtracker.covidtracker.model;

public class Stats {
    private int oneMPop;     //cases per 1 million population
    private int total;

    // NOTE: oneMPop and total can be null

    public Stats(int oneMPop, int total) {
        this.oneMPop = oneMPop;
        this.total = total;
    }

    // public Stats() {}

    public int getOneMPop() {
        return oneMPop;
    }

    public int getTotal() {
        return total;
    }
}
