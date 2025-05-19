package es.ulpgc.eite.cleancode.advclickcounter.counters;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.eite.cleancode.advclickcounter.data.ClickData;
import es.ulpgc.eite.cleancode.advclickcounter.data.CounterData;

public class CounterListModel implements CounterListContract.Model {

  public static String TAG = "AdvClickCounter.CounterListModel";

  private List<CounterData> counters;

  public CounterListModel() {
    counters = new ArrayList<>();
  }


  @Override
  public List<CounterData> getStoredCounterList() {
    // Log.e(TAG, "getStoredCounterList()");
    return counters;
  }

  @Override
  public void onRestartScreen(List<CounterData> counters) {


    // TODO: include code if necessary
    this.counters = counters;

    if(this.counters != null){
      Log.e(TAG, "onRestartScreen - Starting sum for counters.");
      for(CounterData counter : this.counters){
        int sum=0;
        if(counter.clicks != null){
          for(ClickData click : counter.clicks){
            Log.e(TAG, "Summing Click: " + click.value + " for Counter ID: " + counter.id);
            sum += click.value;
          }
        }
        Log.e(TAG, "Total Sum for Counter ID: " + counter.id + " is: " + sum);
        counter.value= sum;
      }
    }
  }

  @Override
  public void onAddCounter(CounterData counter) {

    // TODO: include code if necessary
    if (counter == null) {
      counter = new CounterData();
    }

    counters.add(counter);

  }

  @Override
  public void onDataFromNextScreen(CounterData counter) {
    // Log.e(TAG, "onDataFromNextScreen()");

    // TODO: include code if necessary
    if (counters != null && counter != null) {
      for (CounterData item : counters) {
        if (item.id.equals(counter.id)) {
          item.value = counter.value;
          break;
        }
      }
    }

  }
}
