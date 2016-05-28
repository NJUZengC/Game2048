package com.example.zengc.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZengC on 2016/5/27.
 */
public class GameView extends GridLayout {
    private Card[][]cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();
    private GestureDetector detector;

    public  GameView(Context context)
    {
        super(context);
        initGameView();

    }

    public GameView(Context context, AttributeSet attrs)  {
        super(context, attrs);
        initGameView();
    }   //Constructor that is called when inflating a view from XML
    public  GameView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        initGameView();
    }

    public void initGameView(){
       setColumnCount(4);
        setBackgroundColor(0xffbbada0);

        setOnTouchListener(new OnTouchListener() {
            //如何判断用户的意图？判断用户按下的意图和判断用户手指离开的意图
            private float startX,startY,offsetX,offsetY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX()-startX;
                        offsetY = event.getY()-startY;
                        //防止用户不是左右直线手势滑动而是斜方向滑动的判断代码
                        if(Math.abs(offsetX)>(Math.abs(offsetY))){//表示手势往右滑动不太偏离水平的时候
                            if(offsetX<-5){//手势往左,-5表示范围
                               // System.out.println("left操作");
                                swipeLeft();//侦听用户操作后执行该方法
                               // addRandomNum();
                            }else if (offsetX>5){//往右
                                //System.out.println("rigth操作");
                                swipRight();//侦听用户操作后执行该方法
                                //addRandomNum();
                            }
                        }else{//这个else判断用户的手势上下滑动不太偏离垂直线
                            if(offsetY<-5){//手势往左,-5表示范围
                               // System.out.println("up操作");
                                SwipUp();//侦听用户操作后执行该方法
                               // addRandomNum();
                            }else if (offsetY>5){//往右
                               // System.out.println("down操作");
                                SwipDown();//侦听用户操作后执行该方法
                                //addRandomNum();
                            }
                        }
                        break;
                }

                return true;
            }
        });

   }




    public void startGame()
    {
        MainActivity.getMainActivity().clearScore();
        for(int j=0;j<4;j++) {
            for (int i = 0; i < 4; i++) {
                cardsMap[i][j].setNum(0);


            }
        }
        addRandomNum();
        addRandomNum();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardwidth = (Math.min(w,h)-10)/4;

        addCards(cardwidth,cardwidth);
        System.out.println("what thw fuck");
        startGame();

    }

    private int finished = 0;
    private void addCards(int cardwidth,int cardheight){

        if(finished==0) {
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    Card card = new Card(getContext());
                    card.setNum(0);
                    addView(card, cardwidth, cardheight);
                    cardsMap[j][i] = card;
                }
            }
            finished = 1;
        }


    }

    private void addRandomNum()
    {
        emptyPoints.clear();
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                if(cardsMap[i][j].getNum()<=0)
                emptyPoints.add(new Point(i,j));
            }
        }
        Point p=emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
        //System.out.println(p.x+"----"+p.y);
    }

    private void swipeLeft()
    {
        boolean merge = false;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++)
            {
                for(int k=j+1;k<4;k++){
                    if(cardsMap[i][k].getNum()>0){
                        if(cardsMap[i][j].getNum()<=0){
                            cardsMap[i][j].setNum(cardsMap[i][k].getNum());
                            cardsMap[i][k].setNum(0);
                            j--;
                            merge = true;
                            break;
                        }
                        else if(cardsMap[i][j].equals(cardsMap[i][k]))
                        {
                            cardsMap[i][j].setNum(cardsMap[i][j].getNum()*2);
                            cardsMap[i][k].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[i][j].getNum());
                            merge = true;
                             break;
                        }
                        break;
                    }
                }
            }
        }
        if(merge) {
            addRandomNum();
            checkComplete();
        }
    }

    private void swipRight()
    {
        boolean merge = false;
        for(int i=0;i<4;i++){
            for(int j=3;j>=0;j--)
            {
                for(int k=j-1;k>=0;k--){
                    if(cardsMap[i][k].getNum()>0){
                        if(cardsMap[i][j].getNum()<=0){
                            cardsMap[i][j].setNum(cardsMap[i][k].getNum());
                            cardsMap[i][k].setNum(0);
                            j++;
                            merge = true;

                            break;
                        }
                        else if(cardsMap[i][j].equals(cardsMap[i][k]))
                        {
                            cardsMap[i][j].setNum(cardsMap[i][j].getNum()*2);
                            cardsMap[i][k].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[i][j].getNum());
                            merge = true;

                            break;
                        }
                        break;
                    }
                }
            }
        }
        if(merge) {
            addRandomNum();
            checkComplete();
        }
    }


    private void SwipUp()
    {
        boolean merge = false;
        for(int j=0;j<4;j++){
            for(int i=0;i<4;i++)
            {
                for(int k=i+1;k<4;k++){
                    if(cardsMap[k][j].getNum()>0){
                        if(cardsMap[i][j].getNum()<=0){
                            cardsMap[i][j].setNum(cardsMap[k][j].getNum());
                            cardsMap[k][j].setNum(0);
                            i--;
                            merge = true;

                            break;
                        }
                        else if(cardsMap[i][j].equals(cardsMap[k][j]))
                        {
                            cardsMap[i][j].setNum(cardsMap[i][j].getNum()*2);
                            cardsMap[k][j].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[i][j].getNum());
                            merge = true;

                            break;
                        }
                        break;
                    }
                }
            }
        }
        if(merge) {
            addRandomNum();
            checkComplete();
        }
    }

    private void SwipDown()
    {
        boolean merge = false;
        for(int j=0;j<4;j++){
            for(int i=3;i>=0;i--)
            {
                for(int k=i-1;k>=0;k--){
                    if(cardsMap[k][j].getNum()>0){
                        if(cardsMap[i][j].getNum()<=0){
                            cardsMap[i][j].setNum(cardsMap[k][j].getNum());
                            cardsMap[k][j].setNum(0);
                            i++;
                            merge = true;

                            break;
                        }
                        else if(cardsMap[i][j].equals(cardsMap[k][j]))
                        {
                            cardsMap[i][j].setNum(cardsMap[i][j].getNum()*2);
                            cardsMap[k][j].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[i][j].getNum());
                            merge = true;

                            break;
                        }
                        break;
                    }
                }
            }
        }
        if(merge) {
            addRandomNum();
            checkComplete();
        }
    }


    public void checkComplete(){
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                if (cardsMap[i][j].getNum() <= 0 ||
                        (j>0&&cardsMap[i][j].equals(cardsMap[i][j-1]))||
                        (j<3&&cardsMap[i][j].equals(cardsMap[i][j+1]))||
                        (i>0&&cardsMap[i][j].equals(cardsMap[i-1][j]))||
                        (i<3&&cardsMap[i][j].equals(cardsMap[i+1][j]))) {
                    return;
                }
            }

        }
        new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startGame();
            }
        }).show();
    }
}
