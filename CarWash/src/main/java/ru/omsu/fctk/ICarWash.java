package ru.omsu.fctk;

import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.car.Car;

import java.util.List;

public interface ICarWash {
    void addCarToCarWash(Car car, int time);
    long getProfit();
    List<CarWashCondition> getListOfCarWashConditions();
}
