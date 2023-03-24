package com.example.callthedispatcher;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by God on 02.11.2016.
 * for The Myka
 */

public class ImportExport extends RxJavaImportExport {

    public ImportExport(Context context, String textProgress, int progressValue){
        super(context,textProgress,progressValue);
    }
    public ImportExport(Context context, String textProgress, int progressValue, SQLiteDatabase sQLiteDatabase){
        this(context,textProgress,progressValue);
        mSqLiteDatabase = sQLiteDatabase;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            if(needShowProgressDialog ) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage(mTextProgress + ". Ждите. Идёт загрузка...");
                progressDialog.setIndeterminate(true);
                progressDialog.show();
            }

        }
        catch (Exception e){

        }

    }

    /*@Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(values[0] == StaticFunction.sERROR_CODE_INTERNET_PROGRESS_BAR) {
            if (this instanceof InsertSharedFile) {
                StaticFunction.showMessage(mContext, "Не удалось получить ссылку для файла");
                return;
            }
        }
        StaticFunction.onProgressUpdateActivityLogo(mContext, mTextProgress, values[0]);
    }*/

    /*@Override
    protected void onCancelled() {
        StaticFunction.closeDialog();
        try {
            if(progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }
        catch (Exception ex){

        }

    }*/



}
