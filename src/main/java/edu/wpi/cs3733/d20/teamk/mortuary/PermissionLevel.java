package edu.wpi.cs3733.d20.teamk.mortuary;

import lombok.Getter;

public enum PermissionLevel {
  EMPLOYEE(10),
  ADMIN(20);

  @Getter private int level;

  PermissionLevel(int level) {
    this.level = level;
  }
}
