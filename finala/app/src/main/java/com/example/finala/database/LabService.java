package com.example.finala.database;

import android.content.Context;
import android.telecom.Call;

import com.example.finala.classes.Lab;
import com.example.finala.network.AsyncTaskRunner;
import com.example.finala.network.Callback;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class LabService {
    private Dao labDao;
    private AsyncTaskRunner asyncTaskRunner;

    public LabService(Context context) {
        labDao = DatabaseManager.getInstance(context).getDao();
    }

    public void getAll(Callback<List<Lab>> callback){
        Callable<List<Lab>> callable = labDao::getall;
        asyncTaskRunner.asyncTask(callable, callback);
    }

    public void insert(Lab lab, Callback<Lab> callback){
        Callable<Lab> callable = () -> {
            if(lab.getId() > 0){
                return null;
            }

            long id = labDao.insert(lab);
            if(id < 0){
                return null;
            }
            lab.setId((int) id);
            return lab;
        };
        asyncTaskRunner.asyncTask(callable, callback);
    }

    public void update(Lab lab, Callback<Lab> callback){
        Callable<Lab> callable = () -> {
            if(lab.getId() <= 0){
                return null;
            }

            int result = labDao.update(lab);
            if(result <= 0){
                return null;
            }
            return lab;
        };
        asyncTaskRunner.asyncTask(callable, callback);
    }

    public void delete(Lab lab, Callback<Lab> callback){
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if(lab.getId() <= 0){
                    return false;
                }

                int count = labDao.delete(lab);
                return count == 1;
            }
        };
    }
}
