package es.ulpgc.eite.cleancode.advclickcounter.counters;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.advclickcounter.app.AppMediator;
import es.ulpgc.eite.cleancode.advclickcounter.app.ClickToCounterState;
import es.ulpgc.eite.cleancode.advclickcounter.data.ClickData;
import es.ulpgc.eite.cleancode.advclickcounter.data.CounterData;
import es.ulpgc.eite.cleancode.advclickcounter.app.CounterToClickState;

public class CounterListPresenter implements CounterListContract.Presenter {

  public static String TAG = "AdvClickCounter.CounterListPresenter";

  private WeakReference<CounterListContract.View> view;
  private CounterListState state;
  private CounterListContract.Model model;
  private AppMediator mediator;

  public CounterListPresenter(AppMediator mediator) {
    this.mediator = mediator;
    //state = mediator.getCounterListState();
  }

  @Override
  public void onCreateCalled() {
    Log.e(TAG, "onCreateCalled()");

    // initialize the state
    state = new CounterListState();
    // TODO: include code if necessary

    state.counters= model.getStoredCounterList();

  }

  @Override
  public void onRecreateCalled() {
    Log.e(TAG, "onRecreateCalled()");

    // get back the state
    state.counters = mediator.getCounterListState().counters;

    if(state.counters != null){
      for(CounterData counter : state.counters){
        int sum = 0;
        if(counter.clicks != null){
          for(ClickData click : counter.clicks){
            sum += click.value;
          }
        }
        counter.value=sum;
      }
    }
    state.counters=model.getStoredCounterList();

    view.get().onDataUpdated(state);
  }

  @Override
  public void onResumeCalled() {
    Log.e(TAG, "onResumeCalled - Checking received counters");

    // use passed state
    ClickToCounterState savedState = getStateFromNextScreen();
    if (savedState != null) {
      Log.e(TAG, "Received counter from Clicks: ID = " + savedState.counter.id + ", Clicks = "
              + (savedState.counter.clicks != null ? savedState.counter.clicks.size() : 0));
      if (savedState.counter.clicks != null) {
        for (ClickData click : savedState.counter.clicks) {
          Log.e(TAG, "Click Value: " + click.value);
        }
      }
      // update the model
      // TODO: include code if necessary
      model.onDataFromNextScreen(savedState.counter);

      if (state.counters != null) {
        for (CounterData counter : state.counters) {
          if (counter.id.equals(savedState.counter.id)) {
            counter.clicks = savedState.counter.clicks;
            break;
          }
        }
      }
    }

    state.counters=model.getStoredCounterList();

    // call the model and update the state
    // TODO: include code if necessary
    for (CounterData counter : state.counters) {
      int sum = 0;
      if (counter.clicks != null) {
        for (ClickData click : counter.clicks) {
          sum += click.value;
        }
      }
      counter.value = sum;  // Actualiza el valor que se mostrará en la vista

      Log.e(TAG, "Final Counter - ID: " + counter.id + ", Value (Sum): " + counter.value);
    }


    // update the view
    view.get().onDataUpdated(state);

  }

  @Override
  public void onBackPressedCalled() {
    Log.e(TAG, "onBackPressedCalled()");

    // TODO: include code if necessary
  }

  @Override
  public void onPauseCalled() {
    Log.e(TAG, "onPauseCalled()");

    // save the state
    mediator.setCounterListState(state);
  }

  @Override
  public void onDestroyCalled() {
    Log.e(TAG, "onDestroyCalled()");
  }

  @Override
  public void onListPressed(CounterData counter) {
    Log.e(TAG, "onClickListPressed()");

    // TODO: include code if necessary
    CounterToClickState newState = new CounterToClickState(counter);
    passStateToNextScreen(newState);
    view.get().navigateToNextScreen();


  }


  @Override
  public void onCounterButtonPressed() {
    Log.e(TAG, "onCounterButtonPressed()");

    // TODO: include code if necessary
    model.onAddCounter(new CounterData());
    state.counters=model.getStoredCounterList();

    CounterListViewModel viewModel = new CounterListViewModel();
    viewModel.counters=state.counters;
    view.get().onDataUpdated(viewModel);

  }

  private void passStateToNextScreen(CounterToClickState state) {
    mediator.setNextMasterScreenState(state);
  }


  private ClickToCounterState getStateFromNextScreen() {
    return mediator.getNextMasterScreenState();
  }


  @Override
  public void injectView(WeakReference<CounterListContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(CounterListContract.Model model) {
    this.model = model;
  }

}
