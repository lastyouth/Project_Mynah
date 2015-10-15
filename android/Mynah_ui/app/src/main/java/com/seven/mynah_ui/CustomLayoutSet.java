package com.seven.mynah_ui;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomLayoutSet extends RelativeLayout {

    private int width;
    private int height;
    private int size;
    private int real_height;
    private int margin_height;

    private boolean isMoving = false;


    private ArrayList<RelativeLayout> f_list;
    private ArrayList<Animation> ani_up_list;
    private ArrayList<Animation> ani_down_list;


    LinearLayout llSchedule, llBus, llSubway, llWeather, llRecord, llRefresh, llSetting, llProgressbar, llPlaying;

    //for Schedule
    private static int maxSchedules = 10;
    private static int maxPreparations = 10;
    private LinearLayout layoutSchedule;
    private LinearLayout layoutPreparation;
    private TextView tvSchedules[];
    private TextView tvPreparation;
    //private CalendarManager calendarManager;
    //private ArrayList<ScheduleInfo> scheduleInfo;
    private String today;

    //for Bus
    private ImageView ivBusImage;
    private TextView tvBusRoute;
    private TextView tvBusStopName;
    private TextView tvBusDirName;
    private TextView tvBusNextTime;
    private TextView tvBusNextTime2;
    private TextView tvCurrentBusRoute;
    //private ArrayList<BusInfo> busArrayList;
    private String bRoute;
    private String bDir;
    private String bStation;
    private String time1;
    private String time2;

    //for Subway
    private ImageView ivSubwayImage;
    private TextView tvSubwayName;
    private TextView tvSubwayStopName;
    private TextView tvSubwayDirName;
    private TextView tvSubwayNextTime;
    private TextView tvSubwayDirName2;
    private TextView tvSubwayNextTime2;
    //private ArrayList<SubwayInfo> subwayArrayList;


    //for Weather
    private ImageView ivWeatherImage;
    private TextView tvWeatherType;
    private TextView tvPlace;
    private TextView tvPlace2;
    private TextView tvTemper;
    private TextView tvReh; // 습도
    private TextView tvPop; // 강수확률
    private TextView tvUpdateTime;
    private TextView tvHour;
    //private ArrayList<WeatherLocationInfo> weatherArrayList;


    //for record
    private TextView tvRecord;
    private ImageView ivRecord;


    //for Playing
    private TextView tvPlaying;
    private TextView tvPlayingText;
    private ImageView ivPlaying;


    private int now_index;


    long animationDuration = 800;

    private Interpolator interpolator = new LinearInterpolator();

    public CustomLayoutSet(final Context context, final int size) {

        super(context);

        now_index = 0;
        this.size = size;

        f_list = new ArrayList<RelativeLayout>();
        ani_up_list = new ArrayList<Animation>();
        ani_down_list = new ArrayList<Animation>();



        for(int i=0;i<size;i++)
        {
            RelativeLayout f = new RelativeLayout(context);
            f.setTag(i);
            f_list.add(f);
        }

        post(new Runnable() {
            @Override
            public void run() {
                width = getWidth();
                height = getHeight();
                margin_height = (int) (height / (size -1 + 3));
                real_height = margin_height * 3;
                initWithDimentions(context);
                initClickListener();

            }
        });
    }

    private void initClickListener()
    {
        for(int i=0;i<size;i++)
        {
            f_list.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showView((int)v.getTag());
                }
            });
        }
    }


    private void initWithDimentions(Context context) {

        f_list.get(0).setBackgroundColor(Color.RED);
        f_list.get(1).setBackgroundColor(Color.GREEN);
        f_list.get(2).setBackgroundColor(Color.YELLOW);
        f_list.get(3).setBackgroundColor(Color.DKGRAY);
        f_list.get(4).setBackgroundColor(Color.GRAY);


        RelativeLayout f = new RelativeLayout(context);
        View v = new View(context);
        v = inflate(context,R.layout.fragment_main,f);

        for(int i =0;i<size;i++)
        {
            f_list.get(i).setId(i);
        }


        RelativeLayout container = new RelativeLayout(context);

        //FrameLayout container = new FrameLayout(context);

        LayoutParams containerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //FrameLayout.LayoutParams containerParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        container.setLayoutParams(containerParams);

        /*give enough space so that fl3 don't get resized*/
        containerParams.setMargins(0, 0, 0, 0);

        container.addView(f_list.get(0));
        container.addView(f_list.get(1));
        container.addView(f_list.get(2));
        container.addView(f_list.get(3));
        container.addView(f_list.get(4));

        LayoutParams params0 = new LayoutParams(LayoutParams.MATCH_PARENT, real_height);
        LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, real_height);
        LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT, real_height);
        LayoutParams params3 = new LayoutParams(LayoutParams.MATCH_PARENT, real_height);
        LayoutParams params4 = new LayoutParams(LayoutParams.MATCH_PARENT, real_height);

        params0.setMargins(0, 0, 0, 0);
        //params0.gravity = Gravity.NO_GRAVITY;
        f_list.get(0).setLayoutParams(params0);

        //params1.addRule(RelativeLayout.BELOW, 0);

        params1.setMargins(0, margin_height * 3, 0, 0);
        //params1.gravity = Gravity.NO_GRAVITY;

        f_list.get(1).setLayoutParams(params1);


        //params2.addRule(RelativeLayout.BELOW, 1);

        params2.setMargins(0, margin_height * 4, 0, 0);
       //params2.gravity = Gravity.NO_GRAVITY;
        f_list.get(2).setLayoutParams(params2);


        //params3.addRule(RelativeLayout.BELOW, 2);

        params3.setMargins(0, margin_height * 5, 0, 0);
        //params3.gravity = Gravity.NO_GRAVITY;
        f_list.get(3).setLayoutParams(params3);


        //params4.addRule(RelativeLayout.BELOW, 3);

        params4.setMargins(0,margin_height * 6, 0, 0);
        //params4.gravity = Gravity.NO_GRAVITY;
        f_list.get(4).setLayoutParams(params4);

        addView(container);

    }


    private void scrollUp(int index)
    {
        Animation moveAnimation = new MyTranslateAnimation(f_list.get(index),0,0,f_list.get(index).getY() ,f_list.get(index).getY() - margin_height * 2);
        moveAnimation.setInterpolator(interpolator);
        moveAnimation.setDuration(animationDuration);
        moveAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isMoving = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isMoving = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        f_list.get(index).startAnimation(moveAnimation);
    }

    private void scrollDown(int index)
    {
        Animation moveAnimation = new MyTranslateAnimation(f_list.get(index),0,0,f_list.get(index).getY(), f_list.get(index).getY() + margin_height * 2);
        moveAnimation.setInterpolator(interpolator);
        moveAnimation.setDuration(animationDuration);
        moveAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isMoving = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isMoving = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        f_list.get(index).startAnimation(moveAnimation);
    }

    private void showView(int index)
    {

        if(isMoving==true) return;
        if(index == now_index) return;

        if(index > now_index)
        {
            for(int i=now_index+1;i<=index;i++)
            {
                if(i != 0 ) scrollUp(i);
            }
        }
        else if (index < now_index)
        {
            for(int i=index+1 ; i<=now_index; i++ )
            {
                if( i != 0 ) scrollDown(i);
            }
        }
        now_index = index;
    }

    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    public void setInterpolator(Interpolator i) {
        interpolator = i;
    }

    public void startAnimation(int index) {
        showView(index);
    }

    public RelativeLayout getFrameLayout(int index) { return f_list.get(index);}


}
