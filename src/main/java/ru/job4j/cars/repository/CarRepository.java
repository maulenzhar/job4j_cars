package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class CarRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     * @param car машина.
     * @return машина с id.
     */
    public Car create(Car car) {
        crudRepository.run(session -> session.merge(car));
        return car;
    }

    /**
     * Обновить в базе машину.
     * @param car машина.
     */
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    /**
     * Удалить пользователя по id.
     * @param carId ID
     */
    public void delete(int carId) {
        crudRepository.run(
                "delete from Car where id = :fId",
                Map.of("fId", carId)
        );
    }

    /**
     * Найти машину по ID
     * @return машина.
     */
    public Optional<Car> findById(int carId) {
        return crudRepository.optional(
                "from Car where id = :fId", Car.class,
                Map.of("fId", carId)
        );
    }

    /**
     * Найти все
     */
    public List<Car> findAll() {
        return crudRepository.query("from Car order by id asc", Car.class);
    }
}
