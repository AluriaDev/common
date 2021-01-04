package io.github.aluria.common.sql.provider.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.aluria.common.sql.provider.ConnectionProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariConnectionProvider extends ConnectionProvider {

    private static final String MYSQL_CONNECTION_STRING = "jdbc:<driver>://<hostname>:<port>/<database>";

    private DataSource dataSource;

    public HikariConnectionProvider(Properties properties) {
        super(properties);
    }

    @Override
    public Connection getCurrentConnection() {
        if(dataSource == null) {
            this.connect();
        }

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void connect() {
        if(dataSource != null) throw new UnsupportedOperationException("DataSource already connected");

        final HikariConfig config = new HikariConfig();

        config.setJdbcUrl(this.getJDBCUrl());

        config.setUsername(getProperties().getProperty("username"));
        config.setPassword(getProperties().getProperty("password"));

        config.setMaximumPoolSize(Runtime.getRuntime().availableProcessors() * 2);

        config.addDataSourceProperty("autoReconnect", "true");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "256");

        this.dataSource = new HikariDataSource(config);
    }

    private String getJDBCUrl() {
        return MYSQL_CONNECTION_STRING
          .replace("<driver>", getProperties().getProperty("driver"))
          .replace("<hostname>", getProperties().getProperty("hostname"))
          .replace("<port>", getProperties().getProperty("port"))
          .replace("<database>", getProperties().getProperty("database"));
    }
}
