package ru.omsu.fctk.CarWashService;

import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.car.Car;

import java.util.List;
import java.util.Map;

public interface ICarWashConditions {
    List<CarWashCondition> getListOfCarWashConditions(Map<Integer, List<Car>> timeToListOfCars, int countOfPosts);
}
