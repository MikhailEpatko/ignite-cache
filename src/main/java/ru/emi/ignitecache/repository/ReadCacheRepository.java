package ru.emi.ignitecache.repository;

import java.util.Optional;

public interface ReadCacheRepository<K, V> {

    Optional<V> findById(K id);

    boolean existsById(K id);

    long count();
}
