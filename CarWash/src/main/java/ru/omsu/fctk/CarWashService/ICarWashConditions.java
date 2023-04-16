package ru.omsu.fctk.CarWashService;

import de.scravy.pair.Pair;
import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.car.Car;

import java.util.List;
import java.util.Map;

public interface ICarWashConditions {
    List<CarWashCondition> getListOfCarWashConditions(List<Pair<Integer, Car>> arrivalListOfCars, int countOfPosts);
}
