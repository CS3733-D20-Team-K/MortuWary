package edu.wpi.cs3733.d20.teamk.mortuary;

import lombok.Getter;

/**
 *  Permission enum that defines what functions of the API
 *  the user has access to.
 *  <li>{@link #EMPLOYEE}</li>
 *  <li>{@link #ADMIN}</li>
 */
public enum PermissionLevel {
  /**
   * Basic permissions to create and take requests.
   */
  EMPLOYEE(10),
  /**
   * Allows editing of employees and requests.
   */
  ADMIN(20);

  @Getter private int level;

  /**
   * Constructs a permission level.
   * @param level Permission level integer.
   */
  PermissionLevel(int level) {
    this.level = level;
  }
}
