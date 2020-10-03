package ru.emi.ignitecache.repository;

public interface CacheRepository<K, V> extends ReadCacheRepository<K, V> {

    void save(V value);

    void deleteById(K key);
}
