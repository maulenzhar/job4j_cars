package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Post;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class PostRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     * @param post пост.
     * @return пост с id.
     */
    public Post create(Post post) {
        crudRepository.run(session -> session.save(post));
        return post;
    }

    /**
     * Обновить в базе пост.
     * @param post пост.
     */
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    /**
     * Удалить пост по id.
     * @param postId ID
     */
    public void delete(int postId) {
        crudRepository.run(
                "delete from Post where id = :fId",
                Map.of("fId", postId)
        );
    }

    /**
     * Найти пост по ID
     * @return пост.
     */
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "from Post where id = :fId", Post.class,
                Map.of("fId", postId)
        );
    }

    /**
     * Показать объявления за последний день
     * @return пост.
     */
    public List<Post> findByLastDay(OffsetDateTime date) {
        return crudRepository.query("from Post "
                + "where created_at >= :date "
                + "order by id desc", Post.class,
                Map.of("date", date));
    }

    /**
     * Показать объявления с фото
     * @return пост.
     */
    public List<Post> findWithPhoto() {
        return crudRepository.query(
                "from Post p where size(p.photos) > 0 order by p.id",
                Post.class);
    }

    /**
     * Показать объявления определенной марки
     * @return пост.
     */
    public List<Post> findByBrand(String brand) {
        return crudRepository.query(
                "from Post p join p.cars c WHERE c.brand = :brand",
                Post.class,
                Map.of("brand", brand)
        );
    }

    public List<Post> findAll() {
        return crudRepository.query("from Post order by id asc", Post.class);
    }
}
