package ru.omsu.fctk;

import ru.omsu.fctk.CarWashService.ICarWashConditions;
import ru.omsu.fctk.CarWashService.ICarWashProfit;
import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.car.Car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarWash implements ICarWash{

    private Map<Integer, List<Car>> timeToListOfCars;
    private int countOfPosts;
    private long profitPerMinute; //стоимость мойки за одну минуту в копейках
    private ICarWashConditions carWashConditions;
    private ICarWashProfit carWashProfit;
    private int timeOfAddingLastCar = 0;

    public CarWash(int countOfPosts, long profitPerMinute, ICarWashConditions carWashConditions, ICarWashProfit carWashProfit) {
        if (countOfPosts <= 0 || profitPerMinute < 0 || carWashConditions == null || carWashProfit == null) throw new IllegalArgumentException();
        this.timeToListOfCars = new HashMap<>();
        this.countOfPosts = countOfPosts;
        this.profitPerMinute = profitPerMinute;
        this.carWashConditions = carWashConditions;
        this.carWashProfit = carWashProfit;
    }
    public CarWash(int countOfPosts, ICarWashConditions carWashConditions, ICarWashProfit carWashProfit) {
        this(countOfPosts, 3000, carWashConditions, carWashProfit);
    }
    @Override
    public void addCarToCarWash(Car car, int time) {
        if (time < 0 && time < timeOfAddingLastCar) throw new IllegalArgumentException();
        List<Car> list = timeToListOfCars.getOrDefault(time, new ArrayList<>());
        timeToListOfCars.put(time, list);
        list.add(car);
        timeOfAddingLastCar = time;
    }

    @Override
    public long getProfit() {
        return carWashProfit.getProfitOfCarWash(timeToListOfCars, countOfPosts, profitPerMinute);
    }

    @Override
    public List<CarWashCondition> getListOfCarWashConditions() {
        return carWashConditions.getListOfCarWashConditions(timeToListOfCars, countOfPosts);
    }
}
