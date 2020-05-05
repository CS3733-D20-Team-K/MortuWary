package edu.wpi.cs3733.d20.teamk.mortuary;

import lombok.Getter;

/**
 * Enumeration of possible entry points for the application. {@link #NEW} {@link #EDIT} {@link
 * #DASHBOARD} {@link #NEW_EMPLOYEE} {@link #EDIT_EMPLOYEE} {@link #DASHBOARD_EMPLOYEE}
 */
public enum EntryPoint {
  /** Stand-alone mortuary request creation page. */
  NEW("newRequest.fxml"),
  /** Stand-alone mortuary request editing page. */
  EDIT("editRequest.fxml"),
  /**
   * Main mortuary dashboard for viewing, creating, and editing requests. Contains filters for
   * searching and administrative functions.
   */
  DASHBOARD("dashboard.fxml"),

  /** Stand-alone employee creation page. */
  NEW_EMPLOYEE("newEmployee.fxml"),
  /** Stand-alone employee editing page. */
  EDIT_EMPLOYEE("editEmployee.fxml"),
  /**
   * Main mortuary dashboard for viewing, creating, and editing employees. Contains filters for
   * searching and administrative functions.
   */
  DASHBOARD_EMPLOYEE("employeeDashboard.fxml");

  @Getter private String fxml;

  /**
   * Instantiates an entry point.
   *
   * @param fxml FXML document to load on entry.
   */
  EntryPoint(String fxml) {
    this.fxml = fxml;
  }
}
