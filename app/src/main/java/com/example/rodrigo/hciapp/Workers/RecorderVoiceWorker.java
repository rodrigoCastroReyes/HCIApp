package com.example.rodrigo.hciapp.Workers;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by rodrigo on 16/01/16.
 */
public class RecorderVoiceWorker implements Worker {
    private int codeTask;

    public RecorderVoiceWorker(Activity activity, int codeTask){

    }

    @Override
    public void launchTask() {

    }

    @Override
    public void resolveTask(Bundle results) {

    }

    @Override
    public int getCodeTask() {
       return this.codeTask;
    }
}
