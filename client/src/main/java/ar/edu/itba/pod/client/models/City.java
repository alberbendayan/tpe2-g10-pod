package ar.edu.itba.pod.client.models;

public enum City {
    NYC ("NYC"),
    CHI ("CHI");

    private final String name;

    City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static City fromString(String name) {
        for (City city : City.values()) {
            if (city.name.equals(name)) {
                return city;
            }
        }
        return null;
    }
}
