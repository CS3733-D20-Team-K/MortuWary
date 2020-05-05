package edu.wpi.cs3733.d20.teamk.mortuary;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a mortuary employee.
 *
 * <p>Employees can create requests and be assigned to them.
 */
@Data
@AllArgsConstructor
public class Employee {
  public static final Employee UNKNOWN = new Employee("unknown", "Unknown", "Unknown");

  private String id;
  private String name;
  private String username;

  /**
   * Instantiates an employee
   *
   * @param name Name of employee.
   * @param username Username of employee.
   */
  public Employee(String name, String username) {
    this(UUID.randomUUID().toString(), name, username);
  }

  /**
   * Instantiates an employee without a username.
   *
   * @param name Name of employee.
   */
  public Employee(String name) {
    this(name, name);
  }
}
