package com.snow.night.special;

import android.app.Activity;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    private ListView menuListview;
    private ListView mainListview;
    private Slidmenu slidMenu;
    private ImageView ivHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuListview = (ListView) findViewById(R.id.menu_listview);
        mainListview = (ListView) findViewById(R.id.main_listview);
        slidMenu = (Slidmenu) findViewById(R.id.slid_menu);
        ivHead = (ImageView) findViewById(R.id.iv_head);


        menuListview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Constant.NAMES));
        mainListview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Constant.sCheeseStrings) );
        slidMenu.setOnStateChangeListener(new Slidmenu.OnStateChangeListener() {
            @Override
            public void onOpen() {
                Random random = new Random();
                menuListview.smoothScrollToPosition(random.nextInt(Constant.NAMES.length));
            }

            @Override
            public void onClose() {
                com.nineoldandroids.view.ViewPropertyAnimator.animate(ivHead)
                        .translationX(25)
                        .setInterpolator(new CycleInterpolator(20))
                        .setDuration(350)
                        .start();
            }

            @Override
            public void onDraging() {

            }
        });
    }
}
