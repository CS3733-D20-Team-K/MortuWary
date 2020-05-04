package edu.wpi.cs3733.d20.teamk.mortuary;

import lombok.Getter;

public enum EntryPoint {
  NEW("newRequest.fxml"),
  EDIT("editRequest.fxml"),
  DASHBOARD("dashboard.fxml"),

  NEW_EMPLOYEE("newEmployee.fxml"),
  EDIT_EMPLOYEE("editEmployee.fxml"),
  DASHBOARD_EMPLOYEE("employeeDashboard.fxml");

  @Getter private String fxml;

  EntryPoint(String fxml) {
    this.fxml = fxml;
  }
}
