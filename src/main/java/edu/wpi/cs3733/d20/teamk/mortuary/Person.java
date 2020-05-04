package edu.wpi.cs3733.d20.teamk.mortuary;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
  private String id;
  private String name;
  private String gender;
  private int age;

  public Person(String name, String gender, int age) {
    this(UUID.randomUUID().toString(), name, gender, age);
  }
}
