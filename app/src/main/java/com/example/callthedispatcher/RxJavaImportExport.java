package com.example.callthedispatcher;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaImportExport {

    public LinearLayout mLinearLayout = null;
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    public ProgressDialog progressDialog ;
    public SQLiteDatabase mSqLiteDatabase = null;
    public boolean needShowProgressDialog = true;
    public static boolean error = true;
    public Context mContext;
    public int mProgressValue;
    public String mTextProgress;
    private Date startDate = new Date();
    protected HttpURLConnection setURLConnectionParameters(HttpURLConnection urlConnection){

        urlConnection.setReadTimeout(400000);
        urlConnection.setConnectTimeout(5000);
        return urlConnection;
    }
    protected URL getUrl() throws MalformedURLException, UnsupportedEncodingException {
        return new URL("https://ya.ru/");
    }
    protected String getParameters() throws MalformedURLException, UnsupportedEncodingException {
        return "";
    }
    protected String geRequestMethod() throws MalformedURLException, UnsupportedEncodingException {
        return "GET";
    }

    protected boolean jsonParse(String strJson) throws JSONException, ParseException {
        return false;
    }
    public boolean useNewUrl = true;
    public boolean useCashe = true;
    protected URL getUrlNew(URL url) throws MalformedURLException, UnsupportedEncodingException {
        String text = url.toString();
        if(!text.contains("?"))
            text+="?";
        else
            text+="&";
        text += "db_id=" + Integer.toString(7);

        return new URL(text);
    }
    public void addLogError(String strJson, Exception e){
        String text = null;
        try {
            text = getUrl().toString();
            text+="\r\n"+strJson;
            if(e!= null)
                text+="\r\n"+e.toString();

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

    }
    public RxJavaImportExport(Context context, String textProgress, int progressValue) {
        mContext = context;
        mTextProgress = textProgress;
        mProgressValue = progressValue;
    }
    protected String doInBackground(){

        //publishProgress(mProgressValue);
        // получаем данные с внешнего ресурса
        String resultJson = "";
        try {
            startDate = new Date();
            error = false;

            URL url = useNewUrl? getUrlNew(getUrl()): getUrl();
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection = setURLConnectionParameters(urlConnection);
            urlConnection.setRequestMethod(geRequestMethod());
            urlConnection.connect();
            //LogOKIBI.add(url.toString(), LogOKIBI.Type.Error);
            if(geRequestMethod().equals("POST")){
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getParameters());
                writer.flush();
                writer.close();
                os.close();
            }

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();
        }
        catch (java.net.SocketTimeoutException e){
           return onFailedConnection(e);
        }
        catch (Exception e) {
            return onFailedConnection(e);
        }
        return resultJson;
    }
    protected String onFailedConnection(Exception e){
        error = true;
        e.printStackTrace();
        //publishProgress(StaticFunction.sERROR_CODE_INTERNET_PROGRESS_BAR);
        //this.cancel(false);

        return "";
    }

    protected void onPreExecute() {
        try {
            if(needShowProgressDialog ) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage(mTextProgress + ". Ждите. Идёт загрузка...");
                progressDialog.setIndeterminate(true);


            }
            //StaticFunction.onProgressUpdateActivityLogo(mContext, mTextProgress, mProgressValue);
        }
        catch (Exception e){

        }

    }
    public void execute(){
        onPreExecute();
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(progressDialog != null)
                    progressDialog.show();
                return doInBackground();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> {
                    if(progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String strJson) {
                        onPostExecute(strJson);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onCancelled();
                        if(progressDialog != null)
                            progressDialog.dismiss();
                        progressDialog = null;
                    }

                    @Override
                    public void onComplete() {
                        if(progressDialog != null)
                            progressDialog.dismiss();
                        progressDialog = null;
                    }
                });
                /*.subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String integer) {
                        StaticFunction.toastOKIBI(mContext, integer);
                    }

                });*/

    }

    protected void onPostExecute(String strJson) {

        JSONObject dataJsonObj = null;
        String secondName = "";
        try {
            if(jsonParse(strJson)) {

            }
            else{

            }
        } catch (JSONException e) {
            error = true;
            e.printStackTrace();

        } catch (ParseException e) {
            error = true;
            e.printStackTrace();
        }

        //StaticFunction.closeDialog();

    }
    protected void onCancelled() {
        try {
            if(progressDialog != null)
                progressDialog.dismiss();
            progressDialog = null;
        }
        catch (Exception ex){

        }

    }
    protected void onProgressUpdate(Integer... values) {

    }
}
