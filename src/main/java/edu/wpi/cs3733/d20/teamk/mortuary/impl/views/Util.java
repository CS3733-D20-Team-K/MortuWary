package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import me.xdrop.fuzzywuzzy.FuzzySearch;

public class Util {

  public static boolean filter(String text, String target) {
    if (target.isBlank()) {
      return true;
    }

    int ratio = FuzzySearch.partialRatio(target.toLowerCase(), text.toLowerCase());
    return ratio >= 70;
  }
}
