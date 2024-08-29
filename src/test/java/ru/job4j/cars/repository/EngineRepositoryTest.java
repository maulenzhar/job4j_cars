package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EngineRepositoryTest {
    private static CarRepository carRepository;
    private static EngineRepository engineRepository;

    @BeforeAll
    public static void initRepository() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        carRepository = new CarRepository(new CrudRepository(sf));
        engineRepository = new EngineRepository(new CrudRepository(sf));

        List<Car> cars = carRepository.findAll();
        for (Car c : cars) {
            carRepository.delete(c.getId());
        }

        List<Engine> engines = engineRepository.findAll();
        for (Engine e : engines) {
            engineRepository.delete(e.getId());
        }
    }

    @Test
    void create() {
        Engine engine = new Engine(0, "V8");
        Engine result = engineRepository.create(engine);
        assertThat(engine).isEqualTo(Optional.of(result).get());
    }

    @Test
    void findById() {
        List<Engine> engines = engineRepository.findAll();
        assertThat(engineRepository.findById(engines.get(0).getId()).get()).isEqualTo(engines.get(0));
    }

    @Test
    void delete() {
        engineRepository.delete(0);
        Optional<Engine> c = engineRepository.findById(0);
        assertThat(c).isEmpty();
    }
}