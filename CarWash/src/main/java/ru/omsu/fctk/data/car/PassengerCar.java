package ru.omsu.fctk.data.car;

import java.util.Objects;

public class PassengerCar implements Car {
    private String carModel;
    private int washingTime;

    public PassengerCar() {
        this.carModel = "passenger car";
        this.washingTime = 5;
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
        PassengerCar that = (PassengerCar) o;
        return washingTime == that.washingTime && Objects.equals(carModel, that.carModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carModel, washingTime);
    }
}
