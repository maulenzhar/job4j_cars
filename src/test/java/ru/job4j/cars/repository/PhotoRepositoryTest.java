/*
package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Photo;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PhotoRepositoryTest {
    private static PhotoRepository photoRepository;

    @BeforeAll
    public static void initRepository() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        photoRepository = new PhotoRepository(new CrudRepository(sf));

        List<Photo> photos = photoRepository.findAll();
        for (Photo p : photos) {
            photoRepository.delete(p.getId());
        }
    }

    @Test
    void create() {
        Photo photo = new Photo(0, "Test", "test");
        Photo result = photoRepository.create(photo);
        assertThat(photo).isEqualTo(Optional.of(result).get());
    }

    @Test
    void findById() {
        List<Photo> photo = photoRepository.findAll();
        assertThat(photoRepository.findById(photo.get(0).getId()).get()).isEqualTo(photo.get(0));
    }

    @Test
    void delete() {
        photoRepository.delete(0);
        Optional<Photo> p = photoRepository.findById(0);
        assertThat(p).isEmpty();
    }
}*/
