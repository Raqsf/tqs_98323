package covidtracker.covidtracker.model;

public class Stats {
    private double oneMPop;     //cases per 1 million population
    private int total;
    
    // When there's no info about some statistic, it's value is -1

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
