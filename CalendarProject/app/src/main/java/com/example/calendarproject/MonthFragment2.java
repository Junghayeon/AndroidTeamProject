package com.example.calendarproject;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthFragment2 extends Fragment {

    ArrayList<String> daysList = new ArrayList<String>();
    Calendar now = getInstance();
    Calendar cal = getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MonthFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthFragment2 newInstance(String param1, String param2) {
        MonthFragment2 fragment = new MonthFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_week, container, false);

//        //기능 3. 이전 다음 버튼으로 다른 월의 달력을 표시
//        //받은 인텐트가 있으면 그 내용으로 바꾸고, 없으면 바꾸지 않음
//
//        if (get.getStringExtra("monthData") != null) {
//            int m = Integer.parseInt(get.getStringExtra("monthData"));
//            int y = Integer.parseInt(get.getStringExtra("yearData"));
//            //전달 받은 날짜로 캘린더 객체 수정하기
//            now.set(MONTH, m);
//            now.set(YEAR, y);
//            cal.set(MONTH, m);
//            cal.set(YEAR, y);
//        }

        //기능 2. 현재 날짜 받아와서 GridView에 날짜 뿌려주기
        int curYear = now.get(YEAR);
        //int curDay = now.get(DAY_OF_MONTH);
        int curMonth = now.get(MONTH) + 1; //MONTH는 0부터 시작한다(1월:0 ~ 12월:11)
        int lastDate = now.getActualMaximum(DATE);
        cal.set(curYear, curMonth, 1); //DAY_OF_MONTH를 1로 설정 (월의 첫날)
        int startDay = cal.get(DAY_OF_WEEK); //그 주의 요일 반환 (일:1 ~ 토:7)
        cal.set(DATE, 1); //DAY_OF_MONTH를 1로 설정 (월의 첫날)

        //daysList에 날짜 채워넣기
        for (int i = 1; i <= lastDate + startDay; i++) {
            //달의 첫일(1일)의 요일보다 작을 시 공백 채워넣기
            if (i < startDay) {
                daysList.add(" ");
            } else {
                daysList.add(String.valueOf(i - startDay + 1));
                //공백 채우는 과정에서 i가 증가해서 첫일만큼 빼준다
                //(요일은 1부터 시작>0일을 만들지 않기 위해 +1)
            }
        }

        //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용
        ArrayAdapter<String> adapt
                = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                daysList);
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
                int curDay = position + 1 - startDay + 1;
                if (curDay > 0) //공백부분은 토스트 메세지 없도록
                    Toast.makeText(getActivity(),
                            curYear + "." + curMonth + "." + curDay,
                            Toast.LENGTH_SHORT).show();
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }
}