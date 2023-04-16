package ru.omsu.fctk.data.car;

import java.util.Objects;

public class Car {
    public final String carModel;
    public final int washingTime;

    public Car(String carModel, int washingTime) {
        if(carModel == null || washingTime <= 0) throw new IllegalArgumentException();
        this.carModel = carModel;
        this.washingTime = washingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return washingTime == car.washingTime && Objects.equals(carModel, car.carModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carModel, washingTime);
    }

    @Override
    public String toString() {
        return "Car{" +
                "carModel='" + carModel + '\'' +
                ", washingTime=" + washingTime +
                '}';
    }
}
