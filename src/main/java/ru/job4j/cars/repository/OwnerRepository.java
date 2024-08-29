package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class OwnerRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     * @param owner владелец.
     * @return владелец с id.
     */
    public Owner create(Owner owner) {
        crudRepository.run(session -> session.save(owner));
        return owner;
    }

    /**
     * Обновить в базе владельца.
     * @param owner владелец.
     */
    public void update(Owner owner) {
        crudRepository.run(session -> session.merge(owner));
    }

    /**
     * Удалить владельца по id.
     * @param ownerId ID
     */
    public void delete(int ownerId) {
        crudRepository.run(
                "delete from Owner where id = :fId",
                Map.of("fId", ownerId)
        );
    }

    /**
     * Найти владелец по ID
     * @return владелец.
     */
    public Optional<Owner> findById(int ownerId) {
        return crudRepository.optional(
                "from Owner where id = :fId", Owner.class,
                Map.of("fId", ownerId)
        );
    }

    public List<Owner> findAll() {
        return crudRepository.query("from Owner order by id asc", Owner.class);
    }
}
