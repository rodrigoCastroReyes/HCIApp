package com.example.rodrigo.hciapp.Workers;

import android.os.Bundle;

/**
 * Created by rodrigo on 16/01/16.
 *
 */
public interface Worker {
    public void launchTask();//lanzar un tarea que será resuelva por otra aplicacion de sistema operativo Android
    public void resolveTask(Bundle result);//funcion que se ejecuta cuando la tarea lanzada se ha completado
    public int getCodeTask();
}
