package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class PostRepository {
    private final AtomicLong count;
    private final Map<Long, Post> repository;

    public PostRepository() {
        long initiatCount = 1;
        this.count = new AtomicLong(initiatCount);
        this.repository = new ConcurrentHashMap<>();
    }

    public List<Post> all() {
        System.out.println(repository);
        return repository.values().stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        var post = repository.get(id);
        if (post != null) {
            return Optional.of(post);
        } else {
            return Optional.empty();
        }
    }

    public Post save(Post post) {
        long postId = post.getId();
        if (postId == 0) {
            post.setId(count.getAndIncrement());
            repository.put(post.getId(),post);
            return post;
        } else if (repository.containsKey(postId)){
            repository.put(postId,post);
            return post;
        }else
        throw new NotFoundException();
    }

    public void removeById(long id) {
        repository.remove(id);
        throw new NotFoundException();
    }
}
