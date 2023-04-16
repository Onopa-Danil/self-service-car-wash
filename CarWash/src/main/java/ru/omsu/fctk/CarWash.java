package ru.omsu.fctk;

import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;
import ru.omsu.fctk.CarWashService.ICarWashConditions;
import ru.omsu.fctk.CarWashService.ICarWashProfit;
import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.car.Car;

import java.util.*;

public class CarWash implements ICarWash{

    private List<Pair<Integer, Car>> arrivalListOfCars;
    private int countOfPosts;
    private long profitPerMinute; //стоимость мойки за одну минуту в копейках
    private ICarWashConditions carWashConditions;
    private ICarWashProfit carWashProfit;
    private int timeOfAddingLastCar = 0;
    private List<CarWashCondition> listOfCarWashConditions = null;
    private long profit = 0;
    private boolean wasThereASimulation = false;

    public CarWash(int countOfPosts, long profitPerMinute, ICarWashConditions carWashConditions, ICarWashProfit carWashProfit) {
        if (countOfPosts <= 0 || profitPerMinute < 0 || carWashConditions == null || carWashProfit == null) throw new IllegalArgumentException();
        arrivalListOfCars = new LinkedList<>();
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
        arrivalListOfCars.add(Pairs.from(time, car));
        timeOfAddingLastCar = time;
        wasThereASimulation = false;
    }

    @Override
    public long getProfit() {
        if (wasThereASimulation) return profit;
        throw new IllegalStateException("The simulation has not been started yet");
    }

    @Override
    public List<CarWashCondition> getListOfCarWashConditions() {
        if (wasThereASimulation) return listOfCarWashConditions;
        listOfCarWashConditions = carWashConditions.getListOfCarWashConditions(arrivalListOfCars, countOfPosts);
        profit = carWashProfit.getProfitOfCarWash(arrivalListOfCars, countOfPosts, profitPerMinute);
        wasThereASimulation = true;
        return listOfCarWashConditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarWash carWash = (CarWash) o;
        return countOfPosts == carWash.countOfPosts && profitPerMinute == carWash.profitPerMinute && timeOfAddingLastCar == carWash.timeOfAddingLastCar && profit == carWash.profit && wasThereASimulation == carWash.wasThereASimulation && Objects.equals(arrivalListOfCars, carWash.arrivalListOfCars) && Objects.equals(carWashConditions, carWash.carWashConditions) && Objects.equals(carWashProfit, carWash.carWashProfit) && Objects.equals(listOfCarWashConditions, carWash.listOfCarWashConditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrivalListOfCars, countOfPosts, profitPerMinute, carWashConditions, carWashProfit, timeOfAddingLastCar, listOfCarWashConditions, profit, wasThereASimulation);
    }
}
