package ru.omsu.fctk.CarWashService;

import ru.omsu.fctk.data.car.Car;

import java.util.List;
import java.util.Map;

public class CarWashProfit implements ICarWashProfit{

    @Override
    public long getProfitOfCarWash(Map<Integer, List<Car>> timeToListOfCars, int countOfPosts, long profitPerMinute) {
        long profit = 0;
        for(Map.Entry<Integer, List<Car>> listOfCars : timeToListOfCars.entrySet())
        {
            for(Car car : listOfCars.getValue())
            {
                profit += car.washingTime * profitPerMinute;
            }
        }
        return profit;
    }
}
