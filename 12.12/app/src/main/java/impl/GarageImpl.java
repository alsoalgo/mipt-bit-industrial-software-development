package impl;

import interfaces.Garage;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyNavigableSet;

public class GarageImpl implements Garage {
    private final Map<Owner, NavigableSet<Car>> ownerToCars;
    private final Map<String, NavigableSet<Car>> brandToCars;

    private final Map<Integer, Owner> carIdToOwner;
    private final Map<Integer, Car> carIdToCar;

    private final NavigableSet<Car> carsSortedByPower;
    private final NavigableSet<Car> carsSortedByMaxVelocity;

    public GarageImpl() {
        ownerToCars = new HashMap<>();
        brandToCars = new HashMap<>();
        carIdToOwner = new HashMap<>();
        carIdToCar = new HashMap<>();
        carsSortedByPower = new TreeSet<>(Comparator.comparing(Car::getPower).thenComparing(Car::getCarId));
        carsSortedByMaxVelocity = new TreeSet<>(Comparator.comparing(Car::getMaxVelocity).thenComparing(Car::getCarId));
    }

    @Override
    public Collection<Owner> allCarsUniqueOwners() {
        return ownerToCars.keySet();
    }

    @Override
    public Collection<Car> topThreeCarsByMaxVelocity() {
        if (carsSortedByMaxVelocity.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<Car> topThreeCars = new ArrayList<>();
        Iterator<Car> it = carsSortedByMaxVelocity.descendingIterator();
        for (int i = 0; it.hasNext() && i < 3; i++) {
            topThreeCars.add(it.next());
        }
        return topThreeCars;
    }

    @Override
    public Collection<Car> allCarsOfBrand(String brand) {
        return brandToCars.getOrDefault(brand, emptyNavigableSet());
    }

    @Override
    public Collection<Car> carsWithPowerMoreThan(int power) {
        Car fromCar = new Car(0, null, null, 0, power + 1, 0);
        return carsSortedByPower.tailSet(fromCar, false);
    }

    @Override
    public Collection<Car> allCarsOfOwner(Owner owner) {
        return ownerToCars.getOrDefault(owner, emptyNavigableSet());
    }

    @Override
    public int meanOwnersAgeOfCarBrand(String brand) {
        if (!brandToCars.containsKey(brand)) {
            return 0;
        }
        Set<Owner> owners = new HashSet<>();

        for (Car car : brandToCars.get(brand)) {
            owners.add(carIdToOwner.get(car.getCarId()));
        }

        int totalAge = 0;
        for (Owner owner : owners) {
            totalAge += owner.getAge();
        }
        return totalAge / owners.size();
    }

    @Override
    public int meanCarNumberForEachOwner() {
        if (ownerToCars.isEmpty()) {
            return 0;
        }
        return carIdToOwner.size() / ownerToCars.values().size();
    }

    @Override
    public Car removeCar(int carId) {
        if (!carIdToOwner.containsKey(carId)) {
            return null;
        }
        Car car = carIdToCar.get(carId);

        Owner owner = carIdToOwner.get(carId);
        NavigableSet<Car> carsByOwner = ownerToCars.get(owner);
        carsByOwner.remove(car);
        if (carsByOwner.isEmpty()) {
            ownerToCars.remove(owner);
        }

        String brand = car.getBrand();
        NavigableSet<Car> carsByBrand =  brandToCars.get(brand);
        carsByBrand.remove(car);
        if (carsByBrand.isEmpty()) {
            brandToCars.remove(brand);
        }

        carIdToOwner.remove(carId, owner);
        carIdToCar.remove(carId, car);

        carsSortedByPower.remove(car);

        carsSortedByMaxVelocity.remove(car);
        return car;
    }

    @Override
    public void addNewCar(Car car, Owner owner) {
        NavigableSet<Car> carsByOwner = ownerToCars.computeIfAbsent(
                owner,
                o -> new TreeSet<>(Comparator.comparing(Car::getCarId))
        );
        carsByOwner.add(car);

        NavigableSet<Car> carsByBrand = brandToCars.computeIfAbsent(
                car.getBrand(),
                b -> new TreeSet<>(Comparator.comparing(Car::getCarId))
        );
        carsByBrand.add(car);

        carIdToOwner.put(car.getCarId(), owner);
        carIdToCar.put(car.getCarId(), car);

        carsSortedByPower.add(car);

        carsSortedByMaxVelocity.add(car);
    }
}
