package service;

import dataAccess.DataAccess;

public class ClearService {
    private final DataAccess dataAccess;
    public ClearService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public void clear(){
        dataAccess.clear();
    }
}
