package edu.wpi.cs3733.d20.teamk.mortuary.impl.database;

import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryServiceException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.derby.database.Database;

@Slf4j
public class MortuaryDB {

  @Getter Database database;
  @Getter @Setter Connection connection = null;
  private boolean tableExists = false;
  private String databaseName;

  public MortuaryDB(String name) throws MortuaryServiceException {
    this.databaseName = name;
    setupConnection();
    checkTables();
  }

  public MortuaryDB(Connection connection) throws MortuaryServiceException {
    this.connection = connection;
    checkTables();
  }

  public MortuaryDB() throws MortuaryServiceException {
    this.databaseName = "MortuaryDB";
    setupConnection();
    checkTables();
  }

  private void setupConnection() throws MortuaryServiceException {
    // Get the driver for an embedded derby database
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      throw new MortuaryServiceException(e);
    }

    // Try the connection and throw errors if they appear
    try {
      connection =
          DriverManager.getConnection(
              "jdbc:derby:" + databaseName + ";create=true;user=app;password=password;");
    } catch (SQLException e) {
      throw new MortuaryServiceException("Connection failed", e);
    }
  }

  private void checkTables() throws MortuaryServiceException {
    try {
      // Check if the tables already exist to ensure they don't get re-written
      DatabaseMetaData metas = connection.getMetaData();
      ResultSet tables = metas.getTables(connection.getCatalog(), null, "EMPLOYEES", null);

      if (tables.next()) {
        tableExists = true;
      } else {
        log.info("Tables don't exist, creating...");
        setupDatabase();
      }
    } catch (SQLException e) {
      throw new MortuaryServiceException("Failed to create tables", e);
    }
  }

  private void setupDatabase() throws MortuaryServiceException {
    String file = loadResource("tables.sql", MortuaryDB.class);
    String[] tables = file.split(";");
    for (String table : tables) {
      try {
        connection.prepareStatement(table).executeUpdate();
      } catch (SQLException e) {
        throw new MortuaryServiceException("Failed to create table", e);
      }
      log.info("Created table");
    }
  }

  public static String loadResource(String str, Class<?> cls) {
    try {
      StringBuilder builder = new StringBuilder();
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(cls.getResourceAsStream(str)));

      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line).append("\n");
      }
      return builder.substring(0, builder.length() - 1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
