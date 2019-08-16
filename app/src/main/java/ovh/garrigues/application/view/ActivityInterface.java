package ovh.garrigues.application.view;

import ovh.garrigues.application.adapter.ActivityRequest;
import ovh.garrigues.application.request.Request;

public interface ActivityInterface {
     ActivityRequest getInstance();
     void changeActiError(Request.EditTypeQuestion typeRequest);
     void changeActiSucess(Request.EditTypeQuestion typeRequest);
     //void changeActiSucessPost();
}
