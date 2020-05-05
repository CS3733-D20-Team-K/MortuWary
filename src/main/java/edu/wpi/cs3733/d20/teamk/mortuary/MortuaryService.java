package edu.wpi.cs3733.d20.teamk.mortuary;

import edu.wpi.cs3733.d20.teamk.mortuary.impl.MortuaryServiceImpl;
import java.sql.Connection;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface MortuaryService {

  static MortuaryService instance() {
    return MortuaryServiceImpl.instance();
  }

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
        EntryPoint.NEW,
        PermissionLevel.ADMIN,
        null);
  }

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

  void setDatabase(Connection connection) throws MortuaryServiceException;

  void addRequest(MortuaryRequest request) throws MortuaryServiceException;

  void updateRequest(MortuaryRequest request) throws MortuaryServiceException;

  void removeRequest(UUID request) throws MortuaryServiceException;

  default void removeRequest(MortuaryRequest request) throws MortuaryServiceException {
    removeRequest(request.getId());
  }

  Collection<Employee> getEmployees() throws MortuaryServiceException;

  void addEmployee(Employee employee) throws MortuaryServiceException;

  void updateEmployee(Employee employee) throws MortuaryServiceException;

  void removeEmployee(String employee) throws MortuaryServiceException;

  default void removeEmployee(Employee employee) throws MortuaryServiceException {
    removeEmployee(employee.getId());
  }

  void setCurrent(Employee employee);

  Employee getCurrent();

  Collection<MortuaryRequest> getRequests() throws MortuaryServiceException;

  Collection<MortuaryRequest> getCreatedBy(Employee employee) throws MortuaryServiceException;

  Optional<MortuaryRequest> getRequest(UUID id) throws MortuaryServiceException;

  Optional<MortuaryRequest> getRequest(Person deceased) throws MortuaryServiceException;
}
