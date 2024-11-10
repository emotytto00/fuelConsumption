public class ConsumptionRecord {
    private double distance;
    private double fuel;
    private double consumption;
    private String language;

    public ConsumptionRecord(double distance, double fuel, double consumption, String language) {
        this.distance = distance;
        this.fuel = fuel;
        this.consumption = consumption;
        this.language = language;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return String.format("Distance: %.2f km, Fuel: %.2f liters, Consumption: %.2f L/100km, Language: %s",
                distance, fuel, consumption, language);
    }
}
