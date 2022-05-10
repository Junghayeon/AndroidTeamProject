package com.example.calendarproject;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthFragment extends Fragment {

    ArrayList<String> daysList = new ArrayList<String>();
    Calendar now = getInstance();
    Calendar cal = getInstance();
    int y;
    int m;

    public MonthFragment(int position, int ItemCenter) {
        // Required empty public constructor
        m=now.get(MONTH); //매번 get해줘야 현재 월의 정보가 담김
        m=m+(position-ItemCenter);
        now.set(MONTH, m); //현재 날짜에서 월정보 변경
        y=now.get(YEAR);
        m=now.get(MONTH);
        //Log.d("KUKU", "MonthFragment: "+y+" "+m);
    }

    // TODO: Rename and change types and number of parameters
    public MonthFragment newInstance(String param1, String param2) {
        MonthFragment fragment = new MonthFragment(y, m);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_month, container, false);

        //기능 3. 이전 다음 버튼으로 다른 월의 달력을 표시

        //기능 2. 현재 날짜 받아와서 GridView에 날짜 뿌려주기
        int curYear = now.get(YEAR);
        //int curDay = now.get(DAY_OF_MONTH);
        int curMonth = now.get(MONTH) + 1; //MONTH는 0부터 시작한다(1월:0 ~ 12월:11)
        int lastDate = now.getActualMaximum(DATE);
        cal.set(curYear, now.get(MONTH), 1); //DAY_OF_MONTH를 1로 설정 (월의 첫날)
        int startDay = cal.get(DAY_OF_WEEK); //그 주의 요일 반환 (일:1 ~ 토:7)
        Log.d("KUKU", "onCreateView: "+lastDate);
        cal.set(DATE, 1); //DAY_OF_MONTH를 1로 설정 (월의 첫날)

        //daysList에 날짜 채워넣기
        for (int i = 1; i <= 42; i++) {
            //달의 첫일(1일)의 요일보다 작을 시 공백 채워넣기
            if (i < startDay||i>=lastDate+startDay) {
                daysList.add(" ");
            } else {
                daysList.add(String.valueOf(i - startDay + 1));
                //공백 채우는 과정에서 i가 증가해서 첫일만큼 빼준다
                //(요일은 1부터 시작>0일을 만들지 않기 위해 +1)
            }
        }

        MonthAdapter adapt=new MonthAdapter(getActivity(), R.layout.month_item, daysList, (MonthViewActivity) getActivity());
        //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용
        // id를 바탕으로 화면 레이아웃에 정의된 GridView 객체 로딩
        GridView gridview = (GridView) rootView.findViewById(R.id.MONTH_monthgrid);
        // 어댑터를 GridView 객체에 연결
        gridview.setAdapter(adapt);

        //기능 4. Toast 메세지
        // 항목 선택 이벤트 처리
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //position은 0부터 시작, 첫요일만큼 빼준다
                v.setBackgroundColor(Color.WHITE);
                int curDay = position + 1 - startDay + 1;

                if (curDay > 0) { //공백부분은 토스트 메세지 없도록
                    Toast.makeText(getActivity(),
                            curYear + "." + curMonth + "." + curDay,
                            Toast.LENGTH_SHORT).show();
                    for(int i=0;i<adapt.getCount();i++) {

                        v.setBackgroundColor(Color.CYAN);
                        parent.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }
                    v.setBackgroundColor(Color.CYAN);
                }

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
    public int dpToPx(int sizeDP){
        int pxVal=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,sizeDP,getResources().getDisplayMetrics());
        return pxVal; //https://chebaum.tistory.com/13 dp를 px로 바꾸는 함수
    }
}