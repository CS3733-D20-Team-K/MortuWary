package edu.wpi.cs3733.d20.teamk.mortuary;

public class MortuaryServiceException extends Exception {

  public MortuaryServiceException() {}

  public MortuaryServiceException(String message) {
    super(message);
  }

  public MortuaryServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public MortuaryServiceException(Throwable cause) {
    super(cause);
  }

  public MortuaryServiceException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
