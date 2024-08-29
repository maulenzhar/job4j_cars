package ru.job4j.cars.repository;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class CarRepositoryTest {
    private static CarRepository carRepository;
    private static EngineRepository engineRepository;
    private static SessionFactory sf;

    @BeforeAll
    public static void initRepository() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        carRepository = new CarRepository(new CrudRepository(sf));
        engineRepository = new EngineRepository(new CrudRepository(sf));

      /*  List<Car> cars = carRepository.findAll();
        for (Car c : cars) {
            carRepository.delete(c.getId());
        }

        List<Engine> engines = engineRepository.findAll();
        for (Engine e : engines) {
            engineRepository.delete(e.getId());
        }*/
    }

    @Test
    void create() {
        Engine engine = engineRepository.create(new Engine(0, "V8"));
        Car car = new Car(0, "BMW", engine);
        Car created = carRepository.create(car);
        assertThat(created).isEqualTo(Optional.of(car).get());
    }

    @Test
    void findById() {
        List<Car> c = carRepository.findAll();
        assertThat(carRepository.findById(c.get(0).getId()).get()).isEqualTo(c.get(0));
    }

    /*@Test
    void delete() {
        List<Engine> engines = engineRepository.findAll();
        if (!engines.isEmpty()) {
            engineRepository.delete(engines.get(0).getId());
            Optional<Engine> e = engineRepository.findById(engines.get(0).getId());
            assertThat(e).isEmpty();
        }
    }*/
}