package ru.omsu.fctk.data.car;

import java.util.Objects;

public class Minibus implements Car {
    private String carModel;
    private int washingTime;

    public Minibus() {
        this.carModel = "minibus";
        this.washingTime = 10;
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
        Minibus minibus = (Minibus) o;
        return washingTime == minibus.washingTime && Objects.equals(carModel, minibus.carModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carModel, washingTime);
    }
}
