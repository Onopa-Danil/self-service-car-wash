package ru.omsu.fctk.data;

import de.scravy.pair.Pair;
import ru.omsu.fctk.data.car.Car;

import java.util.List;
import java.util.Map;
import java.util.Objects;
/*Класс хранит информацию об очереди на автомойке в определенный момент времени*/
public class CarWashCondition {
    // Машины в очереди, первым элементом пары является время прибытия машины на автомойку
    public final List<Pair<Integer, Car>> carsInQueue;
    //отображение поста в пару, где первым элементом пары является время прибытия машины в этот пост
    public final Map<Integer, Pair<Integer, Car>> carsInPosts;
    //текущее время
    public final int currentTime;

    // количество постов автомойки
    public final int countOfPosts;
    public final long currentProfit;
    public CarWashCondition(List<Pair<Integer, Car>> carsInQueue, Map<Integer, Pair<Integer, Car>> carsInPosts, int currentTime, int countOfPosts, long currentProfit) {
        this.carsInQueue = carsInQueue;
        this.carsInPosts = carsInPosts;
        this.currentTime = currentTime;
        this.countOfPosts = countOfPosts;
        this.currentProfit = currentProfit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarWashCondition that = (CarWashCondition) o;
        return currentTime == that.currentTime && countOfPosts == that.countOfPosts && currentProfit == that.currentProfit && Objects.equals(carsInQueue, that.carsInQueue) && Objects.equals(carsInPosts, that.carsInPosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carsInQueue, carsInPosts, currentTime, countOfPosts, currentProfit);
    }

    @Override
    public String toString() {
        return "CarWashCondition{" +
                "carsInQueue=" + carsInQueue +
                ", carsInPosts=" + carsInPosts +
                ", currentTime=" + currentTime +
                ", countOfPosts=" + countOfPosts +
                ", currentProfit=" + currentProfit +
                '}';
    }
}
