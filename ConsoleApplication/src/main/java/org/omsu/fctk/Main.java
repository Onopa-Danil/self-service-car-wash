package org.omsu.fctk;

import ru.omsu.fctk.CarWash;
import ru.omsu.fctk.CarWashService.CarWashConditionService;
import ru.omsu.fctk.ICarWash;
import ru.omsu.fctk.data.CarWashCondition;
import ru.omsu.fctk.data.car.Car;
import ru.omsu.fctk.data.car.Jeep;
import ru.omsu.fctk.data.car.Minibus;
import ru.omsu.fctk.data.car.PassengerCar;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите количество машин, которые были на автомойке за рабочий день ");
            int countOfCars = scanner.nextInt();
            System.out.print("Введите количество постов на автомойке ");
            int countOfPosts = scanner.nextInt();
            if (countOfCars <= 0 || countOfPosts <= 0) {
                System.out.printf("Неверные параметры");
                return;
            }
            ICarWash carWash = getListOfCars(countOfCars, countOfPosts);
            if (carWash == null) {
                System.out.printf("Неверные параметры");
                return;
            }
            List<CarWashCondition> listOfCarWashConditions = getCarWashConditions(carWash);
            System.out.printf("Текущая прибыль %d рублей. Вывести ли список всех состояний автомойки? 1 - Да, 0 - нет\n",
                    listOfCarWashConditions.get(listOfCarWashConditions.size() - 1).currentProfit / 100);
            int flag = scanner.nextInt();
            if (flag == 1) {
                printCarWashConditions(listOfCarWashConditions);
                while (true) {
                    System.out.printf("Добавить ли еще машину? 1-Да, 0 - нет ");
                    flag = scanner.nextInt();
                    if (flag == 1) {
                        System.out.println("Выберите машину:\n1.Легковой автомобиль\n2.Микроавтобус\n3.Джип");
                        int numberOfCar = scanner.nextInt();
                        System.out.print("Введите время ее прибытия на автомойку." +
                                " Это число должно быть больше времени в последнем состоянии ");
                        int time = scanner.nextInt();
                        Car car = null;
                        if (numberOfCar == 1) car = new PassengerCar();
                        else if (numberOfCar == 2) car = new Minibus();
                        else if (numberOfCar == 3) car = new Jeep();
                        try {
                            carWash.addCarToCarWash(car, time);
                        } catch (IllegalArgumentException err) {
                            System.out.printf("Неверные параметры");
                            return;
                        }
                        listOfCarWashConditions = getCarWashConditions(carWash);
                        System.out.printf("Текущая прибыль %d рублей." +
                                        " Вывести ли список новых состояний автомойки? 1 - Да, 0 - нет\n",
                                listOfCarWashConditions.get(listOfCarWashConditions.size() - 1).currentProfit / 100);
                        flag = scanner.nextInt();
                        if (flag == 1) printCarWashConditions(listOfCarWashConditions);
                    } else break;

                }
            }
            System.out.print("Начать сначала? 1 - да, 0 - нет ");
            flag = scanner.nextInt();
            if (flag != 1) break;
        }
    }

    public static ICarWash getListOfCars(int countOfCars, int countOfPosts) {
        ICarWash carWash = new CarWash(countOfPosts, new CarWashConditionService());
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < countOfCars; i++) {
            System.out.println("Выберите машину:\n1.Легковой автомобиль\n2.Микроавтобус\n3.Джип");
            int numberOfCar = scanner.nextInt();
            System.out.print("Введите время ее прибытия на автомойку ");
            int time = scanner.nextInt();
            Car car = null;
            if (numberOfCar == 1) car = new PassengerCar();
            else if (numberOfCar == 2) car = new Minibus();
            else if (numberOfCar == 3) car = new Jeep();
            try {
                carWash.addCarToCarWash(car, time);
            } catch (IllegalArgumentException err) {
                return null;
            }
        }
        return carWash;
    }

    public static List<CarWashCondition> getCarWashConditions(ICarWash carWash) {
        List<CarWashCondition> list = new LinkedList<>();
        CarWashCondition carWashCondition = carWash.getNextCarWashCondition();
        while (carWashCondition != null) {
            list.add(carWashCondition);
            carWashCondition = carWash.getNextCarWashCondition();
        }
        return list;
    }

    public static void printCarWashConditions(List<CarWashCondition> carWashConditions) {
        System.out.println("В очередях возле машин написано время прибытия на автомойку." +
                "\nВ постах возле машин написано время прибытия в пост и номер поста");
        int numberOfIteration = 1;
        for (CarWashCondition carWashCondition : carWashConditions) {
            System.out.printf("\nСостояние %d. Текущий момент времени %d." +
                            " Текущая прибыль %d\nСписок машин в очереди:\n",
                    numberOfIteration, carWashCondition.currentTime, carWashCondition.currentProfit / 100);
            for (int i = carWashCondition.carsInQueue.size() - 1; i >= 0; i--) {
                System.out.printf("%s %d\n", carWashCondition.carsInQueue.get(i).getSecond().carModel,
                        carWashCondition.carsInQueue.get(i).getFirst());
            }
            System.out.print("Список машин в постах:\n");
            for (int i = 0; i < carWashCondition.countOfPosts; i++) {
                if (carWashCondition.carsInPosts.get(i) != null)
                    System.out.printf("%s %d %d\n", carWashCondition.carsInPosts.get(i).getSecond().carModel,
                            carWashCondition.carsInPosts.get(i).getFirst(), i + 1);
            }
            numberOfIteration++;
        }
    }
}
