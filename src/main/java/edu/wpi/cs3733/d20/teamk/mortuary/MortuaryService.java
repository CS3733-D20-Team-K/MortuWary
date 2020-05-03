package edu.wpi.cs3733.d20.teamk.mortuary;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface MortuaryService {

  default void run(
      int xcoord,
      int ycoord,
      int windowWidth,
      int windowLength,
      String cssPath,
      String destNodeID,
      String originNodeID)
      throws MortuaryServiceException {
    run(xcoord, ycoord, windowWidth, windowLength, cssPath, originNodeID, EntryPoint.NEW, null);
  }

  default void run(int xcoord, int ycoord, int windowWidth, int windowLength, String cssPath, String nodeId, EntryPoint ep) {
    run(xcoord, ycoord, windowWidth, windowLength, cssPath, nodeId, ep);
  }

  void run(int xcoord, int ycoord, int windowWidth, int windowLength, String cssPath, String nodeId, EntryPoint ep, UUID id);

  void addRequest(MortuaryRequest request) throws MortuaryServiceException;

  void updateRequest(MortuaryRequest request) throws MortuaryServiceException;

  void removeRequest(UUID request) throws MortuaryServiceException;

  default void removeRequest(MortuaryRequest request) throws MortuaryServiceException {
    removeRequest(request.getId());
  }

  void addEmployee(Employee employee) throws MortuaryServiceException;

  void removeEmployee(UUID employee) throws MortuaryServiceException;

  default void removeEmployee(Employee employee) throws MortuaryServiceException {
    removeEmployee(employee.getId());
  }

  Collection<MortuaryRequest> getRequests() throws MortuaryServiceException;

  Collection<MortuaryRequest> getCreatedBy(Employee employee) throws MortuaryServiceException;

  Optional<MortuaryRequest> getRequest(UUID id) throws MortuaryServiceException;

  Optional<MortuaryRequest> getRequest(Person deceased) throws MortuaryServiceException;
}
