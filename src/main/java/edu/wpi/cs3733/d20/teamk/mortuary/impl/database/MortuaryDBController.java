package edu.wpi.cs3733.d20.teamk.mortuary.impl.database;

import edu.wpi.cs3733.d20.teamk.mortuary.Circumstance;
import edu.wpi.cs3733.d20.teamk.mortuary.Employee;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryRequest;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryServiceException;
import edu.wpi.cs3733.d20.teamk.mortuary.Person;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MortuaryDBController {
  private Connection connection;

  public MortuaryDBController(Connection connection) {
    this.connection = connection;
  }

  /**
   * Used to insert rows into the DB (Also used for Delete statements)
   *
   * @param statement
   * @param values
   * @throws SQLException
   */
  private void execute(String statement, List<String> values) throws MortuaryServiceException {
    try {
      PreparedStatement pst = connection.prepareStatement(statement);
      int i = 1;
      for (String string : values) {
        pst.setString(i++, string);
      }
      pst.executeUpdate();
    } catch (SQLException e) {
      throw new MortuaryServiceException("Failed to execute statement: " + statement, e);
    }
  }

  /**
   * Updates a statement given in with values
   *
   * @param statement
   * @param values
   * @throws SQLException
   */
  private void update(String statement, List<String> values) throws MortuaryServiceException {
    try {
      PreparedStatement pst = connection.prepareStatement(statement);

      int i;
      for (i = 1; i < values.size(); i++) {
        pst.setString(i, values.get(i));
      }
      pst.setString(i, values.get(0)); // set pk in where statement

      pst.executeUpdate();
    } catch (SQLException e) {
      throw new MortuaryServiceException("Failed to update statement: " + statement, e);
    }
  }

  /**
   * Gets a the rows from a table and returns them as lists of strings.
   *
   * @param statement
   * @param values
   * @return
   * @throws SQLException
   */
  private List<List<String>> get(String statement, List<String> values)
      throws MortuaryServiceException {
    try {
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
    } catch (SQLException e) {
      throw new MortuaryServiceException("Failed to get statement: " + statement, e);
    }
  }

  /**
   * Adds an employee to the Database
   *
   * @param id The UUID of the employee
   * @param name The name of the employee
   * @param login The login token of the employee
   */
  public void addEmployee(String id, String name, String login) throws MortuaryServiceException {
    String statement = "insert into employees values (?, ?, ?)";
    execute(statement, Arrays.asList(id, name, login));
    log.info("User " + login + " created with ID " + id);
  }

  /**
   * Adds an employee to the Database, and assigns them a random UUID
   *
   * @param name The name of the employee
   * @param login The login token of the employee
   */
  public void addEmployee(String name, String login) throws MortuaryServiceException {
    UUID id = UUID.randomUUID();
    addEmployee(id.toString(), name, login);
  }

  /**
   * Takes in an employee object and adds them to the database
   *
   * @param employee The employee to add
   */
  public void addEmployee(Employee employee) throws MortuaryServiceException {
    addEmployee(employee.getId(), employee.getName(), employee.getUsername());
  }

  /**
   * Removes the user from the database with the given UUID
   *
   * @param id
   */
  public void removeEmployee(String id) throws MortuaryServiceException {
    String statement = "delete from employees where empID = ?";
    execute(statement, Arrays.asList(id.toString()));
  }

  public void updateEmployee(Employee employee) throws MortuaryServiceException {
    String statement = "update employees set " + "name = ?, " + "username = ? " + "where empID = ?";
    update(statement, Arrays.asList(employee.getId(), employee.getName(), employee.getUsername()));
  }

  /**
   * Takes in a person and removes them from the database.
   *
   * @param employee
   */
  public void removeEmployee(Employee employee) throws MortuaryServiceException {
    removeEmployee(employee.getId());
  }

  /**
   * Adds a deceased person to the Database
   *
   * @param id The deceased person's id
   * @param name The deceased person's name
   * @param gender The deceased person's gender
   * @param age The deceased person's age
   */
  public void addPerson(String id, String name, String gender, int age)
      throws MortuaryServiceException {
    String statement = "insert into deceased values (?, ?, ?, ?)";
    execute(statement, Arrays.asList(id, name, gender.toUpperCase(), Integer.toString(age)));
    log.info("Deceased " + name + " created with ID " + id);
  }

  /**
   * Adds a deceased person to the database and assigns them a random UUID
   *
   * @param name The deceased person's name
   * @param gender The deceased person's gender
   * @param age The deceased person's age
   */
  public void addPerson(String name, String gender, int age) throws MortuaryServiceException {
    addPerson(UUID.randomUUID().toString(), name, gender, age);
  }

  /**
   * Adds a person object to the database
   *
   * @param person
   */
  public void addPerson(Person person) throws MortuaryServiceException {
    addPerson(person.getId(), person.getName(), person.getGender(), person.getAge());
  }

  public void removePerson(UUID id) throws MortuaryServiceException {
    String statement = "delete from deceased where personID = ?";
    execute(statement, Arrays.asList(id.toString()));
  }

  public void removePerson(Person person) throws MortuaryServiceException {
    removeEmployee(person.getId());
  }

  public void updatePerson(Person person) throws MortuaryServiceException {
    String statement =
        "update deceased set " + "name = ?, " + "sex = ?, " + "age = ? " + "where personID = ?";
    update(
        statement,
        Arrays.asList(
            person.getId(), person.getName(), person.getGender(), String.valueOf(person.getAge())));
  }

  /**
   * Returns the list of employees in the DB
   *
   * @return
   */
  public List<Employee> getEmployees() throws MortuaryServiceException {
    String statement = "select * from employees";
    List<Employee> emps = new ArrayList<Employee>();
    for (List<String> emp : get(statement, Arrays.asList())) {
      emps.add(new Employee(emp.get(0), emp.get(1), emp.get(2)));
    }

    return emps;
  }

  /**
   * Returns the employee whose ID matches the input
   *
   * @param id The ID of the desired employee
   * @return
   */
  public Optional<Employee> getEmployee(String id) throws MortuaryServiceException {
    for (Employee emp : getEmployees()) {
      if (emp.getId().equals(id)) {
        return Optional.of(emp);
      }
    }
    return Optional.empty();
  }

  /**
   * Returns the list of deceased people in the DB
   *
   * @return
   */
  public List<Person> getPeople() throws MortuaryServiceException {
    String statement = "select * from deceased";
    List<Person> pers = new ArrayList<>();
    for (List<String> per : get(statement, Arrays.asList())) {
      pers.add(new Person(per.get(0), per.get(1), per.get(2), Integer.parseInt(per.get(3))));
    }

    return pers;
  }

  /**
   * Returns the person whose ID matches the input
   *
   * @param id The ID of the desired person
   * @return
   */
  public Optional<Person> getPerson(String id) throws MortuaryServiceException {
    for (Person per : getPeople()) {
      if (per.getId().equals(id)) {
        return Optional.of(per);
      }
    }
    return Optional.empty();
  }

  /**
   * Adds a request to the database
   *
   * @param mortuaryRequest The request to add
   */
  public void addRequest(MortuaryRequest mortuaryRequest) throws MortuaryServiceException {
    String statement = "insert into tickets values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    execute(statement, mortuaryArgs(mortuaryRequest));
    log.info("Adding reqeuest: " + mortuaryRequest.getId().toString());
  }

  /**
   * Modifies the given request to the new updated data
   *
   * @param mortuaryRequest
   */
  public void updateRequest(MortuaryRequest mortuaryRequest) throws MortuaryServiceException {
    String statement =
        "update tickets set "
            + "opened = ?, "
            + "closed = ?, "
            + "requestor = ?, "
            + "deceased = ?, "
            + "circumstance = ?,"
            + "description = ?,"
            + "time = ?,"
            + "location = ? "
            + "where ticketID = ?";
    update(statement, mortuaryArgs(mortuaryRequest));
    log.info("Adding reqeuest: " + mortuaryRequest.getId().toString());
  }

  /**
   * Takes in a UUID and removes the matching mortuary request
   *
   * @param ticketID
   */
  public void removeRequest(UUID ticketID) throws MortuaryServiceException {
    String statement = "delete from tickets where ticketID = ?";
    execute(statement, Arrays.asList(ticketID.toString()));
    log.info("Deleting reqeuest: " + ticketID.toString());
  }

  /**
   * Takes in a request object and removes it from the database
   *
   * @param mortuaryRequest
   */
  public void removeRequest(MortuaryRequest mortuaryRequest) throws MortuaryServiceException {
    removeRequest(mortuaryRequest.getId());
  }

  /**
   * Returns a list of all the requests in the database
   *
   * @return
   */
  public List<MortuaryRequest> getRequests() throws MortuaryServiceException {
    String statement = "select * from tickets";
    List<MortuaryRequest> tickets = new ArrayList<MortuaryRequest>();
    for (List<String> ticket : get(statement, Arrays.asList())) {

      LocalDateTime closedTime;
      if (ticket.get(2) == null) {
        closedTime = null;
      } else {
        closedTime =
            LocalDateTime.ofInstant(Timestamp.valueOf(ticket.get(2)).toInstant(), ZoneOffset.UTC);
      }

      tickets.add(
          new MortuaryRequest(
              UUID.fromString(ticket.get(0)),
              LocalDateTime.ofInstant(Timestamp.valueOf(ticket.get(1)).toInstant(), ZoneOffset.UTC),
              closedTime,
              getEmployee(ticket.get(3)).orElse(null),
              getPerson(ticket.get(4)).orElse(null),
              Circumstance.valueOf(ticket.get(5)),
              LocalDateTime.ofInstant(Timestamp.valueOf(ticket.get(7)).toInstant(), ZoneOffset.UTC),
              ticket.get(6),
              ticket.get(8)));
    }

    return tickets;
  }

  /**
   * Returns the request belonging to the given UUID
   *
   * @param id
   * @return
   */
  public Optional<MortuaryRequest> getRequest(UUID id) throws MortuaryServiceException {
    for (MortuaryRequest req : getRequests()) {
      if (req.getId().equals(id)) {
        return Optional.of(req);
      }
    }
    return Optional.empty();
  }

  public Optional<MortuaryRequest> getRequest(Person deceased) throws MortuaryServiceException {
    for (MortuaryRequest req : getRequests()) {
      if (req.getDeceased().equals(deceased)) {
        return Optional.of(req);
      }
    }
    return Optional.empty();
  }

  public Collection<MortuaryRequest> getCreatedBy(Employee employee)
      throws MortuaryServiceException {
    Collection<MortuaryRequest> output = new ArrayList<MortuaryRequest>();
    for (MortuaryRequest req : getRequests()) {
      if (req.getCreator().equals(employee)) {
        output.add(req);
      }
    }
    return output;
  }

  /**
   * Turns a mortuaryserivice into a list of strings
   *
   * @param ticket
   * @return
   */
  private List<String> mortuaryArgs(MortuaryRequest ticket) {
    String closed;
    if (ticket.getClosedTime().isPresent()) {
      if (!(ticket.getClosedTime() == null)) {
        closed = ticket.getClosedTime().get().toString();
      } else {
        closed = null;
      }
    } else {
      closed = null;
    }

    return Arrays.asList(
        ticket.getId().toString(),
        Timestamp.from(
                ticket.getOpenedTime().toInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS))
            .toString(),
        closed,
        ticket.getCreator().getId().toString(),
        ticket.getDeceased().getId().toString(),
        ticket.getCircumstance().toString(),
        ticket.getDescription(),
        Timestamp.from(ticket.getTime().toInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS))
            .toString(),
        ticket.getLocation());
  }
}
