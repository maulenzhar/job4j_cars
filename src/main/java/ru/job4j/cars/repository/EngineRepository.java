package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Engine;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class EngineRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     * @param engine мотор.
     * @return машина с id.
     */
    public Engine create(Engine engine) {
        crudRepository.run(session -> session.persist(engine));
        return engine;
    }

    /**
     * Обновить в базе мотор.
     * @param engine мотор.
     */
    public void update(Engine engine) {
        crudRepository.run(session -> session.merge(engine));
    }

    /**
     * Удалить мотор по id.
     * @param engineId ID
     */
    public void delete(int engineId) {
        crudRepository.run(
                "delete from Engine where id = :fId",
                Map.of("fId", engineId)
        );
    }

    /**
     * Найти мотор по ID
     * @return мотор.
     */
    public Optional<Engine> findById(int engineId) {
        return crudRepository.optional(
                "from Engine where id = :fId", Engine.class,
                Map.of("fId", engineId)
        );
    }
}
