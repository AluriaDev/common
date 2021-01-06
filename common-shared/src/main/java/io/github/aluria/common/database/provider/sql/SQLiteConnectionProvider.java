package io.github.aluria.common.database.provider.sql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class SQLiteConnectionProvider implements SQLConnectionProvider {

    private static final String SQLITE_URL_FILE_MODEL = "jdbc:sqlite:";

    private Connection connection;

    @Override
    public Connection getConnectionInstance() {
        return connection;
    }

    @Override
    public boolean isRunning() {
        return connection != null;
    }

    @Override
    public void connect(Properties properties) {
        try {
            Class.forName("org.sqlite.JDBC");

            File file = this.getDatabaseFile(properties);

            this.connection = DriverManager.getConnection(SQLITE_URL_FILE_MODEL + file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if(isRunning()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private File getDatabaseFile(Properties properties) throws IOException {
        String fileName = properties.getProperty("database");
        if (!fileName.endsWith(".db")) {
            fileName += ".db";
        }

        File mainDir = new File(properties.getProperty("dir"));
        mainDir.mkdirs();

        File file = new File(mainDir, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }
}