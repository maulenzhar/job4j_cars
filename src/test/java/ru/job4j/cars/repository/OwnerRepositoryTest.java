package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerRepositoryTest {
    private static OwnerRepository ownerRepository;
    private static UserRepository userRepository;

    @BeforeAll
    public static void initRepository() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        ownerRepository = new OwnerRepository(new CrudRepository(sf));
        userRepository = new UserRepository(new CrudRepository(sf));
    }

    private static void deleteAll() {
        List<Owner> owners = ownerRepository.findAll();
        for (Owner o : owners) {
            ownerRepository.delete(o.getId());
        }

        List<User> users = userRepository.findAll();
        for (User u : users) {
            userRepository.delete(u.getId());
        }
    }

    @Test
    void create() {
        deleteAll();
        User user = userRepository.create(new User(0, "test", "test"));
        Owner owner = new Owner(0, "Test", user);
        Owner result = ownerRepository.create(owner);
        assertThat(owner).isEqualTo(Optional.of(result).get());
    }

    @Test
    void findById() {
        deleteAll();
        User user = userRepository.create(new User(0, "test", "test"));
        Owner owner = new Owner(0, "Test", user);
        Owner result = ownerRepository.create(owner);
        assertThat(ownerRepository.findById(result.getId()).get()).isEqualTo(owner);
    }

    @Test
    void delete() {
        deleteAll();
        User user = userRepository.create(new User(0, "test", "test"));
        Owner owner = new Owner(0, "Test", user);
        List<Owner> owners = ownerRepository.findAll();
        if (!owners.isEmpty()) {
            ownerRepository.delete(owners.get(0).getId());
            Optional<Owner> o = ownerRepository.findById(owners.get(0).getId());
            assertThat(o).isEmpty();
        }
    }

}