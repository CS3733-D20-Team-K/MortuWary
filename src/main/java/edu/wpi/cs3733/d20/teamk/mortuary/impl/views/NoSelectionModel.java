package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

public class NoSelectionModel<T> extends MultipleSelectionModel<T> {

  public static <T> NoSelectionModel<T> emptySelection() {
    return new NoSelectionModel<>();
  }

  @Override
  public ObservableList<Integer> getSelectedIndices() {
    return FXCollections.emptyObservableList();
  }

  @Override
  public ObservableList<T> getSelectedItems() {
    return FXCollections.emptyObservableList();
  }

  @Override
  public void selectIndices(int i, int... ints) {}

  @Override
  public void selectAll() {}

  @Override
  public void clearAndSelect(int i) {}

  @Override
  public void select(int i) {}

  @Override
  public void select(T t) {}

  @Override
  public void clearSelection(int i) {}

  @Override
  public void clearSelection() {}

  @Override
  public boolean isSelected(int i) {
    return false;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public void selectPrevious() {}

  @Override
  public void selectNext() {}

  @Override
  public void selectFirst() {}

  @Override
  public void selectLast() {}
}
