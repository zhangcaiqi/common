package com.xingqi.code.commonlib.delegate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ActivityDelegate {


    void onCreate(@Nullable Bundle savedInstanceState);


    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(@NonNull Bundle outState);

    void onDestroy();
}
