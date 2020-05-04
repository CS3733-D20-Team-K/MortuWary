package edu.wpi.cs3733.d20.teamk.mortuary.impl.database;

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

  public MortuaryDB(String name) {
    this.databaseName = name;
    setupConnection();
  }

  public MortuaryDB() {
    this.databaseName = "MortuaryDB";
    setupConnection();
  }

  private void setupConnection() {
    // Get the driver for an embedded derby database
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return;
    }

    // Try the connection and throw errors if they appear
    try {
      connection =
          DriverManager.getConnection(
              "jdbc:derby:" + databaseName + ";create=true;user=app;password=password;");

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
      System.out.println("Connection failed.");
      e.printStackTrace();
      return;
    }
  }

  private void setupDatabase() throws SQLException {
    String file = loadResource("tables.sql", MortuaryDB.class);
    String[] tables = file.split(";");
    for (String table : tables) {
      connection.prepareStatement(table).executeUpdate();
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
