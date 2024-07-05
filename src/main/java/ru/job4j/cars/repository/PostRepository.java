package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Post;

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
        crudRepository.run(session -> session.persist(post));
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
}
