package com.hannesdorfmann.mosby.sample.mail.label;

import android.os.Parcel;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.CastedArrayListLceViewState;
import com.hannesdorfmann.mosby.sample.mail.model.mail.Label;
import java.util.List;

/**
 * @author Hannes Dorfmann
 */
public class LabelViewState extends CastedArrayListLceViewState<List<Label>, LabelView> {

  public static final Creator<LabelViewState> CREATOR = new Creator<LabelViewState>() {
    @Override public LabelViewState createFromParcel(Parcel source) {
      return new LabelViewState(source);
    }

    @Override public LabelViewState[] newArray(int size) {
      return new LabelViewState[size];
    }
  };



  private final int STATE_SHOWING_LABEL = 3;

  public LabelViewState(){
  }

  protected LabelViewState(Parcel source){
    super(source);
  }

  public void setStateShowingLabel() {
    currentViewState = STATE_SHOWING_LABEL;
  }

  @Override public void apply(LabelView view, boolean retained) {

    if (currentViewState == STATE_SHOWING_LABEL) {
      view.showLabel();
    } else {
      super.apply(view, retained);
    }
  }
}
