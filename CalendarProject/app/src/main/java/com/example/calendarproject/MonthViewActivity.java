package com.example.calendarproject;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MonthViewActivity extends AppCompatActivity {
    ViewPager2 vp; //페이징을 위한 변수
    int y,m;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp=findViewById(R.id.vpPager); //xml파일의 vpPager가져오기
        //일단 처음 시작할때 월간달력이 보이도록 설정해두었음
        setMonthPager(vp);//액티비티 시작시 weeKFragmentAdater와 연결함(아래 함수있음)

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MonthViewActivity.this, ScheduleActivity.class);
                startActivity(intent); //액티비티 열기
            }
        });
    }

    //메뉴바를 동적 추가하는 부분
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //메뉴바의 아이템이 클릭되었을때-이 부분에서 월별/주별 달력 전환함
    public boolean onOptionsItemSelected(MenuItem item) {
        //클릭한 메뉴 item id를 읽어옴
        switch (item.getItemId()) {
            //02. 월간 달력 프레그먼트
            case R.id.action_monthActivity:
                setMonthPager(vp);
                return true;

            //03. 주간 달력 프레그먼트
            case R.id.action_weekActivity:
                setWeekPager(vp);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //월간 페이지 어댑터 설정 함수
    private void setMonthPager(ViewPager2 vp) {
        ViewPager2 monthPager = vp;
        //출처: https://itpangpang.tistory.com/266 [ITPangPang]
        MonthPagerAdapter MFA = new MonthPagerAdapter(this);
        monthPager.setAdapter(MFA);

        monthPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                //메뉴바의 글자를 바꾸는 부분(연도-월 표시 변경)
                MonthViewActivity.this.getSupportActionBar().setTitle(MFA.toString(position));
                //출처: 액션바 텍스트 바꾸기 https://m.blog.naver.com/dhdnjswnd/221665442594
            }
        });

        TextView blank=this.findViewById(R.id.dates_text);

        LinearLayout.LayoutParams pp=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
        blank.setLayoutParams(pp);
        Log.d("kuku", "setMonthPager: "+blank.getWidth());
        monthPager.setCurrentItem(MFA.ItemCenter);
    }

    //주간 페이지 어댑터 설정 함수
    private void setWeekPager(ViewPager2 vp) {
        ViewPager2 weekPager = vp;
        WeekPagerAdapter WFA= new WeekPagerAdapter(this);
        weekPager.setAdapter(WFA);
        //페이지 변경 이벤트 리스너
        weekPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                //메뉴바의 글자를 바꾸는 부분(연도-월 표시 변경)
                MonthViewActivity.this.getSupportActionBar().setTitle(WFA.toString(position));
                //출처: 액션바 텍스트 바꾸기 https://m.blog.naver.com/dhdnjswnd/221665442594
            }
        });
        TextView blank=this.findViewById(R.id.dates_text);
        LinearLayout.LayoutParams pp=new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.size_50dp),LinearLayout.LayoutParams.WRAP_CONTENT);
        blank.setLayoutParams(pp);
        //https://realjune.tistory.com/11 dimen의 dp값을 px값으로 간단하게 변환해주는 코드
        //https://ckbcorp.tistory.com/1197 setWidth가 안될때 param으로 동적 변환
        weekPager.setCurrentItem(WFA.ItemCenter); //중간 페이지에서 시작
    }


};