package com.snow.night.special;

import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    private ListView menuListview;
    private ListView mainListview;
    private Slidmenu slidMenu;
    private ImageView ivHead;
    private Mylinealayout llMylayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuListview = (ListView) findViewById(R.id.menu_listview);
        mainListview = (ListView) findViewById(R.id.main_listview);
        slidMenu = (Slidmenu) findViewById(R.id.slid_menu);
        ivHead = (ImageView) findViewById(R.id.iv_head);
        llMylayout = (Mylinealayout) findViewById(R.id.my_layout);

        menuListview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Constant.NAMES){
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textview = new TextView(getContext());
            textview= (TextView) super.getView(position, convertView, parent);
            textview.setTextColor(Color.WHITE);
            return textview;
        }
    });
        mainListview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Constant.sCheeseStrings)
           );
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
        llMylayout.setSlidMenu(slidMenu);
    }
}
