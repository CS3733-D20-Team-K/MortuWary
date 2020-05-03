package edu.wpi.cs3733.d20.teamk.mortuary.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MortuaryDBController {
  private Connection connection;

  public MortuaryDBController(Connection connection) {
    this.connection = connection;
  }

  // public void addRequest(MortuaryRequest) {}
  // public void updateRequest(MortuaryRequest) {}
  // public void removeRequest(MortuaryRequest) {}
  // public void removeRequest(UUID id) {}

  /**
   * Used to insert rows into the DB (Also used for Update statements)
   *
   * @param statement
   * @param values
   * @throws SQLException
   */
  private void execute(String statement, List<String> values) throws SQLException {
    PreparedStatement pst = connection.prepareStatement(statement);
    int i = 1;
    for (String string : values) {
      pst.setString(i++, string);
    }
    pst.executeUpdate();
  }

  private List<List<String>> get(String statement, List<String> values) throws SQLException {
    List<String> headers = new ArrayList<>();
    List<List<String>> rows = new ArrayList<>();
    PreparedStatement pst = connection.prepareStatement(statement);

    int j = 1;
    for (String string : values) {
      pst.setString(j++, string);
    }

    ResultSet rs = pst.executeQuery();
    ResultSetMetaData rsmd = rs.getMetaData();
    for (int i = 0; i < rsmd.getColumnCount(); i++) {
      headers.add(rsmd.getColumnName(i + 1).toLowerCase());
    }

    // Add each row to the Rows list
    while (rs.next()) {
      List<String> row = new ArrayList<>();
      for (String entry : headers) {
        row.add(rs.getString(entry));
      }
      rows.add(row);
    }

    return rows;
  }

  // public void addEmployee(Employee) {}
  public void addEmployee(UUID id, String name, String login) {
    String statement = "insert into employees values (?, ?, ?)";
    try {
      execute(statement, Arrays.asList(id.toString(), name, login));
      log.info("User " + login + " created with ID " + id.toString());
    } catch (SQLException e) {
      log.info("Employee could not be inserted");
      e.printStackTrace();
    }
  }

  public void addEmployee(String name, String login) {
    String statement = "insert into employees values (?, ?, ?)";
    UUID id = UUID.randomUUID();
    addEmployee(id, name, login);
  }

  // public void removeEmployee(Employee) {}
  public void removeEmployee(UUID id) {}
  // public void getCreatedRequests(Employee) {}
  public void getCreatedRequests(UUID id) {}
}
