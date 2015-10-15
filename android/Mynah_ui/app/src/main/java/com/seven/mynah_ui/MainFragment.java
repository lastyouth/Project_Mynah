package com.seven.mynah_ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.brandontate.BTGridPager.BTFragmentGridPager;

import java.util.Random;

/**
 * BTGridPager
 *
 * @author Brandon Tate
 */
public class MainFragment extends Fragment {

    TextView mLabel;
    TextView mLabel2;

    BTFragmentGridPager.GridIndex mGridIndex;


    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_main, container,false);

        mLabel = (TextView) layout.findViewById(R.id.section_label);
        mLabel2 = (TextView) layout.findViewById(R.id.section_label2);

        setTxtRow(mGridIndex);

        Random color = new Random();
        layout.setBackgroundColor(Color.argb(255, color.nextInt(255), color.nextInt(255), color.nextInt(255)));

        //layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        //layout.setLayoutParams(new RelativeLayout.LayoutParams(0,0));
//        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) layout.getLayoutParams();
//        p.width = 300;
//        p.height = 300;
//        layout.setLayoutParams(p);

        return layout;

    }


    public void setTxtRow(BTFragmentGridPager.GridIndex gridIndex) {
        mLabel.setText("(" + gridIndex.getRow() + ", " + gridIndex.getCol() + ")");
    }

    public void setGridIndex(BTFragmentGridPager.GridIndex gridIndex){
        mGridIndex = gridIndex;
    }

}
