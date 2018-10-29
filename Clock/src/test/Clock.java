package test;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
        /*
                *表盘时钟
         */

public class Clock extends JApplet implements Runnable {
    private int lastXh,lastYh,lastXm,lastYm,lastXs,lastYs;//表针的位置
    private SimpleDateFormat sdf;                               //日期格式
    private Thread timer=null;
    private NewPanel panle=new NewPanel();

    public void init(){
        lastXs=lastYs=lastXm=lastYm=lastXh=lastYh=0;
        setBackground(Color.white);                             //窗口背景
        add(panle);
        sdf=new SimpleDateFormat("yyyy年MM月dd日 a hh时mm分ss秒 EEEEE");//设定文字日期的显示格式
    }

    private class NewPanel extends JPanel{          //定义JPanel的子类显示时钟
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            int xh,yh,xm,ym,xs,ys,s,m,h,xCenter,yYenter;
            xCenter=getWidth()/2;                       //表盘位置居中
            yYenter=80;                                  //高度为80
            g.setColor(Color.BLACK);                    //设置表盘颜色为黑色
            g.drawOval(xCenter-52,yYenter-52,104,104);//表盘半径为52
            g.fillOval(xCenter-3,yYenter-3,5,5);//表盘中心点
            Date date=new Date();                //获取当前时间和日期
            String today=sdf.format(date);       //转化为特定格式显示
            Calendar cal=Calendar.getInstance(); //生成一个日历类的对象
            cal.setTime(date);                    //设置日历对象的内容
            s=cal.get(Calendar.SECOND);         //获取秒
            m=cal.get(Calendar.MINUTE);         //获取分
            h=cal.get(Calendar.HOUR);           //获取小时
            /*
                *分别计算时分秒针的横纵坐标
                * 获取时分秒针的位置
             */
            xs=(int)(Math.cos(s*Math.PI/30-Math.PI/2)*45+xCenter);
            ys=(int)(Math.sin(s*Math.PI/30-Math.PI/2)*45+yYenter);
            xm=(int)(Math.cos(m*Math.PI/30-Math.PI/2)*40+xCenter);
            ym=(int)(Math.sin(m*Math.PI/30-Math.PI/2)*40+yYenter);
            xh=(int)(Math.cos((h*30+m/2)*Math.PI/180-Math.PI/2)*30+xCenter);
            yh=(int)(Math.sin((h*30+m/2)*Math.PI/180-Math.PI/2)*30+yYenter);

            /*
                * 表盘上时间数字的位置显示
                * 设置表盘上的字体、大小和颜色
            */
            g.setFont(new Font("TimerRoman",Font.PLAIN,14));//设置当前绘图区域的字体和大小
            g.setColor(Color.DARK_GRAY);                                //设置字体颜色
            g.drawString("9",xCenter-45,yYenter+5);
            g.drawString("3",xCenter+40,yYenter+3);
            g.drawString("12",xCenter-7,yYenter-37);
            g.drawString("6",xCenter-4,yYenter+45);
            //数字时钟显示
            g.setColor(Color.darkGray);
            g.drawString(today,30,180);
            /*
                *分别画时针、分针、秒针
                * 秒针一条线，分针、时针两条线
                * 时、分、秒针宽度不同以作区分
             */
            g.setColor(Color.red);//设置秒针颜色为红色
            g.drawLine(xCenter,yYenter,xs,ys);//秒针画线
            g.setColor(Color.BLUE);//设置时针、分针为蓝色
            g.drawLine(xCenter,yYenter-1,xm,ym);//分针画线
            g.drawLine(xCenter-1,yYenter,xm,ym);
            g.drawLine(xCenter,yYenter-2,xh,yh);//时针画线
            g.drawLine(xCenter-2,yYenter,xh,yh);
            /*
                *保存时、分、秒的指针位置
             */
            lastXs=xs;  lastYs=ys;
            lastXm=xm;  lastYm=ym;
            lastXh=xh;  lastYh=yh;
            /*
                    *时钟刷新，消除指针
                    * 以背景颜色画线，擦除指针
             */
            g.setColor(getBackground());//背景颜色
            if (xs!=lastXs||ys!=lastYs){                    //秒针
               //g.fillOval(lastXs-5,lastYs-5,10,10);
                g.drawLine(xCenter,yYenter,lastXs,lastYs);//当时间变化重新在原位置画线
            }
            if (xm!=lastXm||ym!=lastYm){                    //分针
                g.drawLine(xCenter,yYenter-1,lastXm,lastYm);
                g.drawLine(xCenter-1,yYenter,lastXm,lastYm);
            }
            if (xh!=lastXh||yh!=lastYh){                    //时针
                g.drawLine(xCenter,yYenter-2,lastXh,lastYh);
                g.drawLine(xCenter-2,yYenter,lastXh,lastYh);
            }


        }
    }

    public void start(){         //启动线程
        if (timer==null){
            timer=new Thread(this); //生成对象
            timer.start();      //启动生成的线程
        }
    }
    public void stop(){
        if(timer!=null){
            timer.interrupt();//中短线程
            timer=null;
        }
    }

    @Override
    public void run() {
        while (timer!=null){
            try{
                Thread.sleep(1000);//时钟刷新时间1s
            }catch (InterruptedException e){

            }
            repaint();//调用paintComponent()方法重画时钟
        }
    }
}


