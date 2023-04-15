package ru.omsu.fctk.CarWashService;

import ru.omsu.fctk.data.car.Car;

import java.util.List;
import java.util.Map;

public interface ICarWashProfit {
    long getProfitOfCarWash(Map<Integer, List<Car>> timeToListOfCars, int countOfPosts, long profitPerMinute);
}
