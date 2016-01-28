package com.example.rodrigo.hciapp.Workers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rodrigo on 16/01/16.
 */
public class CameraWorker implements Worker {
    private Activity context;
    private String mCurrentPhotoPath;
    private int codeTask;

    public CameraWorker(Activity activity,int codeTask){
       this.context = activity;
       this.codeTask = codeTask;
    }

    public String getPath(){
        return this.mCurrentPhotoPath;
    }

    public void setPath(String p){
        this.mCurrentPhotoPath = p;
    }

    public File createImageFile() throws IOException { //crea un nuevo archivo para guardar la imagen capturada
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName ="JPEG_"+timestamp+"_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /*prefix*/
                ".jpg",        /*suffix*/
                storageDir
        );
        this.mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void addPictureToGallery(String path) {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File file = new File(path);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    @Override
    public void launchTask() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//construye un intent para capturar un fota, cualquier aplicacion que capture imagenes puede resolverlo
        if (takePictureIntent.resolveActivity(this.context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = this.createImageFile();//crea el archivo en donde se guardara la foto
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            } catch (IOException ex) {
                ex.printStackTrace();
                photoFile=null;
                this.setPath(null);
            }
            if (photoFile != null) { // continua solamente si el archivo fue creado exitosamente
                this.context.startActivityForResult(takePictureIntent, this.codeTask);
            }
        }
    }

    @Override
    public void resolveTask(Bundle results) {
        int resultCode = results.getInt("ResultCode");
        if (resultCode == this.context.RESULT_OK) {
            if (this.mCurrentPhotoPath != null) {
                this.addPictureToGallery(mCurrentPhotoPath);
                Toast.makeText(context,"Foto capturada con exito", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getCodeTask() {
        return this.codeTask;
    }

    public String getmCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    public CameraWorker setmCurrentPhotoPath(String mCurrentPhotoPath) {
        this.mCurrentPhotoPath = mCurrentPhotoPath;
        return this;
    }
}
