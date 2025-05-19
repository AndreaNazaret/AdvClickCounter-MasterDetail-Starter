package es.ulpgc.eite.cleancode.advclickcounter.clicks;

import java.util.ArrayList;

import es.ulpgc.eite.cleancode.advclickcounter.data.ClickData;
import es.ulpgc.eite.cleancode.advclickcounter.data.CounterData;

public class ClickListModel implements ClickListContract.Model {

  public static String TAG = "AdvClickCounter.ClickListModel";

  public CounterData counter;

  /*
  public ClickListModel() {
    counter = new CounterData();
    counter.clicks = new ArrayList<>();
  }
  */

  @Override
  public CounterData getStoredData() {
    // Log.e(TAG, "getStoredCounterList()");
    return counter;
  }

  @Override
  public void onRestartScreen(CounterData counter) {
    // Log.e(TAG, "onRestartScreen()");

    // TODO: include code if necessary
    this.counter = counter;
  }


  @Override
  public void onDataFromPreviousScreen(CounterData counter) {
    // Log.e(TAG, "onDataFromPreviousScreen()");
    this.counter = counter;
  }

  @Override
  public void onAddClick(ClickData click) {

    // TODO: include code if necessary
    if (click == null) {
      counter.clicks = new ArrayList<>();
    }
    counter.clicks.add(click);
  }

  @Override
  public void onUpdateClick(ClickData click) {

    if (counter.clicks != null && click != null) {
      for (ClickData item : counter.clicks) {
        if (item.id.equals(click.id)) {
          item.value++;
          break;
        }
      }

      // TODO: include code if necessary

    }
  }
}