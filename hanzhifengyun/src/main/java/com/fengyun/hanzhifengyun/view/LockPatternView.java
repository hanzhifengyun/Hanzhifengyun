package com.fengyun.hanzhifengyun.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fengyun.hanzhifengyun.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("DrawAllocation")
public class LockPatternView extends View {
    private MyPoint[][] points = new MyPoint[3][3]; // 3行3列 9 个点
    private boolean isInit, isStart, isFinsh, movintNoPoint; // isInit是否初始化View   isStart点是绘制开始   isFinsh是否绘制结束
    private Bitmap pointNormal, pointPressed, pointError, linePressed,
            lineError;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);// 画笔
    private Matrix matrix = new Matrix(); //矩阵,用来处理线的缩放
    private float width, height, offsetsX, offsetsY, bitMapR, movingX, movingY;

    private List<MyPoint> pointList = new ArrayList<MyPoint>();// 用来记录按下点的集合
    private static final int POINT_LENGTH_MIN = 4;

    private boolean isSettingSuccess = false;
    private Handler handler = new Handler();

    public boolean isSettingSuccess() {
        return isSettingSuccess;
    }

    public void setIsSettingSuccess(boolean isSettingSuccess) {
        this.isSettingSuccess = isSettingSuccess;
    }

    private OnPatterChangeListener onPatterChangeListener;

    public LockPatternView(Context context) {
        super(context);
    }

    public LockPatternView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockPatternView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            init();

        }
        //画点
        canvasPoint(canvas);

        //画线
        if (pointList.size() > 0) {
            //绘制九宫格点与点之间的线
            MyPoint a = pointList.get(0);
            for (int i = 0; i < pointList.size(); i++) {
                MyPoint b = pointList.get(i);
                canvasLine(canvas, a, b);
                a = b;
            }
            //绘制九宫格点与鼠标点(在九宫格点外部)的线
            if (movintNoPoint) {
                canvasLine(canvas, a, new MyPoint(movingX, movingY));
            }
        }
    }

    // 将点绘制到画布上
    private void canvasPoint(Canvas canvas) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                MyPoint point = points[i][j];
                if (point.state == MyPoint.STATE_NORMAL)// 点正常
                {
                    canvas.drawBitmap(pointNormal, point.x - bitMapR, point.y
                            - bitMapR, paint);
                } else if (point.state == MyPoint.STATE_PRESSED)// 点被按下
                {
                    canvas.drawBitmap(pointPressed, point.x - bitMapR, point.y
                            - bitMapR, paint);
                } else
                // 点错误
                {
                    canvas.drawBitmap(pointError, point.x - bitMapR, point.y
                            - bitMapR, paint);
                }
            }
        }
    }

    //将线绘制到画布上
    private void canvasLine(Canvas canvas, MyPoint a, MyPoint b) {
        double distance = MyPoint.getDistance(a, b);
        //System.out.println("distance:" + distance);
        float degrees = MyPoint.getDegrees(a, b);
        //System.out.println("degrees:" + degrees);
        canvas.rotate(degrees, a.x, a.y); //旋转角度
        if (a.state == MyPoint.STATE_PRESSED)//状态为按下时
        {
            //设置缩放比例
            matrix.setScale((float) (distance / linePressed.getWidth()), 1.0f);
            matrix.postTranslate(a.x, a.y - linePressed.getHeight() / 2);//缩放时固定起始点
            canvas.drawBitmap(linePressed, matrix, paint);
        } else//状态为错误时
        {
            //设置缩放比例
            matrix.setScale((float) (distance / lineError.getWidth()), 1.0f);
            matrix.postTranslate(a.x, a.y - lineError.getHeight() / 2);//缩放时固定起始点
            canvas.drawBitmap(lineError, matrix, paint);
        }

        canvas.rotate(-degrees, a.x, a.y); //将角度转回原始状态
    }

    // 初始化点和线
    private void init() {
        // 1.获取布局的宽高
        width = getWidth();// 获取屏幕的宽
        height = getHeight();// 获取屏幕的高

        // 2.计算布局偏移量
        // 判断横竖屏
        if (width > height)// 横屏
        {
            offsetsX = (width - height) / 2;
            // 由于横屏状态下height更小,则布局(正方形)的边长应取height的值;
            width = height;
        } else
        // 竖屏
        {
            offsetsY = (height - width) / 2;
            // 由于竖屏状态下width更小,则布局(正方形)的边长应取width的值;
            height = width;
        }

        // 3.图片资源

        pointNormal = BitmapFactory.decodeResource(getResources(),
                R.drawable.point_normal);
        pointPressed = BitmapFactory.decodeResource(getResources(),
                R.drawable.point_pressed);
        pointError = BitmapFactory.decodeResource(getResources(),
                R.drawable.point_error);
        linePressed = BitmapFactory.decodeResource(getResources(),
                R.drawable.line_pressed);
        lineError = BitmapFactory.decodeResource(getResources(),
                R.drawable.line_error);

        // 4.初始化点,点的坐标

        // 第一行
        points[0][0] = new MyPoint(offsetsX + width / 4, offsetsY + width / 4);
        points[0][1] = new MyPoint(offsetsX + width / 2, offsetsY + width / 4);
        points[0][2] = new MyPoint(offsetsX + width - width / 4, offsetsY
                + width / 4);

        // 第二行
        points[1][0] = new MyPoint(offsetsX + width / 4, offsetsY + width / 2);
        points[1][1] = new MyPoint(offsetsX + width / 2, offsetsY + width / 2);
        points[1][2] = new MyPoint(offsetsX + width - width / 4, offsetsY
                + width / 2);

        // 第三行
        points[2][0] = new MyPoint(offsetsX + width / 4, offsetsY + width
                - width / 4);
        points[2][1] = new MyPoint(offsetsX + width / 2, offsetsY + width
                - width / 4);
        points[2][2] = new MyPoint(offsetsX + width - width / 4, offsetsY
                + width - width / 4);

        // 5.处理图片资源的半径
        bitMapR = pointNormal.getHeight() / 2;

        //6.设置点对应角标
        int index = 1;
        for (MyPoint[] myPoints : points) {
            for (MyPoint point : myPoints) {
                point.index = index;
                index++;
            }
        }

        //7.初始化完毕
        isInit = true;
    }

    // 监听鼠标点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isSettingSuccess) {
            return true;
        }
        movintNoPoint = false;  //默认为false
        isFinsh = false;  //每次点击都初始为false
        movingX = event.getX();
        movingY = event.getY();// 获取鼠标的位置

        MyPoint point = null;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:// 鼠标按下
            {
                resetPointList();//先重置点集合
                point = checkPointIsSelected();
                if (point != null) //证明鼠标选中了一个点,图案真正开始绘制
                {
                    isStart = true;
                } else //按下时点击的是屏幕除点外的其他地方
                {

                }
                break;
            }
            case MotionEvent.ACTION_MOVE:// 鼠标移动
            {
                if (isStart) {
                    point = checkPointIsSelected();
                    if (point == null) {
                        movintNoPoint = true;  //此时移动的点不是九宫格的点
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:// 鼠标抬起
            {
                isFinsh = true;
                isStart = false;
                break;
            }
        }
        //开始绘制并且没有结束的时候, 需对选中的点检查是否重复
        if (!isFinsh && isStart && point != null) {
            if (checkCrossPoint(point)) //交叉点
            {
                movintNoPoint = true;  //不算成九宫格的点
            } else  //新点
            {
                point.state = MyPoint.STATE_PRESSED;  //设置状态为按下状态,添加到list集合中
                pointList.add(point);
            }
        }
        //绘制结束
        if (isFinsh) {
            if (pointList.size() == 1)//不构成绘制线的条件
            {
                resetPointList();
            } else if (pointList.size() > 1 && pointList.size() < POINT_LENGTH_MIN) //绘制长度不符合要求
            {
                if (onPatterChangeListener != null) {
                    onPatterChangeListener.onPatterChange("");
                }
            } else //绘制符合要求
            {
                String password = "";
                if (onPatterChangeListener != null) {
                    for (int i = 0; i < pointList.size(); i++) {
                        password += pointList.get(i).index;
                    }
                    if (!TextUtils.isEmpty(password)) {
                        onPatterChangeListener.onPatterChange(password);
                    }
                }

            }
        }
        //每次改变,刷新View
        postInvalidate();

        return true; // return true 实时监听
    }

    //检查移动到的点是否已经选取过,即是否有交叉点
    private boolean checkCrossPoint(MyPoint point) {
        if (pointList.contains(point)) {
            return true;
        } else {
            return false;
        }
    }

    //绘制不成立
    public void resetPointList() {
        for (int i = 0; i < pointList.size(); i++) {
            pointList.get(i).state = MyPoint.STATE_NORMAL; //重置时将点击过的点全部还原
        }
        pointList.clear();
        isSettingSuccess = false;
    }

    //绘制错误
    public void drawError() {
        for (MyPoint point : pointList) {
            point.state = MyPoint.STATE_ERROR;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resetPointList();
                postInvalidate();
            }
        }, 1000);
    }

    private MyPoint checkPointIsSelected() {
        // 鼠标的点和9个点位置比较
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                MyPoint point = points[i][j];
                if (MyPoint.compare(movingX, movingY, point.x, point.y, bitMapR))//如果重合则返回该点
                {
                    return point;
                }
            }
        }

        return null;
    }

    /**
     * 自定义的点
     *
     * @author fengyun
     */
    public static class MyPoint {
        // 正常
        public static int STATE_NORMAL = 0;
        // 选中
        public static int STATE_PRESSED = 1;
        // 错误
        public static int STATE_ERROR = 2;
        public float x, y;
        public int index = 0, state = 0;

        public MyPoint() {

        }

        public MyPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public static float getDegrees(MyPoint a, MyPoint b) {
            float degrees = 0.0f;

            if (a.y < b.y && a.x == b.x)  //90度
            {
                degrees = 90.0f;
            } else if (a.y > b.y && a.x == b.x)  //270度
            {
                degrees = 270.0f;
            } else if (a.y == b.y && a.x == b.x)  //270度
            {
                degrees = 0.0f;
            } else if (a.x < b.x) //270 ~ 90度之间(不包含90度和270度)
            {
                double tan = (b.y - a.y) / (b.x - a.x);
                degrees = (float) (Math.atan(tan) * 180 / Math.PI);

            } else  //90度到270度
            {
                double tan = (b.y - a.y) / (b.x - a.x);
                degrees = 180 + (float) (Math.atan(tan) * 180 / Math.PI);
            }


            return degrees;
        }

        /**
         * 计算亮点之间的距离
         *
         * @param a 点a
         * @param b 点b
         * @return
         */
        public static double getDistance(MyPoint a, MyPoint b) {
            return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
        }

        /**
         * 计算移动的点是否与九宫格的点重合
         *
         * @param movingX 鼠标的X
         * @param movingY 鼠标的Y
         * @param pointX  参考点的X
         * @param pointY  参考点的Y
         * @param r       参考点的半径
         * @return 是否重合
         */
        public static boolean compare(float movingX, float movingY,
                                      float pointX, float pointY, float r) {
            // 当鼠标的位置距离点的位置小于点图标的半径时,返回true;
            return Math.sqrt((movingX - pointX) * (movingX - pointX) + (movingY - pointY) * (movingY - pointY)) < r;
        }
    }

    /**
     * 图案监听器
     */
    public static interface OnPatterChangeListener {
        //图案改变
        void onPatterChange(String password);
    }

    //设置图案的监听器
    public void setPatterChangeListener(OnPatterChangeListener onPatterChangeListener) {
        if (onPatterChangeListener != null) {
            this.onPatterChangeListener = onPatterChangeListener;
        }
    }
}
