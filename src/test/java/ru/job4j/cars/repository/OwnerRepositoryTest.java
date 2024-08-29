package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerRepositoryTest {
    private static OwnerRepository ownerRepository;

    @BeforeAll
    public static void initRepository() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        ownerRepository = new OwnerRepository(new CrudRepository(sf));

        List<Owner> owners = ownerRepository.findAll();
        for (Owner o : owners) {
            ownerRepository.delete(o.getId());
        }
    }

    @Test
    void create() {
        User user = new User(0, "test", "test");
        Owner owner = new Owner(0, "Test", user);
        Owner result = ownerRepository.create(owner);
        assertThat(owner).isEqualTo(Optional.of(result).get());
    }

    @Test
    void findById() {
        List<Owner> owners = ownerRepository.findAll();
        assertThat(ownerRepository.findById(owners.get(0).getId()).get()).isEqualTo(owners.get(0));
    }

    @Test
    void delete() {
        ownerRepository.delete(0);
        Optional<Owner> o = ownerRepository.findById(0);
        assertThat(o).isEmpty();
    }

}