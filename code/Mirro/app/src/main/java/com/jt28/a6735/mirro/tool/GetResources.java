package com.jt28.a6735.mirro.tool;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jt28.a6735.mirro.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by a6735 on 2017/9/8.
 */

public class GetResources {
    public static Bitmap getWeatherIcon(Context context, int resId, float requestW, float requestH) {
        Bitmap bmp;
        int outWdith, outHeight;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        outWdith = options.outWidth;
        outHeight = options.outHeight;
        options.inSampleSize = 1;
        if (outWdith > requestW || outHeight > requestH) {
            int ratioW = Math.round(outWdith / requestW);
            int ratioH = Math.round(outHeight / requestH);
            options.inSampleSize = Math.max(ratioW, ratioH);
        }
        options.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeResource(context.getResources(), resId, options);
        return bmp;
    }

    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if(file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    public static void startRecordDialog(Context context,String path) {
        final Dialog recordIndicator;
        recordIndicator = new Dialog(context, R.style.record_voice_dialog);
        recordIndicator.setContentView(R.layout.dialog_record_bmp);
        recordIndicator.setCanceledOnTouchOutside(false);
        recordIndicator.setCancelable(false);
        ImageView bmp = (ImageView) recordIndicator.findViewById(R.id.dialog_record_bmp_bmp);
        Button close = (Button) recordIndicator.findViewById(R.id.dialog_record_bmp_close);

        bmp.setImageBitmap(getDiskBitmap(path));

        recordIndicator.show();
        //暂停或继续
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordIndicator.dismiss();
            }
        });
    }

    public static int getDaysOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        int days_of_month = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days_of_month;
    }

    public static int getWeekOfYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_of_year = cal.get(Calendar.WEEK_OF_YEAR);
        return week_of_year;
    }

    public static String getWeek(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;
    }

    //求数组最大值，最小值，平均值
    public static int detarr(int arr[]) {
        int sum = 0;
        for(int k = 0; k<arr.length; k++){
            sum+=arr[k];
        }
        return (sum/arr.length);
    }

    public static Bitmap getBitmap(String path) throws IOException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
