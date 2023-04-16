package ru.omsu.fctk.CarWashService;

import de.scravy.pair.Pair;
import ru.omsu.fctk.data.car.Car;

import java.util.List;
import java.util.Map;

public class CarWashProfit implements ICarWashProfit{

    @Override
    public long getProfitOfCarWash(List<Pair<Integer, Car>> arrivalListOfCars, int countOfPosts, long profitPerMinute) {
        long profit = 0;
        for(Pair<Integer, Car> pair : arrivalListOfCars)
        {
            profit += pair.getSecond().washingTime * profitPerMinute;
        }
        return profit;
    }
}
