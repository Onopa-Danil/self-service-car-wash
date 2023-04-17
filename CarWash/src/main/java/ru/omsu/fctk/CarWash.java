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
    //список всех машин, который были на автомойке за рабочий день
    private Deque<Pair<Integer, Car>> arrivalQueueOfCars;
    //количество постов автомойки
    private final int countOfPosts;
    //правила образования прибыли
    private final IProfitPerMinute profitPerMinute;
    //Класс, который содержит метод образования состояний автомойки
    private final ICarWashConditionService carWashConditionService;
    //последнее состояние автомойки, полученное методом getNextCarWashCondition интерфейса ICarWashService
    private CarWashCondition correctCarWashCondition = null;
    //завершено ли формирование состояний автомойки
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
    /*метод добавления машин в список всех машин за рабочий день.
    Возможно добавление машин во время моделирования поведения автомойки и после моделирования.
    В последнем случае моделирование возобновляется.
    IllegalArgumentException если данные не верны или не сочетаются с прошлыми состояниями автомойки*/
    @Override
    public void addCarToCarWash(Car car, int time) {
        if (car == null || time < 0 || (correctCarWashCondition != null
                && time <= correctCarWashCondition.currentTime) ||
                (arrivalQueueOfCars.size() != 0 && time < arrivalQueueOfCars.peekLast().getFirst()))
            throw new IllegalArgumentException();
        arrivalQueueOfCars.addLast(Pairs.from(time, car));
        wasThereASimulation = false;
    }
    //Получение прибыли за рабочий день. IllegalStateException если моделирование не завершено
    @Override
    public long getProfit() {
        if (wasThereASimulation) return correctCarWashCondition.currentProfit;
        throw new IllegalStateException("The simulation has not started or is not finished yet");
    }
    /*Метод получаения состояний.
    Возвращает null если моделирование поведения автомойки завершилось или машин в списке arrivalQueueOfCars нету*/
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
    //делает "пустой" автомойку. arrivalQueueOfCars - список машин за рабочий день нужно формировать заново.
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
