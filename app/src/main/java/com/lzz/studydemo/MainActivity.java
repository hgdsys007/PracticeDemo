package com.lzz.studydemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.example.lzz.studtdemo.Logger;
import com.lzz.studydemo.greendao.DaoSession;
import com.lzz.studydemo.greendao.Teacher;
import com.lzz.studydemo.greendao.User;
import com.lzz.studydemo.greendao.UserDao;
import com.lzz.studydemo.ui.activity.AnamorphismActivity;
import com.lzz.studydemo.ui.activity.HttpActivity;
import com.lzz.studydemo.ui.activity.SingleInstanceActivity;
import com.lzz.studydemo.ui.activity.StickyHeaderActivity;
import com.lzz.studydemo.ui.adapter.CustomTransformer;
import com.lzz.studydemo.ui.adapter.UltraPagerAdapter;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DaoSession daoSession;
    private StudyDemoApplication studyDemoApplication;
    private UltraViewPager ultraViewPager;
    private UltraPagerAdapter ultraPagerAdapter;
    private List<User> users;
    private ViewPager realViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        users = new ArrayList<>();
        studyDemoApplication = StudyDemoApplication.getStudyDemoApplication();
        daoSession = studyDemoApplication.getDaoSession();
        ultraViewPager = findViewById(R.id.ultra_viewpager);
        realViewPager = ultraViewPager.getViewPager();
        realViewPager.setPageTransformer(true, new CustomTransformer());
        findViewById(R.id.insert).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.change).setOnClickListener(this);
        findViewById(R.id.select).setOnClickListener(this);
        findViewById(R.id.go2Trans).setOnClickListener(this);
        findViewById(R.id.go2Sticky).setOnClickListener(this);
        findViewById(R.id.go2Http).setOnClickListener(this);
        findViewById(R.id.go2Instance).setOnClickListener(this);


//        StatusBarUtil.setTransparent(this);

//        StatusBarUtil.setTransparent(this,null);
//        StatusBarUtil.setFullScreen(this);
        setupUltraViewPager();
    }

    private void setupUltraViewPager() {
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraPagerAdapter = new UltraPagerAdapter(true);
        realViewPager.setAdapter(ultraPagerAdapter);

        //UltraPagerAdapter 绑定子view到UltraViewPager
        ultraPagerAdapter.setData(users);

        ultraViewPager.setInfiniteLoop(true);
        ultraViewPager.setMultiScreen(0.6f);

        //内置indicator初始化
        ultraViewPager.initIndicator();

        //设置indicator样式
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.GREEN)
                .setNormalColor(Color.WHITE)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

        //设置indicator对齐方式
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

        //构造indicator,绑定到UltraViewPager
        ultraViewPager.getIndicator().build();


        //设定页面循环播放
        ultraViewPager.setInfiniteLoop(true);

        //设定页面自动切换  间隔2秒
        ultraViewPager.setAutoScroll(2000);
        ultraViewPager.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:
                insert();
                break;
            case R.id.delete:
                delete();
                break;
            case R.id.change:
                change();
                break;
            case R.id.select:
                select();
                break;
            case R.id.go2Trans:
                go2Trans();
                break;
            case R.id.go2Sticky:
                go2Sticky();
                break;
            case R.id.go2Http:
                go2Http();

            case R.id.go2Instance:
                go2Instance();
                break;

        }
    }

    private void go2Instance() {
        SingleInstanceActivity.startActivity(this);
    }

    private void go2Http() {
        HttpActivity.startActivity(this);
    }

    /**
     * 进入状态栏渐变
     */
    private void go2Trans() {
        AnamorphismActivity.startActivity(this);

    }

    /**
     * 进入吸頂
     */
    private void go2Sticky() {
        StickyHeaderActivity.startActivity(this);

    }

    int i = 5;

    private void select() {
        //查询所有
//        List<User> users = daoSession.getUserDao().loadAll();
        //查询所有并降序
//        List<User> users = daoSession.getUserDao().queryBuilder().orderDesc(UserDao.Properties.Age).list();
        //查询名称为“修改了”where（条件1，条件2，）
//        List<User> users = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Name.eq("修改了")).list();
        //查询所有并降序, 跳过前五条offset(5)，只显示3条limit(3)。
        users = daoSession.getUserDao().queryBuilder().orderDesc(UserDao.Properties.Age).offset(5).limit(6).list();
        Logger.e("总长度" + users.size());
        for (int i = 0; i < users.size(); i++) {
            Logger.e("i" + i, users.get(i).toString());
        }
        if (users.size() > 0) {
            ultraViewPager.setVisibility(View.VISIBLE);
        } else {
            ultraViewPager.setVisibility(View.GONE);
        }
        //每次获取数据重新设置Adapter,防止数据初始化负一屏为空
        ultraPagerAdapter = new UltraPagerAdapter(false, users);
        realViewPager.setAdapter(ultraPagerAdapter);

        i += 1;

    }

    private void change() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            User user = new User((long) i, "修改了", 22);
            users.add(user);

        }
        daoSession.getUserDao().updateInTx(users);
        daoSession.clear();

    }

    private void delete() {
        //daoSession.getUserDao().deleteInTx(users);

        daoSession.getUserDao().deleteByKey((long) 1);
        daoSession.clear();

        ultraViewPager.setCurrentItem(9);
    }

    /**
     * 增加数据
     */
    private void insert() {
        User user = new User();
        long l = System.currentTimeMillis();
        user.setName("这是名字" + l);
        user.setAge(22);
        long insert = daoSession.getUserDao().insert(user);
        daoSession.clear();

        Teacher teacher = new Teacher();
        teacher.setName("李老师");
        long insertT = daoSession.getTeacherDao().insert(teacher);


        Logger.e(insert + "Teacher insertT" + insert);
    }
}
