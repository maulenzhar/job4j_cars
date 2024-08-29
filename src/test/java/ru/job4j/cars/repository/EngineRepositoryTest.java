package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EngineRepositoryTest {
    private static PostRepository postRepository;
    private static CarRepository carRepository;
    private static EngineRepository engineRepository;
    private static PhotoRepository photoRepository;
    private static PriceHistoryRepository priceHistoryRepository;

    @BeforeAll
    public static void initRepository() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        carRepository = new CarRepository(new CrudRepository(sf));
        engineRepository = new EngineRepository(new CrudRepository(sf));
        postRepository = new PostRepository(new CrudRepository(sf));
        photoRepository = new PhotoRepository(new CrudRepository(sf));
        priceHistoryRepository = new PriceHistoryRepository(new CrudRepository(sf));

        List<PriceHistory> priceHistories = priceHistoryRepository.findAll();
        for (PriceHistory p : priceHistories) {
            priceHistoryRepository.delete(p.getId());
        }

        List<Photo> photos = photoRepository.findAll();
        for (Photo p : photos) {
            photoRepository.delete(p.getId());
        }

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

    @Test
    void create() {
        Engine savedEngine = engineRepository.create(new Engine(1, "V8"));
        assertThat(savedEngine.getName()).isEqualTo("V8");
    }

    @Test
    void findById() {
        Engine savedEngine = engineRepository.create(new Engine(1, "V8"));
        List<Engine> engines = engineRepository.findAll();
        assertThat(engineRepository.findById(engines.get(0).getId()).get()).isEqualTo(engines.get(0));
    }

    @Test
    void delete() {
        Engine savedEngine = engineRepository.create(new Engine(1, "V8"));
        List<Engine> engines = engineRepository.findAll();
        if (!engines.isEmpty()) {
            engineRepository.delete(engines.get(0).getId());
            Optional<Engine> e = engineRepository.findById(engines.get(0).getId());
            assertThat(e).isEmpty();
        }
    }
}
