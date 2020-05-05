package edu.wpi.cs3733.d20.teamk.mortuary.impl;

import edu.wpi.cs3733.d20.teamk.mortuary.Employee;
import edu.wpi.cs3733.d20.teamk.mortuary.EntryPoint;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryRequest;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryService;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryServiceException;
import edu.wpi.cs3733.d20.teamk.mortuary.PermissionLevel;
import edu.wpi.cs3733.d20.teamk.mortuary.Person;
import edu.wpi.cs3733.d20.teamk.mortuary.impl.database.MortuaryDB;
import edu.wpi.cs3733.d20.teamk.mortuary.impl.database.MortuaryDBController;
import edu.wpi.cs3733.d20.teamk.mortuary.impl.views.DashboardController;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import java.sql.Connection;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MortuaryServiceImpl implements MortuaryService {
  private JamEnvironment environment = new JamEnvironment();
  private MortuaryDB db;
  private MortuaryDBController controller;
  private Employee current = Employee.UNKNOWN;

  private MortuaryServiceImpl() {}

  private static class SingletonHelper {
    public static final MortuaryServiceImpl INSTANCE = new MortuaryServiceImpl();
  }

  public static MortuaryServiceImpl instance() {
    return SingletonHelper.INSTANCE;
  }

  @Override
  public void run(
      int xcoord,
      int ycoord,
      int windowWidth,
      int windowLength,
      String cssPath,
      String nodeId,
      EntryPoint ep,
      PermissionLevel permissionLevel,
      String targetId)
      throws MortuaryServiceException {
    checkDb();

    Employee employee = null;
    MortuaryRequest request = null;

    if (targetId != null) {
      try {
        request = getRequest(UUID.fromString(targetId)).orElse(null);
      } catch (IllegalArgumentException e) {
        // IGNORE
      }

      employee = getEmployee(targetId).orElse(null);
    }

    Pair<JamController, Stage> loaded =
        JamController.loadStage(
            DashboardController.class.getResource(ep.getFxml()),
            environment,
            new JamProperties()
                .put("permissions", permissionLevel)
                .put("entry_point", ep)
                .put("employee", employee)
                .put("request", request)
                .put(
                    "css",
                    cssPath == null
                        ? DashboardController.class.getResource("default.css").toExternalForm()
                        : cssPath));

    Stage stage = loaded.getValue();
    if (windowLength >= 0) stage.setHeight(windowLength);
    if (windowWidth >= 0) stage.setWidth(windowWidth);

    if (xcoord >= 0) stage.setX(xcoord);
    if (ycoord >= 0) stage.setY(ycoord);

    stage.show();
  }

  private void checkDb() throws MortuaryServiceException {
    if (this.db == null) {
      this.db = new MortuaryDB();
      this.controller = new MortuaryDBController(this.db.getConnection());

      try {
        this.controller.addEmployee(Employee.UNKNOWN);
      } catch (MortuaryServiceException duplicate) {
        // IGNORE
      }

      try {
        this.controller.addPerson(Person.DEFAULT);
      } catch (MortuaryServiceException duplicate) {
        // IGNORE
      }
    }
  }

  @Override
  public void setDatabase(Connection connection) throws MortuaryServiceException {
    this.db = new MortuaryDB(connection);
    this.controller = new MortuaryDBController(connection);
  }

  @Override
  public void addRequest(MortuaryRequest request) throws MortuaryServiceException {
    checkDb();
    this.controller.addRequest(request);
  }

  @Override
  public void updateRequest(MortuaryRequest request) throws MortuaryServiceException {
    checkDb();
    this.controller.updateRequest(request);
  }

  @Override
  public void removeRequest(UUID request) throws MortuaryServiceException {
    checkDb();
    this.controller.removeRequest(request);
  }

  @Override
  public Collection<Employee> getEmployees() throws MortuaryServiceException {
    checkDb();
    return this.controller.getEmployees();
  }

  @Override
  public void addEmployee(Employee employee) throws MortuaryServiceException {
    checkDb();
    this.controller.addEmployee(employee);
  }

  @Override
  public void updateEmployee(Employee employee) throws MortuaryServiceException {
    checkDb();
    this.controller.updateEmployee(employee);
  }

  @Override
  public void removeEmployee(String employee) throws MortuaryServiceException {
    checkDb();
    this.controller.removeEmployee(employee);
  }

  @Override
  public Optional<Employee> getEmployee(String id) throws MortuaryServiceException {
    checkDb();
    return this.controller.getEmployee(id);
  }

  @Override
  public void setCurrent(Employee employee) {
    this.current = employee;
  }

  @Override
  public Employee getCurrent() {
    return this.current;
  }

  @Override
  public Collection<MortuaryRequest> getRequests() throws MortuaryServiceException {
    checkDb();
    return this.controller.getRequests();
  }

  @Override
  public Collection<MortuaryRequest> getCreatedBy(Employee employee)
      throws MortuaryServiceException {
    checkDb();
    return this.controller.getCreatedBy(employee);
  }

  @Override
  public Optional<MortuaryRequest> getRequest(UUID id) throws MortuaryServiceException {
    checkDb();
    return this.controller.getRequest(id);
  }

  @Override
  public Optional<MortuaryRequest> getRequest(Person deceased) throws MortuaryServiceException {
    checkDb();
    return this.controller.getRequest(deceased);
  }
}
