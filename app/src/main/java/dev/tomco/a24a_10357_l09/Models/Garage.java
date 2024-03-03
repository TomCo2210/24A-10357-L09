package dev.tomco.a24a_10357_l09.Models;

import java.util.HashMap;

public class Garage {

    private String name ="" ;

    private HashMap<String, Car> allCars = new HashMap<>();

    public Garage() {
    }

    public String getName() {
        return name;
    }

    public Garage setName(String name) {
        this.name = name;
        return this;
    }

    public HashMap<String, Car> getAllCars() {
        return allCars;
    }

    public Garage setAllCars(HashMap<String, Car> allCars) {
        this.allCars = allCars;
        return this;
    }
}
