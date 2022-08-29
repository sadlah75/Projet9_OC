package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * Created by Philippe on 21/02/2018.
 * Updated by sadek on 28/07/2022
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.812);
    }

    /**
     * Created by sadek on 28/07/2022
     * Conversion d'un prix d'un bien immobilier (Euros vers Dollars)
     * @param euros
     * @return
     */
    public static int convertEurosToDollars(int euros) {
        return (int)Math.round(euros * 1.02);
    }


    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    public static String getTodayDate(){
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    public static String getFormattedDate(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    public static Boolean isInternetAvailable(Context context){
        /*
            WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            return wifi.isWifiEnabled();
         */

        //We check both wifi and mobile connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        boolean wifiAvailable = false;
        boolean networkMobileAvailable = false;

        if(connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if(networkInfo != null && networkInfo.isConnected()) {
            wifiAvailable = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            networkMobileAvailable = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(wifiAvailable) {
                Toast.makeText(context, "Wifi connection is available", Toast.LENGTH_SHORT).show();
            }
            if(networkMobileAvailable) {
                Toast.makeText(context, "Network Mobile connection is available", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context, "No WIfi or network mobile connection available", Toast.LENGTH_SHORT).show();
        }

        return (wifiAvailable || networkMobileAvailable);
    }


    // Conversion d'un tableau de String vers une String
    public static String arrayToString(List<String> strs) {
        StringBuilder builder = new StringBuilder("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            strs.forEach(s -> builder.append(s+","));
        }
        return builder.toString();
    }


    // Conversion price au format x,xxxx,xxx
    public static String getFormattedPrice(long price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###",new DecimalFormatSymbols(Locale.US));
        return decimalFormat.format(price);
    }
}
