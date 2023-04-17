package ru.omsu.fctk;

import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;
import ru.omsu.fctk.CarWashService.ICarWashConditionService;
import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.IProfitPerMinute;
import ru.omsu.fctk.data.ProfitPerMinute;
import ru.omsu.fctk.data.car.Car;

import java.util.*;

public class CarWash implements ICarWash {

    private Deque<Pair<Integer, Car>> arrivalQueueOfCars;
    private int countOfPosts;
    //правила образования прибыли
    private IProfitPerMinute profitPerMinute;
    private ICarWashConditionService carWashConditionService;
    private CarWashCondition correctCarWashCondition = null;
    private boolean wasThereASimulation = false;

    private static final IProfitPerMinute STANDARD_PROFIT_PER_MINUTE = new ProfitPerMinute(3000);
    public CarWash(int countOfPosts, IProfitPerMinute profitPerMinute, ICarWashConditionService carWashConditionService) {
        if (countOfPosts <= 0 || carWashConditionService == null)
            throw new IllegalArgumentException();
        arrivalQueueOfCars = new LinkedList<>();
        this.countOfPosts = countOfPosts;
        this.profitPerMinute = profitPerMinute;
        this.carWashConditionService = carWashConditionService;
        carWashConditionService.start(arrivalQueueOfCars, this.profitPerMinute, this.countOfPosts);
    }
    public CarWash(int countOfPosts, ICarWashConditionService carWashConditionService)
    {
        this(countOfPosts, STANDARD_PROFIT_PER_MINUTE, carWashConditionService);
    }

    @Override
    public void addCarToCarWash(Car car, int time) {
        if (time < 0 || (correctCarWashCondition != null
                && time <= correctCarWashCondition.currentTime) ||
                (arrivalQueueOfCars.size() != 0 && time < arrivalQueueOfCars.peekLast().getFirst()))
            throw new IllegalArgumentException();
        arrivalQueueOfCars.addLast(Pairs.from(time, car));
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
        CarWashCondition buffer = carWashConditionService.getNextCarWashCondition();
        if (buffer != null) {
            correctCarWashCondition = buffer;
            return correctCarWashCondition;
        }
        wasThereASimulation = true;
        return null;
    }

    public void reset() {
        arrivalQueueOfCars = new LinkedList<>();
        correctCarWashCondition = null;
        wasThereASimulation = false;
        carWashConditionService.start(arrivalQueueOfCars, profitPerMinute, countOfPosts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarWash carWash = (CarWash) o;
        return countOfPosts == carWash.countOfPosts && wasThereASimulation == carWash.wasThereASimulation
                && Objects.equals(arrivalQueueOfCars, carWash.arrivalQueueOfCars)
                && Objects.equals(profitPerMinute, carWash.profitPerMinute)
                && Objects.equals(carWashConditionService, carWash.carWashConditionService)
                && Objects.equals(correctCarWashCondition, carWash.correctCarWashCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrivalQueueOfCars, countOfPosts, profitPerMinute, carWashConditionService,
                correctCarWashCondition, wasThereASimulation);
    }
}
