package edu.wpi.cs3733.d20.teamk.mortuary;

/**
 * Enumeration of death circumstance. {@link #NATURAL} {@link #DISEASE} {@link #ACCIDENT} {@link
 * #COMPLICATIONS} {@link #SUICIDE} {@link #CRIME} {@link #OTHER} {@link #PENDING} {@link #CS3733}
 * {@link #DAKA}
 */
public enum Circumstance {
  /** Death by natural causes, old age or otherwise. */
  NATURAL,
  /** Death by disease, virus, sickness, or similar conditions. */
  DISEASE,
  /** Death by accidental injuries such as car or workplace accidents. */
  ACCIDENT,
  /** Death due to medical complication from surgery or other treatments. */
  COMPLICATIONS,
  /** Death purposefully caused the person themselves, usually due to mental illness. */
  SUICIDE,
  /** Death by violent crime such as gun violence, terrorism, or other forms of murder. */
  CRIME,
  /** Death that does not fit into other categories. */
  OTHER,
  /** Death circumstances unknown or awaiting determination. */
  PENDING,
  /** Death related to or caused by software engineering. */
  CS3733,
  /** Death related to or caused by Chartwell's Pulse on Dining colloquially known as DAKA. */
  DAKA
}
