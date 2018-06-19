package com.dr.betadapurrakyat.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.dr.betadapurrakyat.MainActivity;
import com.dr.betadapurrakyat.NetworkActivity;
import com.dr.betadapurrakyat.R;
import com.dr.betadapurrakyat.PromoActivity;

/**
 * Created by ASUS on 1/28/2018.
 */

public class BottomNavSetting {


    public static void bNavListener (final Context context, final BottomNavigationView bottomNavigationView){

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        context.startActivity(new Intent(context,MainActivity.class));

                        break;
                    case R.id.navigation_newRelease:
                        context.startActivity(new Intent(context,PromoActivity.class));

                        break;
                    case R.id.navigation_network:
                        context.startActivity(new Intent(context,NetworkActivity.class));

                        break;
                }
                return false;
            }

        });
    }
}

