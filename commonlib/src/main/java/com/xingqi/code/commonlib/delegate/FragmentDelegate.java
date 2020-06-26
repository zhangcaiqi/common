package com.xingqi.code.commonlib.delegate;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface FragmentDelegate {

    void onCreate(@Nullable Bundle savedInstanceState);

    void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState);

    void onActivityCreated(@Nullable Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onSaveInstanceState(@NonNull Bundle outState);

    void onPause();

    void onStop();

    void onDestroyView();

    void onDestroy();

    void onDetach();
}
