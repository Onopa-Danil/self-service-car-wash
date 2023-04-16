package ru.omsu.fctk.CarWashService;

import de.scravy.pair.Pair;
import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.car.Car;

import java.util.List;

public interface ICarWashConditionService {
    CarWashCondition getNextCarWashCondition(List<Pair<Integer, Car>> arrivalListOfCars,
                                             CarWashCondition previousCarWashCondition);
}
