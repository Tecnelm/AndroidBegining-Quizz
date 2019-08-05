package ovh.garrigues.application.view;

import android.app.Activity;
import android.content.Context;

import ovh.garrigues.application.adapter.activityRequest;

public interface activityInterface {
     activityRequest getInstance();
     void changeActiError();
     void changeActiSucess();
}
