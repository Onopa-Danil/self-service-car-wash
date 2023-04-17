package ru.omsu.fctk.CarWashService;

import de.scravy.pair.Pair;
import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.IProfitPerMinute;
import ru.omsu.fctk.data.car.Car;

import java.util.*;

public interface ICarWashConditionService {
    //установка значений, необходимых для работы метода getNextCarWashCondition
    void start(Deque<Pair<Integer, Car>> arrivalQueueOfCars, IProfitPerMinute profitPerMinute, int countOfPosts);
    CarWashCondition getNextCarWashCondition();

}
