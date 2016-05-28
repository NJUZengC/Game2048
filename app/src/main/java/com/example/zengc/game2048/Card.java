package com.example.zengc.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by ZengC on 2016/5/27.
 */
public class Card extends FrameLayout {
    public Card(Context context){
        super(context);
        label = new TextView(getContext());
        label.setTextSize(32);
        label.setGravity(Gravity.CENTER);
        label.setBackgroundColor(0x33ffffff);
        LayoutParams layoutParams  = new LayoutParams(-1,-1);
        layoutParams.setMargins(10,10,0,0);
        addView(label,layoutParams);



    }
    private int num = 0;
    private TextView label=null;
    public void setNum(int num){
        this.num = num;
        if(num>0)
        label.setText(num+"");
        else
            label.setText("");

    }
    public int getNum(){
        return  num;
    }

    @Override
    public boolean equals(Object o) {
        return getNum()==(((Card)(o)).getNum());
    }
}
