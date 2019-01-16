package com.kozden.gpsuygulamasi;

import com.google.android.gms.maps.model.PolylineOptions;


public interface TaskLoadedCallback {
    void onTaskDone(Object... values);
}