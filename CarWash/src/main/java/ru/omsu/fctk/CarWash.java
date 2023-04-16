package ru.omsu.fctk;

import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;
import ru.omsu.fctk.CarWashService.ICarWashConditionService;
import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.car.Car;

import java.util.*;

public class CarWash implements ICarWash {

    private Queue<Pair<Integer, Car>> arrivalQueueOfCars;
    private int countOfPosts;
    //стоимость мойки за одну минуту в копейках
    private long profitPerMinute;
    private ICarWashConditionService carWashConditionService;
    private CarWashCondition correctCarWashCondition = null;
    private boolean wasThereASimulation = false;
    private static final int STANDARD_PROFIT_PER_MINUTE = 3000;

    public CarWash(int countOfPosts, long profitPerMinute, ICarWashConditionService carWashConditionService) {
        if (countOfPosts <= 0 || profitPerMinute < 0 || carWashConditionService == null)
            throw new IllegalArgumentException();
        arrivalListOfCars = new LinkedList<>();
        this.countOfPosts = countOfPosts;
        this.profitPerMinute = profitPerMinute;
        this.carWashConditionService = carWashConditionService;
    }

    public CarWash(int countOfPosts, ICarWashConditionService carWashConditionService) {
        this(countOfPosts, STANDARD_PROFIT_PER_MINUTE, carWashConditionService);
    }

    @Override
    public void addCarToCarWash(Car car, int time) {
        if (time < 0 || (correctCarWashCondition != null
                && time < correctCarWashCondition.currentTime))
            throw new IllegalArgumentException();
        arrivalListOfCars.add(Pairs.from(time, car));
        wasThereASimulation = false;
    }

    @Override
    public long getProfit() {
        if (wasThereASimulation) return correctCarWashCondition.currentProfit;
        throw new IllegalStateException("The simulation has not started or is not finished yet");
    }

    @Override
    public CarWashCondition getNextCarWashCondition() {
        if (wasThereASimulation) return null;
        CarWashCondition buffer = carWashConditionService.getNextCarWashCondition
                (arrivalListOfCars, correctCarWashCondition);
        if (buffer != null) {
            correctCarWashCondition = buffer;
            return correctCarWashCondition;
        }
        wasThereASimulation = true;
        return null;
    }

    public void reset() {
        arrivalListOfCars = new LinkedList<>();
        correctCarWashCondition = null;
        wasThereASimulation = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarWash carWash = (CarWash) o;
        return countOfPosts == carWash.countOfPosts && profitPerMinute == carWash.profitPerMinute
                && wasThereASimulation == carWash.wasThereASimulation
                && Objects.equals(arrivalListOfCars, carWash.arrivalListOfCars)
                && Objects.equals(carWashConditionService, carWash.carWashConditionService)
                && Objects.equals(correctCarWashCondition, carWash.correctCarWashCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrivalListOfCars, countOfPosts, profitPerMinute, carWashConditionService,
                correctCarWashCondition, wasThereASimulation);
    }
}
