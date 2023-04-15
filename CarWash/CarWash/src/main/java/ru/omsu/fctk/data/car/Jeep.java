package ru.omsu.fctk.data.car;

import java.util.Objects;

public class Jeep implements Car {
    private String carModel;
    private int washingTime;

    public Jeep() {
        this.carModel = "jeep";
        this.washingTime = 8;
    }

    @Override
    public String getCarModel() {
        return carModel;
    }

    @Override
    public int getWashingTime() {
        return washingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jeep jeep = (Jeep) o;
        return washingTime == jeep.washingTime && Objects.equals(carModel, jeep.carModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carModel, washingTime);
    }
}
