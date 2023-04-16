package ru.omsu.fctk.CarWashService;

import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;
import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.car.Car;

import java.util.*;


public class CarWashConditions implements ICarWashConditions{
    @Override
    public List<CarWashCondition> getListOfCarWashConditions(List<Pair<Integer, Car>> arrivalListOfCars, int countOfPosts) {
        Queue<Pair<Integer, Car>> arrivalQueueOfCars = new LinkedList<>(arrivalListOfCars);
        List<CarWashCondition> listOfCarWashConditions = new LinkedList<>();
        Map<Integer, Pair<Integer, Car>> carInPost = new HashMap<>();
        Queue<Pair<Integer, Car>> queueOfCars = new LinkedList<>();
        int arrivalTimeOfTheLastCar = arrivalListOfCars.get(arrivalListOfCars.size() - 1).getFirst();
        boolean changesInTheQueue = true;
        for(int currentTime = 0; currentTime <= arrivalTimeOfTheLastCar; currentTime++)
        {
            while(arrivalQueueOfCars.peek() != null && arrivalQueueOfCars.peek().getFirst() == currentTime) //прибытие машин на автомойку
            {
                queueOfCars.add(arrivalQueueOfCars.remove());
                changesInTheQueue = true;
            }
            for(int i = 0; i < countOfPosts; i++) //машины уезжают из постов автомойки и на их место встают другие машины из очереди
            {
                Pair<Integer, Car> currentCar = carInPost.get(i);
                if(currentCar != null && currentTime - currentCar.getFirst() == currentCar.getSecond().washingTime) {
                    Pair<Integer, Car> buffer = queueOfCars.poll();
                    if (buffer != null) carInPost.put(i, Pairs.from(currentTime, buffer.getSecond()));
                    else carInPost.put(i, null);
                    changesInTheQueue = true;
                }
                else if (currentCar == null)
                {
                    Pair<Integer, Car> buffer = queueOfCars.poll();
                    if (buffer != null) {
                        carInPost.put(i, Pairs.from(currentTime, buffer.getSecond()));
                        changesInTheQueue = true;
                    }
                }
            }
            if (changesInTheQueue) listOfCarWashConditions.add(new CarWashCondition(new ArrayList<>(queueOfCars), new HashMap<>(carInPost), currentTime, countOfPosts));
            changesInTheQueue = false;
        }
        return listOfCarWashConditions;
    }
}
