package edu.wpi.cs3733.d20.teamk.mortuary;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
  private UUID id;
  private String name;
  private String username;

  public Employee(String name, String username) {
    this(UUID.randomUUID(), name, username);
  }

  public Employee(String name) {
    this(name, name);
  }
}
