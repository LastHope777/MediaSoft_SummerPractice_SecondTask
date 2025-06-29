import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int[] years = new int[50];
        for (int i = 0; i < 50; i++) {
            years[i] = 2000 + random.nextInt(26);
        }
        System.out.println("Машины, выпущенные после 2015:");
        Arrays.stream(years).filter(y -> y > 2015).forEach(System.out::println);

        double avgAge = Arrays.stream(years).map(y -> 2025 - y).average().orElse(0);
        System.out.println("Средний возраст авто: " + avgAge);

        List<String> models = new ArrayList<>(List.of("Toyota Camry", "BMW X5", "Tesla Model S", "Toyota Camry", "Tesla Model 3", "BMW X5"));
        Set<String> sortedUniqueModels = models.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .map(m -> m.contains("Tesla") ? "ELECTRO_CAR" : m)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println("Уникальные модели:");
        sortedUniqueModels.forEach(System.out::println);

        Set<Car> carSet = new HashSet<>();
        carSet.add(new Car("1", "Camry", "Toyota", 2020, 30000, 20000, CarType.SEDAN));
        carSet.add(new Car("1", "Camry2", "Toyota2", 2021, 30001, 20001, CarType.SEDAN)); // duplicate
        carSet.add(new Car("2", "Model S", "Tesla", 2022, 10000, 50000, CarType.ELECTRIC));
        System.out.println("Количество различных машин (Без совпадения по VIN): " + carSet.size());

        List<Car> carList = new ArrayList<>(carSet);
        carList.add(new Car("3", "X5", "BMW", 2021, 40000, 55000, CarType.SUV));
        carList.add(new Car("4", "Logan", "Renault", 2019, 60000, 12000, CarType.SEDAN));

        List<Car> filteredSorted = carList.stream()
                .filter(c -> c.mileage < 50000)
                .sorted(Comparator.comparingDouble(c -> -c.price))
                .collect(Collectors.toList());
        System.out.println("Топ-3 самых дорогих машин:");
        filteredSorted.stream().limit(3).forEach(System.out::println);

        double avgMileage = carList.stream().mapToInt(c -> c.mileage).average().orElse(0);
        System.out.println("Средний пробег: " + avgMileage);

        Map<String, List<Car>> carsByManufacturer = carList.stream().collect(Collectors.groupingBy(c -> c.manufacturer));
        System.out.println("Сгруппированные машины по производителю: " + carsByManufacturer.keySet());

        CarDealership dealer = new CarDealership();
        carList.forEach(dealer::addCar);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nМеню:");
                    System.out.println("1. Добавить машину\n2. Поиск по производителю\n3. Средняя цена машин определённого типа\n4. Сортировка по году выпуска\n5. Количество машин каждого типа\n6. Самая старая и новая машины\n0. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("VIN: "); String vin = scanner.nextLine();
                    System.out.print("Модель: "); String model = scanner.nextLine();
                    System.out.print("Производитель: "); String m = scanner.nextLine();
                    System.out.print("Год: "); int y = scanner.nextInt();
                    System.out.print("Пробег: "); int mil = scanner.nextInt();
                    System.out.print("Цена: "); double pr = scanner.nextDouble();
                    System.out.print("Тип машины (SEDAN, SUV, ELECTRIC...): "); scanner.nextLine();
                    CarType t = CarType.valueOf(scanner.nextLine().toUpperCase());
                    dealer.addCar(new Car(vin, model, m, y, mil, pr, t));
                }
                case 2 -> {
                    System.out.print("Введите производителя: ");
                    String man = scanner.nextLine();
                    dealer.findByManufacturer(man).forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Введите тип: ");
                    CarType type = CarType.valueOf(scanner.nextLine().toUpperCase());
                    System.out.println("Средняя цена: " + dealer.averagePriceByType(type));
                }
                case 4 -> dealer.getSortedByYear().forEach(System.out::println);
                case 5 -> dealer.getTypeStats().forEach((k, v) -> System.out.println(k + ": " + v));
                case 6 -> {
                    System.out.println("Самая старая: " + dealer.getOldestCar());
                    System.out.println("Самая новая: " + dealer.getNewestCar());
                }
                case 0 -> System.exit(0);
            }
        }
    }
}
