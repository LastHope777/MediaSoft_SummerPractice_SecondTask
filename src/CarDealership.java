import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class CarDealership {
    List<Car> cars = new ArrayList<>();

    public void addCar(Car car) {
        if (cars.stream().noneMatch(c -> c.vin.equals(car.vin))) {
            cars.add(car);
        }
    }

    public List<Car> findByManufacturer(String manufacturer) {
        return cars.stream().filter(c -> c.manufacturer.equalsIgnoreCase(manufacturer)).collect(Collectors.toList());
    }

    public double averagePriceByType(CarType type) {
        return cars.stream().filter(c -> c.type == type).mapToDouble(c -> c.price).average().orElse(0);
    }

    public List<Car> getSortedByYear() {
        return cars.stream().sorted().collect(Collectors.toList());
    }

    public Map<CarType, Long> getTypeStats() {
        return cars.stream().collect(Collectors.groupingBy(c -> c.type, Collectors.counting()));
    }

    public Car getOldestCar() {
        return cars.stream().min(Comparator.comparingInt(c -> c.year)).orElse(null);
    }

    public Car getNewestCar() {
        return cars.stream().max(Comparator.comparingInt(c -> c.year)).orElse(null);
    }
}
