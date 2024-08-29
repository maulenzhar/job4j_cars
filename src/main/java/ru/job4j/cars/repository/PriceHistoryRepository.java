package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class PriceHistoryRepository {
    private final CrudRepository crudRepository;

    public PriceHistory create(PriceHistory priceHistory) {
        crudRepository.run(session -> session.save(priceHistory));
        return priceHistory;
    }

    public void update(PriceHistory priceHistory) {
        crudRepository.run(session -> session.merge(priceHistory));
    }

    public void delete(int priceHistoryId) {
        crudRepository.run(
                "delete from PriceHistory where id = :fId",
                Map.of("fId", priceHistoryId)
        );
    }

    public Optional<PriceHistory> findById(int priceHistoryId) {
        return crudRepository.optional(
                "from PriceHistory where id = :fId", PriceHistory.class,
                Map.of("fId", priceHistoryId)
        );
    }

    public List<PriceHistory> findAll() {
        return crudRepository.query("from PriceHistory order by id asc", PriceHistory.class);
    }

}
