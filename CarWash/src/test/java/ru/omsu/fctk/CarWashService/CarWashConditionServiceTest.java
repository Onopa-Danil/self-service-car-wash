package ru.omsu.fctk.CarWashService;

import org.junit.Test;
import ru.omsu.fctk.CarWash;
import ru.omsu.fctk.ICarWash;
import ru.omsu.fctk.data.car.Jeep;

import static org.junit.Assert.*;

public class CarWashConditionServiceTest {

    @Test
    public void getNextCarWashCondition() {
        ICarWashConditionService carWashConditionService = new CarWashConditionService();
        new CarWash(2, carWashConditionService);
        assertNull(carWashConditionService.getNextCarWashCondition());
    }
}