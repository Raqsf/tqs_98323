package covidtracker.covidtracker.model;

public class CountryStats {
    private String continent;
    private String name;
    private int population;
    private Cases cases;
    private Deaths deaths;
    private Tests tests;
    private String day;
    private String time;


    public CountryStats(String continent, String name, int population, Cases cases, Deaths deaths, Tests tests, String day, String time) {
        this.continent = continent;
        this.name = name;
        this.population = population;
        this.cases = cases;
        this.deaths = deaths;
        this.tests = tests;
        this.day = day;
        this.time = time;
    }

    public String getContinent() {
        return continent;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public Cases getCases() {
        return cases;
    }

    public Deaths getDeaths() {
        return deaths;
    }

    public Tests getTests() {
        return tests;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
