package ru.omsu.fctk.data;

import de.scravy.pair.Pair;
import ru.omsu.fctk.data.car.Car;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CarWashCondition {
    public final List<Pair<Integer, Car>> carsInQueue;
    public final Map<Integer, Pair<Integer, Car>> carsInPosts;
    public final int currentTime;

    public final int countOfPosts;

    public CarWashCondition(List<Pair<Integer, Car>> carsInQueue, Map<Integer, Pair<Integer, Car>> carsInPosts, int currentTime, int countOfPosts) {
        this.carsInQueue = carsInQueue;
        this.carsInPosts = carsInPosts;
        this.currentTime = currentTime;
        this.countOfPosts = countOfPosts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarWashCondition that = (CarWashCondition) o;
        return currentTime == that.currentTime && countOfPosts == that.countOfPosts && Objects.equals(carsInQueue, that.carsInQueue) && Objects.equals(carsInPosts, that.carsInPosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carsInQueue, carsInPosts, currentTime, countOfPosts);
    }
}
