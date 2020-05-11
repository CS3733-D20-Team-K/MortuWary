package edu.wpi.cs3733.d20.teamk.mortuary;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Mortuary Request represents a request to the mortuary.
 *
 * <p>Mortuary requests are for a specific deceased person and contains details about their death
 * along with the employee who created the request.
 */
@AllArgsConstructor
@Slf4j
@ToString
public class MortuaryRequest {
  @Getter @Setter private UUID id;

  @Getter @Setter private LocalDateTime openedTime;
  private LocalDateTime closedTime;

  @Getter @Setter private Employee creator;
  @Getter @Setter private Person deceased;

  @Getter @Setter private Circumstance circumstance;
  @Getter @Setter private LocalDateTime time;
  @Getter @Setter private String description;
  @Getter @Setter private String location;

  /**
   * Instantiates a mortuary request.
   *
   * @param creator Employee that created the request.
   * @param deceased The deceased person.
   * @param circumstance The circumstance of the death.
   * @param time Time of death.
   * @param description Description of the person's death.
   * @param location Location of death.
   */
  public MortuaryRequest(
      Employee creator,
      Person deceased,
      Circumstance circumstance,
      LocalDateTime time,
      String description,
      String location) {
    this.id = UUID.randomUUID();
    this.openedTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    this.closedTime = null;

    this.creator = creator;
    this.deceased = deceased;
    this.circumstance = circumstance;
    this.time = time.truncatedTo(ChronoUnit.SECONDS);
    this.description = description;
    this.location = location;
  }

  /**
   * Checks if a request is closed.
   *
   * @return True if closed.
   */
  public boolean isClosed() {
    return this.closedTime != null;
  }

  /**
   * Checks if a request is open.
   *
   * @return True if open.
   */
  public boolean isOpen() {
    return this.closedTime == null;
  }

  /**
   * Closes the ticket.
   *
   * <p>Sets the closed time to the current time.
   */
  public void close() {
    this.closedTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
  }

  /** Reopens a ticket. */
  public void reopen() {
    this.closedTime = null;
  }

  /**
   * Gets the closed time if the ticket is closed.
   *
   * @return Optional close time.
   */
  public Optional<LocalDateTime> getClosedTime() {
    return Optional.ofNullable(this.closedTime);
  }

  /**
   * Determines the equality of requests.
   *
   * @param object Object to compare
   * @return True if the object is equivalent to the request.
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof MortuaryRequest)) return false;
    MortuaryRequest request = (MortuaryRequest) object;
    return Objects.equals(id, request.id);
  }

  /**
   * Generates a hashcode for the request.
   *
   * @return Hashcode.
   */
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
