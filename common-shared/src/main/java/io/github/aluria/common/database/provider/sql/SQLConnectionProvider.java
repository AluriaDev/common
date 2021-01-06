package io.github.aluria.common.database.provider.sql;

import io.github.aluria.common.database.provider.ConnectionProvider;
import io.github.aluria.common.sql.function.SqlFunction;
import io.github.aluria.common.sql.mapper.SqlEntryMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface SQLConnectionProvider extends ConnectionProvider<Connection> {

    default boolean execute(String sql, Object... values) {
        final Connection connection = this.getConnectionInstance();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            applyValuesToStatement(statement, values);

            return statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default int executeUpdate(String sql, Object... statementValues) {
        final Connection connection = this.getConnectionInstance();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            this.applyValuesToStatement(preparedStatement, statementValues);

            return preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    default ResultSet executeQuery(String sql, Object... statementValues) {
        try (PreparedStatement statement = this.getConnectionInstance().prepareStatement(sql)) {
            applyValuesToStatement(statement, statementValues);

            return statement.executeQuery();
        } catch (Exception e) {
            return null;
        }
    }

    default <T> List<T> selectAsList(String sql, SqlFunction<ResultSet, T> function, Object... statementValues) {
        final Connection currentConnection = getConnectionInstance();
        final List<T> collected = Collections.synchronizedList(new ArrayList<>());

        try (PreparedStatement statement = currentConnection.prepareStatement(sql)) {
            applyValuesToStatement(statement, statementValues);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    T obj = function.apply(resultSet);
                    if (obj != null) {
                        collected.add(obj);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return collected;
    }

    default <T> T getFirstFromQuery(String sql, SqlFunction<ResultSet, T> function, Object... statementValues) {
        final Connection currentConnection = this.getConnectionInstance();

        try (PreparedStatement statement = currentConnection.prepareStatement(sql)) {
            applyValuesToStatement(statement, statementValues);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.first()) {
                    return function.apply(resultSet);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    default <K, V> Map<K, V> mapFromQuery(String sql, SqlEntryMapper<K, V> mapper, Object... statementValues) {
        final Map<K, V> map = new ConcurrentHashMap<>();

        try (ResultSet resultSet = executeQuery(sql, statementValues)) {

            while (resultSet.next()) {

                K key = mapper.transformKey(resultSet);
                V value = mapper.transformValue(resultSet);

                map.put(key, value);
            }

            return map;
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }

    default void applyValuesToStatement(PreparedStatement statement, Object... values) throws SQLException {
        if (values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
        }
    }
}
