/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/
package edu.wpi.cs3733.d20.teamk.mortuary;

import edu.wpi.cs3733.d20.teamk.mortuary.database.MortuaryDB;
import edu.wpi.cs3733.d20.teamk.mortuary.database.MortuaryDBController;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBTests {
  MortuaryDB database;
  MortuaryDBController controller;

  @BeforeAll
  private void before() {
    database = new MortuaryDB("MortuaryTestDB");
    controller = new MortuaryDBController(database.getConnection());
  }

  @Test
  public void addEmployeeTest() {

    controller.addEmployee(UUID.randomUUID(), "Dan Burly", "dburly");
    controller.addEmployee("Test Man", "tman");
  }
}
