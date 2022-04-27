package covidtracker.covidtracker.model;

public class Stats {
    private double oneMPop;     //cases per 1 million population
    private int total;

    // NOTE: oneMPop and total can be null

    public Stats(double oneMPop, int total) {
        this.oneMPop = oneMPop;
        this.total = total;
    }

    public double getOneMPop() {
        return oneMPop;
    }

    public int getTotal() {
        return total;
    }
}
