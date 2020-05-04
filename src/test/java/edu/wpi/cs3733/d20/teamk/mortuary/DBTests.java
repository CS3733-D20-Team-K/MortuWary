/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/
package edu.wpi.cs3733.d20.teamk.mortuary;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.cs3733.d20.teamk.mortuary.impl.database.MortuaryDB;
import edu.wpi.cs3733.d20.teamk.mortuary.impl.database.MortuaryDBController;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBTests {
  MortuaryDB database;
  MortuaryDBController controller;
  Employee globalEmp = new Employee(UUID.randomUUID().toString(), "Classandra", "class");
  Person globalPerson = new Person(UUID.randomUUID().toString(), "Deatrice", "FEMALE", 69);
  MortuaryRequest globalRequest =
      new MortuaryRequest(
          globalEmp,
          globalPerson,
          Circumstance.PENDING,
          LocalDateTime.now(),
          "She just magically died it was crazy",
          "KLABS00501");
  UUID testEmpID;
  UUID testPerID;

  private static void delete(Path directory) throws IOException {
    if (Files.exists(directory)) {
      Files.walkFileTree(
          directory,
          new SimpleFileVisitor<>() { // Copied from stack overflow like a true programmer
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException {
              Files.delete(file);
              return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(java.nio.file.Path dir, IOException exc)
                throws IOException {
              Files.delete(dir);
              return FileVisitResult.CONTINUE;
            }
          });
    }
  }

  @BeforeAll
  private void before() throws IOException {
    delete(Paths.get("MortuaryTestDB"));
    database = new MortuaryDB("MortuaryTestDB");
    controller = new MortuaryDBController(database.getConnection());

    controller.addPerson(globalPerson);
    controller.addEmployee(globalEmp);
    controller.addRequest(globalRequest);
    testEmpID = UUID.randomUUID();
    testPerID = UUID.randomUUID();
    controller.addEmployee(testEmpID.toString(), "potato", "potato");
  }

  @Test
  public void addEmployeeTest() {
    controller.addEmployee("Test Man", "tman");
    controller.addEmployee(UUID.randomUUID().toString(), "Dan Burly", "dburly");
  }

  @Test
  public void addPerson() {
    controller.addPerson(testPerID.toString(), "Ima Dedman", "MALE", 92);
    controller.addPerson("Igot Kildred", "OTHER", 21);
  }

  @Test
  public void getEmployeeTest() throws SQLException {
    List<Employee> emp = controller.getEmployees();
    assertEquals(globalEmp, emp.get(0));

    Employee test = controller.getEmployee(globalEmp.getId()).get();
    assertEquals(test, globalEmp);

    controller.removeEmployee(testEmpID);
    assertEquals(controller.getEmployee(testEmpID.toString()), Optional.empty());
  }

  @Test
  public void getPersonTest() throws SQLException {
    List<Person> emp = controller.getPeople();
    assertEquals(globalPerson, emp.get(0));

    Person test = controller.getPerson(globalPerson.getId()).get();
    assertEquals(test, globalPerson);
  }

  @Test
  public void editRequestTest() throws SQLException {
    globalRequest.setCircumstance(Circumstance.SUICIDE);
    controller.updateRequest(globalRequest);
  }

  @Test
  public void deleteRequestTest() throws SQLException {
    controller.removeRequest(globalRequest);

    controller.addRequest(globalRequest);

    controller.removeRequest(globalRequest.getId());
  }

  @Test
  public void getRequestTest() throws SQLException {
    List<MortuaryRequest> req = controller.getRequests();
    assertEquals(globalRequest, req.get(0));

    MortuaryRequest test1 = controller.getRequest(globalRequest.getId()).get();
    assertEquals(test1, globalRequest);

    MortuaryRequest test2 = controller.getRequest(globalPerson).get();
    assertEquals(test2, globalRequest);

    Collection<MortuaryRequest> test3 = controller.getCreatedBy(globalEmp);
    assertTrue(test3.contains(globalRequest));
  }
}
