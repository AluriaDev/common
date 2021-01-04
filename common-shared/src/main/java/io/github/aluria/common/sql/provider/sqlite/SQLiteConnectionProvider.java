package io.github.aluria.common.sql.provider.sqlite;

import io.github.aluria.common.sql.provider.ConnectionProvider;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public final class SQLiteConnectionProvider extends ConnectionProvider {
	
	private static final String SQLITE_URL_FILE_MODEL = "jdbc:sqlite:";

	protected Connection cachedConnection;
	
	public SQLiteConnectionProvider(Properties properties) {
		super(properties);
	}
	
	@SneakyThrows
	@Override
	public Connection getCurrentConnection() {
		if (cachedConnection != null && !cachedConnection.isClosed()) {
			return cachedConnection;
		}
		
		cachedConnection = this.connect();
		if (cachedConnection != null) {
			return cachedConnection;
		}
		
		throw new NullPointerException("Provided connection is null.");
	}
	
	private Connection connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			
			File file = this.getDatabaseFile();
			
			return DriverManager.getConnection(SQLITE_URL_FILE_MODEL + file.getPath());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private File getDatabaseFile() throws IOException {
		String fileName = getProperties().getProperty("database");
		if (!fileName.endsWith(".db")) {
			fileName += ".db";
		}
		
		File mainDir = new File(getProperties().getProperty("dir"));
		mainDir.mkdirs();
		
		File file = new File(mainDir, fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		
		return file;
	}
	
	@Override
	protected void finalize() throws Throwable {
		cachedConnection.close();
		cachedConnection = null;
		
		super.finalize();
	}
}