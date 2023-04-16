package ru.omsu.fctk;

import de.scravy.pair.Pair;
import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.car.Car;

import java.util.List;

public interface ICarWash {
    void addCarToCarWash(Car car, int time);

    long getProfit();

    CarWashCondition getNextCarWashCondition();
}
