package impl;

import interfaces.Garage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static io.qameta.allure.SeverityLevel.*;

public class GarageImplTests {
    static ArrayList<Owner> owners = new ArrayList<>();

    static ArrayList<Car> cars = new ArrayList<>();

    @BeforeEach
    void createCars() {
        cars = new ArrayList<>();

        cars.add(new Car(1, "a", "a_1", 10, 1, 1));
        cars.add(new Car(2, "b", "b_1", 10, 1, 1));
        cars.add(new Car(3, "c", "c_1", 10, 1, 2));
        cars.add(new Car(4, "a", "a_2", 11, 2, 2));
        cars.add(new Car(5, "b", "b_2", 11, 2, 3));
        cars.add(new Car(6, "c", "c_2", 11, 2, 3));
        cars.add(new Car(7, "a", "a_3", 11, 2, 4));
        cars.add(new Car(8, "b", "b_3", 12, 3, 4));
        cars.add(new Car(9, "c", "c_3", 12, 3, 5));
        cars.add(new Car(10, "a", "a_4", 12, 3, 5));
    }

    @BeforeEach
    void createOwners() {
        owners = new ArrayList<>();

        owners.add(new Owner("aa", "aa", 15));
        owners.add(new Owner("bb", "bb", 20));
        owners.add(new Owner("cc", "cc", 25));
        owners.add(new Owner("dd", "dd", 30));
        owners.add(new Owner("ee", "ff", 35));
        owners.add(new Owner("ff", "gg", 40));
    }

    ArrayList<Owner> expectedOwners(int[] expectedOwnerIds) {
        ArrayList<Owner> res = new ArrayList<>();
        for (int ownerId: expectedOwnerIds) {
            res.add(owners.get(ownerId - 1));
        }
        res.sort(Comparator.comparing(Owner::getAge));
        return res;
    }

    ArrayList<Car> expectedCars(int[] expectedCarIds) {
        ArrayList<Car> res = new ArrayList<>();
        for (int carId : expectedCarIds) {
            res.add(cars.get(carId - 1));
        }
        res.sort(Comparator.comparing(Car::getCarId));
        return res;
    }

    @Test
    @SuppressWarnings("unused")
    void allCarsUniqueOwners() {
        final Garage garage;
        Collection<Owner> whenOwners;
        GIVEN: {
            garage = new GarageImpl();

            for (Car car : cars) {
                garage.addNewCar(car, owners.get(car.getOwnerId() - 1));
            }
        }

        WHEN: {
            whenOwners = garage.allCarsUniqueOwners();
        }

        THEN: {
            assertArrayEquals(
                    expectedOwners(new int[]{1, 2, 3, 4, 5}).toArray(),
                    whenOwners.stream().sorted(Comparator.comparing(Owner::getAge)).toArray()
            );
        }
    }

    @Test
    void allCarsOfBrand() {
        GarageImpl garage = new GarageImpl();
        for (Car car : cars) {
            garage.addNewCar(car, owners.get(car.getOwnerId() - 1));
        }

        assertArrayEquals(
                expectedCars(new int[]{1, 4, 7, 10}).toArray(),
                garage.allCarsOfBrand("a").stream().sorted(Comparator.comparing(Car::getCarId)).toArray()
        );
    }

    @Test
    void carsWithPowerMoreThan() {
        GarageImpl garage = new GarageImpl();
        for (Car car : cars) {
            garage.addNewCar(car, owners.get(car.getOwnerId() - 1));
        }


        assertArrayEquals(
                expectedCars(new int[]{8, 9, 10}).toArray(),
                garage.carsWithPowerMoreThan(2).toArray()
        );
    }

    @Test
    void allCarsOfOwner() {
        GarageImpl garage = new GarageImpl();
        for (Car car : cars) {
            garage.addNewCar(car, owners.get(car.getOwnerId() - 1));
        }

        assertArrayEquals(
                expectedCars(new int[]{1, 2}).toArray(),
                garage.allCarsOfOwner(owners.get(0)).stream().sorted(Comparator.comparing(Car::getCarId)).toArray()
        );
    }

    @Test
    void meanOwnersAgeOfCarBrand() {
        GarageImpl garage = new GarageImpl();
        assertEquals(
                0,
                garage.meanOwnersAgeOfCarBrand("a")
        );

        for (Car car : cars) {
            garage.addNewCar(car, owners.get(car.getOwnerId() - 1));
        }

        assertEquals(
                (/*OwnerId=1*/15 + /*OwnerId=2*/20 + /*OwnerId=4*/30 + /*OwnerId=5*/35) / 4,
                garage.meanOwnersAgeOfCarBrand("a")
        );
    }

    @Test
    void meanCarNumberForEachOwner() {
        GarageImpl garage = new GarageImpl();
        assertEquals(
                0,
                garage.meanCarNumberForEachOwner()
        );

        for (Car car : cars) {
            garage.addNewCar(car, owners.get(car.getOwnerId() - 1));
        }

        assertEquals(
                (2 + 2 + 2 + 2 + 2) / 5,
                garage.meanCarNumberForEachOwner()
        );
    }

    @Test
    void removeCar() {
        GarageImpl garage = new GarageImpl();
        for (Car car : cars) {
            garage.addNewCar(car, owners.get(car.getOwnerId() - 1));
        }

        assertEquals(cars.get(9), garage.removeCar(10));
        assertEquals(cars.get(7), garage.removeCar(8));
        assertEquals(cars.get(8), garage.removeCar(9));
        assertEquals(cars.get(6), garage.removeCar(7));
        assertEquals(cars.get(5), garage.removeCar(6));
        assertEquals(cars.get(4), garage.removeCar(5));
        assertEquals(cars.get(3), garage.removeCar(4));
        assertEquals(cars.get(2), garage.removeCar(3));
        assertEquals(cars.get(1), garage.removeCar(2));
        assertEquals(cars.get(0), garage.removeCar(1));
        assertNull(garage.removeCar(1));
    }

    @Test
    void addNewCar() {
        GarageImpl garage = new GarageImpl();
        for (Car car : cars) {
            garage.addNewCar(car, owners.get(car.getOwnerId() - 1));
        }
    }
}
