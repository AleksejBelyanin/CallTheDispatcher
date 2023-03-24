package com.example.callthedispatcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class SetCommand extends ImportExport {

    public SetCommand(Context context, String textProgress, int progressValue) {
        super(context, textProgress, progressValue);

    }

    @Override
    public URL getUrl() throws MalformedURLException, UnsupportedEncodingException {

        return new URL("http://194.58.111.229:801/json/get_phone_dispatcher.aspx?db_id=7");
    }

    @Override
    public boolean jsonParse(String strJson) {
        try {
            //toastOKIBI(mContext, strJson.trim());
            callPhone(mContext, strJson.trim());
            ((AppCompatActivity)mContext).finish();
        }
        catch (Exception e){

        }
        return true;
    }
    public static void toastOKIBI(Context context, String text, int lenghtTime) {
        if(text == null) text = "";
        Toast toast = Toast.makeText(context, text.trim(), lenghtTime);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static void toastOKIBI(Context context, String text) {
        toastOKIBI(context, text, Toast.LENGTH_SHORT);
    }
    public static void callPhone(Context context, String phone, int dialogResult) {
        //phone = "8" + phoneToOnlyDigit( phone);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            toastOKIBI(context, "У приложение OKIBI нет прав на осуществление вызовов. Зайдите в настройки телефона и дайте право ");
            return;
        }
        /*if (phone.length() > 11) {
            phone = phone.substring(0, 11) + ";" + phone.substring(11);
        }*/
        Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (dialogResult == -666)
            ((AppCompatActivity) context).startActivityForResult(dialIntent, -1);
        else
            ((AppCompatActivity) context).startActivityForResult(dialIntent, dialogResult);
    }

    public static void callPhone(Context context, String phone) {
        callPhone(context, phone, -666);
    }
    public static String phoneToOnlyDigit(String phone) {
        if(phone == null) phone="";
        String str = "";
        char[] chArray = phone.toCharArray();
        for(int i = 0; chArray.length > i; i++){
            if(Character.isDigit(chArray[i])){
                str += chArray[i];
            }
        }
        phone = str;
        /*phone = phone.replace("/[^\d;]/g", "");
        phone = phone.replace("(", "");
        phone = phone.replace(")", "");
        phone = phone.replace("-", "");
        phone = phone.replace(" ", "");
        phone = phone.replace(",", "");
        phone = phone.replace(";", "");
        phone = phone.replace(".", "");
        phone = phone.replace("+", "");*/
        if (phone.length() == 11)
            return phone.substring(1, 11);
        /*if(phone.length() == 11 && phone.substring(0,1)== "8") {
            phone = phone.substring(1);
        }*/
        return phone;
    }
}


