package ovh.garrigues.application.view;

import ovh.garrigues.application.adapter.ActivityRequest;

public interface ActivityInterface {
     ActivityRequest getInstance();
     void changeActiError();
     void changeActiSucess();
     void changeActiSucessPost();
}
