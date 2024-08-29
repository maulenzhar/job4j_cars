package ru.job4j.cars.repository;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class CarRepositoryTest {
    private static PostRepository postRepository;
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
        postRepository = new PostRepository(new CrudRepository(sf));
    }

    @Test
    void create() {
        deleteAll();

        engineRepository.create(new Engine(0, "V8"));
        List<Engine> engine = engineRepository.findAll();
        Car car = new Car(0, "BMW", engine.get(0));
        Car created = carRepository.create(car);
        assertThat(created).isEqualTo(Optional.of(car).get());
    }

    @Test
    void delete() {
        deleteAll();

        engineRepository.create(new Engine(0, "V8"));
        List<Engine> engine = engineRepository.findAll();
        Car car = new Car(0, "BMW", engine.get(0));
        Car created = carRepository.create(car);
        carRepository.delete(car.getId());
        Optional<Car> c = carRepository.findById(created.getId());
        assertThat(c).isEmpty();
    }

    @Test
    void findById() {
        deleteAll();

        engineRepository.create(new Engine(0, "V8"));
        List<Engine> engine = engineRepository.findAll();
        Car car = carRepository.create(new Car(0, "BMW", engine.get(0)));
        Optional<Car> c = carRepository.findById(car.getId());
        assertThat(c.get().getName()).isEqualTo("BMW");
    }

    private static void deleteAll() {
        List<Post> posts = postRepository.findAll();
        for (Post p : posts) {
            postRepository.delete(p.getId());
        }

        List<Car> cars = carRepository.findAll();
        for (Car c : cars) {
            carRepository.delete(c.getId());
        }

        List<Engine> engines = engineRepository.findAll();
        for (Engine e : engines) {
            engineRepository.delete(e.getId());
        }
    }
}