package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Photo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class PhotoRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     * @param photo фото.
     * @return фото с id.
     */
    public Photo create(Photo photo) {
        crudRepository.run(session -> session.save(photo));
        return photo;
    }

    /**
     * Обновить в базе владельца.
     * @param photo фото.
     */
    public void update(Photo photo) {
        crudRepository.run(session -> session.merge(photo));
    }

    /**
     * Удалить владельца по id.
     * @param photoId ID
     */
    public void delete(int photoId) {
        crudRepository.run(
                "delete from Photo where id = :fId",
                Map.of("fId", photoId)
        );
    }

    /**
     * Найти фото по ID
     * @return фото.
     */
    public Optional<Photo> findById(int photoId) {
        return crudRepository.optional(
                "from Photo where id = :fId", Photo.class,
                Map.of("fId", photoId)
        );
    }

    public List<Photo> findAll() {
        return crudRepository.query("from Photo order by id asc", Photo.class);
    }
}
