package com.ads.restaurantetectoy.modulos;

import com.ads.restaurantetectoy.model.CadastroActivity;
import com.pax.dal.ISys;

public class SysTester {
    private static SysTester sysTester;
    private ISys iSys = null;

    private SysTester(){iSys = CadastroActivity.getDal().getSys();}
    public static SysTester getInstance(){
        if(sysTester == null){
            sysTester = new SysTester();
        }
        return sysTester;
    }

    public void enableNavigationBar(boolean enable){
        iSys.enableNavigationBar(enable);
    }

}
