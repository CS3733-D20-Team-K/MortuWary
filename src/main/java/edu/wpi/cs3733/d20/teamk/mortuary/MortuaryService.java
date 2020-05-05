package edu.wpi.cs3733.d20.teamk.mortuary;

import edu.wpi.cs3733.d20.teamk.mortuary.impl.MortuaryServiceImpl;
import java.sql.Connection;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/** Interface for connecting to the Mortuary Service. */
public interface MortuaryService {

  /**
   * Gets the implementation of the mortuary service.
   *
   * @return Instance of mortuary service.
   */
  static MortuaryService instance() {
    return MortuaryServiceImpl.instance();
  }

  /**
   * Runs the API with default parameters.
   *
   * @throws MortuaryServiceException
   */
  default void run() throws MortuaryServiceException {
    run(-1, -1, -1, -1, null, null, EntryPoint.DASHBOARD, PermissionLevel.ADMIN, null);
  }

  /**
   * Runs the API with specified parameters specified.
   *
   * @param xcoord X coordinate of the window.
   * @param ycoord Y coordinate of the window.
   * @param windowWidth Width of the window.
   * @param windowLength Length of the window, otherwise known as the height.
   * @param cssPath Path to style sheet to use in windows.
   * @param destNodeID Unused but required string parameter.
   * @param originNodeID ID of node from where the request was made.
   * @throws MortuaryServiceException
   */
  default void run(
      int xcoord,
      int ycoord,
      int windowWidth,
      int windowLength,
      String cssPath,
      String destNodeID,
      String originNodeID)
      throws MortuaryServiceException {
    run(
        xcoord,
        ycoord,
        windowWidth,
        windowLength,
        cssPath,
        originNodeID,
        EntryPoint.DASHBOARD,
        PermissionLevel.ADMIN,
        null);
  }

  /**
   * Runs the API with specified parameters and entry point.
   *
   * @param xcoord X coordinate of the window.
   * @param ycoord Y coordinate of the window.
   * @param windowWidth Width of the window.
   * @param windowLength Length of the window, otherwise known * as the height.
   * @param cssPath Path to style sheet to use in windows.
   * @param nodeId ID of node from where the request was made.
   * @param ep Entry point for the API.
   * @throws MortuaryServiceException
   */
  default void run(
      int xcoord,
      int ycoord,
      int windowWidth,
      int windowLength,
      String cssPath,
      String nodeId,
      EntryPoint ep)
      throws MortuaryServiceException {
    run(
        xcoord,
        ycoord,
        windowWidth,
        windowLength,
        cssPath,
        nodeId,
        ep,
        PermissionLevel.ADMIN,
        null);
  }

  /**
   * Runs the API with specified parameters, entry point, and permission level.
   *
   * @param xcoord X coordinate of the window.
   * @param ycoord Y coordinate of the window.
   * @param windowWidth Width of the window.
   * @param windowLength Length of the window, otherwise known as the height.
   * @param cssPath Path to style sheet to use in windows.
   * @param nodeId ID of node from where the request was made.
   * @param ep Entry point for the API.
   * @param permissionLevel Permissions for logged in user.
   * @param targetId ID of a target request to open.
   * @throws MortuaryServiceException
   */
  void run(
      int xcoord,
      int ycoord,
      int windowWidth,
      int windowLength,
      String cssPath,
      String nodeId,
      EntryPoint ep,
      PermissionLevel permissionLevel,
      String targetId)
      throws MortuaryServiceException;

  /**
   * Sets the database connection.
   *
   * @param connection SQL Connection to the database.
   * @throws MortuaryServiceException
   */
  void setDatabase(Connection connection) throws MortuaryServiceException;

  /**
   * Adds a new mortuary request to the database.
   *
   * @param request New mortuary request object.
   * @throws MortuaryServiceException
   */
  void addRequest(MortuaryRequest request) throws MortuaryServiceException;

  /**
   * Updates a request already in the database.
   *
   * <p>Replaces a request in the database with the UUID of the given request object with the given
   * request object.
   *
   * @param request Mortuary request object with a UUID already present in the database.
   * @throws MortuaryServiceException
   */
  void updateRequest(MortuaryRequest request) throws MortuaryServiceException;

  /**
   * Removes a mortuary request from the database.
   *
   * @param request UUID of the request to remove.
   * @throws MortuaryServiceException
   */
  void removeRequest(UUID request) throws MortuaryServiceException;

  /**
   * Removes a mortuary request from the database
   *
   * @param request Mortuary request object to remove.
   * @throws MortuaryServiceException
   */
  default void removeRequest(MortuaryRequest request) throws MortuaryServiceException {
    removeRequest(request.getId());
  }

  /**
   * Gets all mortuary employees.
   *
   * @return Collection of employee objects.
   * @throws MortuaryServiceException
   */
  Collection<Employee> getEmployees() throws MortuaryServiceException;

  /**
   * Adds an employee to the database.
   *
   * @param employee Employee to add.
   * @throws MortuaryServiceException
   */
  void addEmployee(Employee employee) throws MortuaryServiceException;

  /**
   * Updates an employee already in the database.
   *
   * <p>Replaces an employee in the database with the ID of the given employee object with the given
   * request object.
   *
   * @param employee Employee object with ID already in the database.
   * @throws MortuaryServiceException
   */
  void updateEmployee(Employee employee) throws MortuaryServiceException;

  /**
   * Removes an employee from the database.
   *
   * @param employee ID of employee to remove.
   * @throws MortuaryServiceException
   */
  void removeEmployee(String employee) throws MortuaryServiceException;

  /**
   * Gets an employee from the database.
   *
   * @param id ID of the employee.
   * @return Employee object with the given ID.
   * @throws MortuaryServiceException
   */
  Optional<Employee> getEmployee(String id) throws MortuaryServiceException;

  /**
   * Removes an employee from the database.
   *
   * @param employee The employee object to remove from the database.
   * @throws MortuaryServiceException
   */
  default void removeEmployee(Employee employee) throws MortuaryServiceException {
    removeEmployee(employee.getId());
  }

  /**
   * Sets the currently logged in employee.
   *
   * @param employee Employee to log in as.
   */
  void setCurrent(Employee employee);

  /**
   * gets the logged in employee.
   *
   * @return Logged in employee.
   */
  Employee getCurrent();

  /**
   * Gets all requests from the database.
   *
   * @return Collection of all mortuary requests.
   * @throws MortuaryServiceException
   */
  Collection<MortuaryRequest> getRequests() throws MortuaryServiceException;

  /**
   * Gets all requests created by a particular employee.
   *
   * @param employee Employee to filter by.
   * @return Collection of requests created by the employee.
   * @throws MortuaryServiceException
   */
  Collection<MortuaryRequest> getCreatedBy(Employee employee) throws MortuaryServiceException;

  /**
   * Gets a request by its UUID.
   *
   * @param id Unique ID string of the request.
   * @return Optional mortuary request.
   * @throws MortuaryServiceException
   */
  Optional<MortuaryRequest> getRequest(UUID id) throws MortuaryServiceException;

  /**
   * Gets a request reporting the death of a particular person.
   *
   * @param deceased The deceased person to filter by.
   * @return Optional mortuary request.
   * @throws MortuaryServiceException
   */
  Optional<MortuaryRequest> getRequest(Person deceased) throws MortuaryServiceException;
}
