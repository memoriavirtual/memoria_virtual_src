package mobile.memoriavirtual.usp.mvmobile.services.impl;

import mobile.memoriavirtual.usp.mvmobile.manager.VolleyManager;

import mobile.memoriavirtual.usp.mvmobile.services.BaseService;
import mobile.memoriavirtual.usp.mvmobile.Utils.AppConstant;

/**
 * Created by danieleboscolo on 25/07/15.
 */

public abstract class BaseServiceImpl implements BaseService {

    public void cancelAllRequests() {
        VolleyManager.getInstance().cancelAll(AppConstant.TAG_DEFAULT);
        VolleyManager.getInstance().cancelAll(AppConstant.TAG_ENVIAR_BEM_PATRIMONIAL);
    }


}
