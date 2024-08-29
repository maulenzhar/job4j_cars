package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PostRepositoryTest {
    private static PostRepository postRepository;
    private static EngineRepository engineRepository;
    private static PriceHistoryRepository priceHistoryRepository;
    private static CarRepository carRepository;
    private static PhotoRepository photoRepository;
    private static UserRepository userRepository;
    private static OwnerRepository ownerRepository;

    @BeforeAll
    public static void initRepository() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        postRepository = new PostRepository(new CrudRepository(sf));
        engineRepository = new EngineRepository(new CrudRepository(sf));
        priceHistoryRepository = new PriceHistoryRepository(new CrudRepository(sf));
        carRepository = new CarRepository(new CrudRepository(sf));
        photoRepository = new PhotoRepository(new CrudRepository(sf));
        userRepository = new UserRepository(new CrudRepository(sf));
        ownerRepository = new OwnerRepository(new CrudRepository(sf));

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
        List<Owner> owners = ownerRepository.findAll();
        for (Owner o : owners) {
            ownerRepository.delete(o.getId());
        }
        List<User> users = userRepository.findAll();
        for (User u : users) {
            userRepository.delete(u.getId());
        }

        List<Photo> photos = photoRepository.findAll();
        for (Photo  p : photos) {
            photoRepository.delete(p.getId());
        }
    }

    @Test
    void create() throws SQLException {
       /* Server.main();*/
        engineRepository.create(new Engine(0, "V8"));
        List<Engine> engine = engineRepository.findAll();
        Car car = carRepository.create(new Car(0, "BMW", engine.get(0)));
        User user = userRepository.create(new User(0, "test", "test"));
        Post post = new Post(0, "test", OffsetDateTime.now(),
                List.of(new PriceHistory(0, new BigDecimal("123.45"), new BigDecimal("222.45"), LocalDateTime.now())),
                car,
                List.of(new Photo(0, "test", "test")),
                List.of(user));
        Post result = postRepository.create(post);
        assertThat(post).isEqualTo(Optional.of(result).get());
    }
}