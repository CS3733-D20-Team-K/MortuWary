package edu.wpi.cs3733.d20.teamk.mortuary;

import edu.wpi.cs3733.d20.teamk.mortuary.impl.cert.CertPrinter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@EqualsAndHashCode
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

  public boolean isClosed() {
    return this.closedTime != null;
  }

  public boolean isOpen() {
    return this.closedTime == null;
  }

  public void close() {
    this.closedTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
  }

  public void reopen() {
    this.closedTime = null;
  }

  public Optional<LocalDateTime> getClosedTime() {
    return Optional.ofNullable(this.closedTime);
  }

  public void printCertificate(String hospital) {
    log.info("Printing death certificate for: " + this.deceased);
    log.info("Request: " + this);
    CertPrinter.print(this, hospital);
  }
}
