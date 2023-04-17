package ru.omsu.fctk.CarWashService;

import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;
import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.IProfitPerMinute;
import ru.omsu.fctk.data.ProfitPerMinute;
import ru.omsu.fctk.data.car.Car;

import java.util.*;


public class CarWashConditionService implements ICarWashConditionService {
    private Deque<Pair<Integer, Car>> arrivalQueueOfCars;
    private Map<Integer, Pair<Integer, Car>> carInPost;
    private Queue<Pair<Integer, Car>> queueOfCars;
    private CarWashCondition previousCarWashCondition;
    private ProfitPerMinute profitPerMinute;
    private int countOfPosts;
    @Override
    public void start(Deque<Pair<Integer, Car>> arrivalQueueOfCars,IProfitPerMinute profitPerMinute, int countOfPosts)
    {
        carInPost = new HashMap<>();
        queueOfCars = new LinkedList<>();
        previousCarWashCondition = null;
        this.arrivalQueueOfCars = arrivalQueueOfCars;
        this.profitPerMinute = (ProfitPerMinute) profitPerMinute;
        this.countOfPosts = countOfPosts;
    }

    @Override
    public CarWashCondition getNextCarWashCondition() {
        int arrivalTimeOfTheLastCar = 0;
        long profit = 0;
        if (!arrivalQueueOfCars.isEmpty()) arrivalTimeOfTheLastCar = arrivalQueueOfCars.peekLast().getFirst();
        if (previousCarWashCondition != null) profit = previousCarWashCondition.currentProfit;
        boolean changesInTheQueue = false;
        int initializingTime = 0;
        if (previousCarWashCondition != null) initializingTime = previousCarWashCondition.currentTime;
        for (int currentTime = initializingTime; currentTime <= arrivalTimeOfTheLastCar || !carInPost.isEmpty(); currentTime++) {
            //прибытие машин на автомойку
            while (!arrivalQueueOfCars.isEmpty() && arrivalQueueOfCars.peekFirst().getFirst() == currentTime) {
                queueOfCars.add(arrivalQueueOfCars.removeFirst());
                changesInTheQueue = true;
            }
            //машины уезжают из постов автомойки и на их место встают другие машины из очереди
            for (int i = 0; i < countOfPosts; i++) {
                Pair<Integer, Car> currentCar = carInPost.get(i);
                if (currentCar != null && currentTime - currentCar.getFirst() == currentCar.getSecond().washingTime) {
                    Pair<Integer, Car> buffer = queueOfCars.poll();
                    if (buffer != null)
                        carInPost.put(i, Pairs.from(currentTime, buffer.getSecond()));
                    else carInPost.remove(i);
                    profit += currentCar.getSecond().washingTime * profitPerMinute.profitPerMinute;
                    changesInTheQueue = true;
                } else if (currentCar == null) {
                    Pair<Integer, Car> buffer = queueOfCars.poll();
                    if (buffer != null) {
                        carInPost.put(i, Pairs.from(currentTime, buffer.getSecond()));
                        changesInTheQueue = true;
                    }
                }
            }
            if (changesInTheQueue) {
                previousCarWashCondition = new CarWashCondition(new ArrayList<>(queueOfCars),
                        new HashMap<>(carInPost), currentTime, countOfPosts, profit);
                return previousCarWashCondition;
            }
        }
        return null;
    }
}
