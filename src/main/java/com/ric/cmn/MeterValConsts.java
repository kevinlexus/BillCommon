package com.ric.cmn;

/**
 * Статус загрузки показания из разных источников
 * @version 1.0
 */
public interface MeterValConsts {

    // статусы показаний счетчиков
    int INSERT_FOR_LOAD_TO_GIS=0;
    int IN_PROCESS_FOR_LOAD_TO_GIS=1;
    int LOADED_TO_GIS=2;
    int LOADED_FROM_GIS=3;
    int ERORR_LOAD_TO_GIS=4;

}
