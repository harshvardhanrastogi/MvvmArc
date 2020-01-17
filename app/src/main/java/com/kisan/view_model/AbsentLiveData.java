package com.kisan.view_model;

import androidx.lifecycle.LiveData;

public class AbsentLiveData extends LiveData {
    private AbsentLiveData(){}
    public static AbsentLiveData create() {
        return new AbsentLiveData();
    }
}
