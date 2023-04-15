package ru.omsu.fctk.data;

import ru.omsu.fctk.data.car.Car;

import java.util.List;
import java.util.Objects;

public class CarWashCondition {
    public final List<Car> carsInQueue;
    public final List<Car> carsInPosts;
    public final int currentTime;

    public CarWashCondition(List<Car> carsInQueue, List<Car> carsInPosts, int currentTime) {
        if (carsInQueue == null || carsInPosts == null || currentTime < 0) throw new IllegalArgumentException();
        this.carsInQueue = carsInQueue;
        this.carsInPosts = carsInPosts;
        this.currentTime = currentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarWashCondition that = (CarWashCondition) o;
        return currentTime == that.currentTime && Objects.equals(carsInQueue, that.carsInQueue) && Objects.equals(carsInPosts, that.carsInPosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carsInQueue, carsInPosts, currentTime);
    }
}
