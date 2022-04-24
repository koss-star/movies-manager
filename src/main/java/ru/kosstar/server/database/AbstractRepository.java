package ru.kosstar.server.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractRepository<K, V> {
    protected final Connection connection;

    public AbstractRepository(Connection connection) {
        this.connection = connection;
    }

    public V get(K key) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<V> getAll() throws SQLException {
        throw new UnsupportedOperationException();
    }

    public V insert(V value) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public void update(V value) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public void delete(K key) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
