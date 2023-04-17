package ru.omsu.fctk;

import org.junit.Before;
import org.junit.Test;
import ru.omsu.fctk.CarWashService.CarWashConditionService;
import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.car.Jeep;
import ru.omsu.fctk.data.car.Minibus;
import ru.omsu.fctk.data.car.PassengerCar;

import java.util.*;

import static org.junit.Assert.*;

public class CarWashTest {

    ICarWash testCarWash = new CarWash(2, new CarWashConditionService());
    @Before
    public void initialize()
    {
        testCarWash.addCarToCarWash(new PassengerCar(), 0);
        testCarWash.addCarToCarWash(new Minibus(), 0);
        testCarWash.addCarToCarWash(new Jeep(), 1);
        testCarWash.addCarToCarWash(new PassengerCar(), 5);
        testCarWash.addCarToCarWash(new Minibus(), 8);
        testCarWash.addCarToCarWash(new Jeep(), 12);
        testCarWash.addCarToCarWash(new PassengerCar(), 14);
        testCarWash.addCarToCarWash(new Minibus(), 18);
        testCarWash.addCarToCarWash(new Jeep(), 30);
    }
    @Test
    public void addCarToCarWash() {
        testCarWash.addCarToCarWash(new Jeep(), 30);
        boolean flag = false;
        try
        {
            testCarWash.addCarToCarWash(new Jeep(), 12);
        }
        catch(IllegalArgumentException err)
        {
            flag = true;
        }
        assertTrue(flag);

        CarWashCondition carWashCondition = testCarWash.getNextCarWashCondition();
        while(carWashCondition != null)
        {
            carWashCondition = testCarWash.getNextCarWashCondition();
        }

        flag = false;
        try
        {
            testCarWash.addCarToCarWash(new Jeep(), 30);
        }
        catch(IllegalArgumentException err)
        {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void getProfitBeforeSimulation() {
        testCarWash.getNextCarWashCondition();
        testCarWash.getNextCarWashCondition();
        boolean flag = false;
        try
        {
            testCarWash.getProfit();
        }
        catch(IllegalStateException err)
        {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void getNextCarWashCondition() {
        Queue<String> testQueue = new LinkedList<>();
        Collections.addAll(testQueue, "CarWashCondition{carsInQueue=[], carsInPosts={0=ImmutablePair(first=0, second=Car{carModel='passenger car', washingTime=5}), 1=ImmutablePair(first=0, second=Car{carModel='minibus', washingTime=10})}, currentTime=0, countOfPosts=2, currentProfit=0}",
                "CarWashCondition{carsInQueue=[ImmutablePair(first=1, second=Car{carModel='jeep', washingTime=8})], carsInPosts={0=ImmutablePair(first=0, second=Car{carModel='passenger car', washingTime=5}), 1=ImmutablePair(first=0, second=Car{carModel='minibus', washingTime=10})}, currentTime=1, countOfPosts=2, currentProfit=0}",
                "CarWashCondition{carsInQueue=[ImmutablePair(first=5, second=Car{carModel='passenger car', washingTime=5})], carsInPosts={0=ImmutablePair(first=5, second=Car{carModel='jeep', washingTime=8}), 1=ImmutablePair(first=0, second=Car{carModel='minibus', washingTime=10})}, currentTime=5, countOfPosts=2, currentProfit=15000}",
                "CarWashCondition{carsInQueue=[ImmutablePair(first=5, second=Car{carModel='passenger car', washingTime=5}), ImmutablePair(first=8, second=Car{carModel='minibus', washingTime=10})], carsInPosts={0=ImmutablePair(first=5, second=Car{carModel='jeep', washingTime=8}), 1=ImmutablePair(first=0, second=Car{carModel='minibus', washingTime=10})}, currentTime=8, countOfPosts=2, currentProfit=15000}",
                "CarWashCondition{carsInQueue=[ImmutablePair(first=8, second=Car{carModel='minibus', washingTime=10})], carsInPosts={0=ImmutablePair(first=5, second=Car{carModel='jeep', washingTime=8}), 1=ImmutablePair(first=10, second=Car{carModel='passenger car', washingTime=5})}, currentTime=10, countOfPosts=2, currentProfit=45000}",
                "CarWashCondition{carsInQueue=[ImmutablePair(first=8, second=Car{carModel='minibus', washingTime=10}), ImmutablePair(first=12, second=Car{carModel='jeep', washingTime=8})], carsInPosts={0=ImmutablePair(first=5, second=Car{carModel='jeep', washingTime=8}), 1=ImmutablePair(first=10, second=Car{carModel='passenger car', washingTime=5})}, currentTime=12, countOfPosts=2, currentProfit=45000}",
                "CarWashCondition{carsInQueue=[ImmutablePair(first=12, second=Car{carModel='jeep', washingTime=8})], carsInPosts={0=ImmutablePair(first=13, second=Car{carModel='minibus', washingTime=10}), 1=ImmutablePair(first=10, second=Car{carModel='passenger car', washingTime=5})}, currentTime=13, countOfPosts=2, currentProfit=69000}",
                "CarWashCondition{carsInQueue=[ImmutablePair(first=12, second=Car{carModel='jeep', washingTime=8}), ImmutablePair(first=14, second=Car{carModel='passenger car', washingTime=5})], carsInPosts={0=ImmutablePair(first=13, second=Car{carModel='minibus', washingTime=10}), 1=ImmutablePair(first=10, second=Car{carModel='passenger car', washingTime=5})}, currentTime=14, countOfPosts=2, currentProfit=69000}",
                "CarWashCondition{carsInQueue=[ImmutablePair(first=14, second=Car{carModel='passenger car', washingTime=5})], carsInPosts={0=ImmutablePair(first=13, second=Car{carModel='minibus', washingTime=10}), 1=ImmutablePair(first=15, second=Car{carModel='jeep', washingTime=8})}, currentTime=15, countOfPosts=2, currentProfit=84000}",
                "CarWashCondition{carsInQueue=[ImmutablePair(first=14, second=Car{carModel='passenger car', washingTime=5}), ImmutablePair(first=18, second=Car{carModel='minibus', washingTime=10})], carsInPosts={0=ImmutablePair(first=13, second=Car{carModel='minibus', washingTime=10}), 1=ImmutablePair(first=15, second=Car{carModel='jeep', washingTime=8})}, currentTime=18, countOfPosts=2, currentProfit=84000}",
                "CarWashCondition{carsInQueue=[], carsInPosts={0=ImmutablePair(first=23, second=Car{carModel='passenger car', washingTime=5}), 1=ImmutablePair(first=23, second=Car{carModel='minibus', washingTime=10})}, currentTime=23, countOfPosts=2, currentProfit=138000}",
                "CarWashCondition{carsInQueue=[], carsInPosts={1=ImmutablePair(first=23, second=Car{carModel='minibus', washingTime=10})}, currentTime=28, countOfPosts=2, currentProfit=153000}",
                "CarWashCondition{carsInQueue=[], carsInPosts={0=ImmutablePair(first=30, second=Car{carModel='jeep', washingTime=8}), 1=ImmutablePair(first=23, second=Car{carModel='minibus', washingTime=10})}, currentTime=30, countOfPosts=2, currentProfit=153000}",
                "CarWashCondition{carsInQueue=[], carsInPosts={0=ImmutablePair(first=30, second=Car{carModel='jeep', washingTime=8})}, currentTime=33, countOfPosts=2, currentProfit=183000}",
                "CarWashCondition{carsInQueue=[], carsInPosts={}, currentTime=38, countOfPosts=2, currentProfit=207000}",
                "CarWashCondition{carsInQueue=[], carsInPosts={0=ImmutablePair(first=39, second=Car{carModel='jeep', washingTime=8})}, currentTime=39, countOfPosts=2, currentProfit=207000}",
                "CarWashCondition{carsInQueue=[], carsInPosts={}, currentTime=47, countOfPosts=2, currentProfit=231000}");



        CarWashCondition carWashCondition = testCarWash.getNextCarWashCondition();
        while(carWashCondition != null)
        {
            assertEquals(testQueue.remove(), carWashCondition.toString());
            carWashCondition = testCarWash.getNextCarWashCondition();
        }
        testCarWash.addCarToCarWash(new Jeep(), 39);
        carWashCondition = testCarWash.getNextCarWashCondition();
        while(carWashCondition != null)
        {
            assertEquals(testQueue.remove(), carWashCondition.toString());
            carWashCondition = testCarWash.getNextCarWashCondition();
        }
        testCarWash = new CarWash(2, new CarWashConditionService());
        assertNull(testCarWash.getNextCarWashCondition());
    }
    @Test
    public void getProfitAfterSimulation()
    {
        CarWashCondition carWashCondition = testCarWash.getNextCarWashCondition();
        while(carWashCondition != null)
        {
            carWashCondition = testCarWash.getNextCarWashCondition();
        }
        testCarWash.addCarToCarWash(new Jeep(), 39);
        carWashCondition = testCarWash.getNextCarWashCondition();
        while(carWashCondition != null)
        {
            carWashCondition = testCarWash.getNextCarWashCondition();
        }
        assertEquals(231000, testCarWash.getProfit());
    }
    @Test
    public void reset() {
        ((CarWash) testCarWash).reset();
        assertEquals(new CarWash(2, new CarWashConditionService()), testCarWash);
    }
}