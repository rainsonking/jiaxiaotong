package com.example.bailyhome.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bailyhome.R;
import com.example.bailyhome.adapter.HworkGradeTimeBaseAdapter;
import com.example.bailyhome.config.DateUtil;
import com.example.bailyhome.model.OnDataListener;
import com.example.bailyhome.view.LineChartView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/16 0016.
 * <p>
 * 折线图
 */
public class FirstTextChartFragment extends Fragment implements OnDataListener {
    private double agvScore;
    private String tag, jsonObject, className;
    private List<String> list1 = null;//日期
    private List<String> list2 = null;//成绩
    private List<Map<String, String>> lists = null;
    private TextView tvText;
    //y轴数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_text_chart, null);
        tvText = (TextView) view.findViewById(R.id.tv_text);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);

        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            tag = bundle.getString("str");
            jsonObject = bundle.getString("jsonObject");
            className = bundle.getString("className");
            if (className.contains("托福")) {
                String[] ylabel1 = {"0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100", "105", "110", "115", "120"};
                judgeJsonObn(layout, ylabel1);
            } else if (className.contains("雅思")) {
                String[] ylabel2 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
                judgeJsonObn(layout, ylabel2);
            } else if (className.contains("GRE")) {
                String[] ylabel3 = {"0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "110", "120", "130", "140", "150", "160", "170", "180", "190", "200", "210", "220", "230", "240"};
                judgeJsonObn(layout, ylabel3);
            } else if (className.contains("GMAT")) {
                String[] ylabel4 = {"0", "50", "100", "150", "200", "250", "300", "350", "400", "450", "500", "550", "600", "650", "700", "750", "800"};
                judgeJsonObn(layout, ylabel4);
            } else if (className.contains("SAT")) {
                String[] ylabel5 = {"0", "50", "100", "150", "200", "250", "300", "350", "400", "450", "500", "550", "600", "650", "700", "750", "800", "850", "900", "950", "1000", "1050", "1100", "1150", "1200", "1250", "1300", "1350", "1400", "1450", "1500", "1550", "1600"};
                judgeJsonObn(layout, ylabel5);
            } else if (className.contains("ACT")) {
                String[] ylabel7 = {"0", "4", "8", "12", "16", "20", "24", "28", "32", "36"};
                judgeJsonObn(layout, ylabel7);
            } else {
                String[] ylabel6 = {"0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100"};
                judgeJsonObn(layout, ylabel6);
            }
        } else {
            setTvText();
        }

//        TextView tv = new TextView(getContext());
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        tv.setText("平均的分80分");
//        layout.addView(tv,lp);
        return view;
    }

    public void judgeJsonObn(LinearLayout layout, String[] ylabel1) {
        if (jsonObject != null && jsonObject.length() >= 0) {
            handleJson(layout, ylabel1);
        } else {
            setTvText();
        }
    }

    private void setTvText() {
        tvText.setVisibility(View.VISIBLE);
        tvText.setText("没有该模块数据");
    }

    private void handleJson(LinearLayout layout, String[] ylabel) {
        try {
            JSONObject jsonObject1 = new JSONObject(jsonObject);
            org.json.JSONArray array = jsonObject1.getJSONArray("rows");
            if (array != null && array.length() > 0) {
                tvText.setVisibility(View.GONE);
                getLists(array, layout, ylabel);
//                if (list1 != null && list2 != null && list1.size() > 0 && list2.size() > 0) {
//                    String[] xlabel = (String[]) list1.toArray(new String[list1.size()]);
//                    String[] datastr = (String[]) list2.toArray(new String[list2.size()]);
//
//                    getResult(layout, xlabel, datastr, ylabel);
//                } else {
//                    setTvText();
//                }
            } else {
                setTvText();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResult(LinearLayout layout, String[] xlabel, String[] datastr, String[] ylabel) {
        int[] data = new int[datastr.length];
        double total = 0;
        int num = 0;
        int staLength = 0;
        if (xlabel.length > 10) {
            staLength = xlabel.length - 10;
            String[] xlabelStr = new String[10];
            int[] dataInt = new int[10];
            int z = 0;
            for (int j = staLength; j < xlabel.length; j++, z++) {
                xlabelStr[z] = xlabel[j];
                dataInt[z] = Integer.parseInt(datastr[j]);
                if (dataInt[z] != 0) {
                    total += dataInt[z];
                    num += 1;
                }
            }
            initLineChart(layout, xlabelStr, dataInt, total, num, ylabel);
        } else {
            for (int j = 0; j < xlabel.length; j++) {
                data[j] = Integer.parseInt(datastr[j]);
                if (data[j] != 0) {
                    total += data[j];
                    num += 1;
                }
            }
            initLineChart(layout, xlabel, data, total, num, ylabel);
        }
    }

    private void getLists(JSONArray array, LinearLayout layout, String[] ylabel) throws JSONException {
        if ("0".equals(tag)) {
            lists = new ArrayList<>();
            String[] ylabel7 = {"0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100"};
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Map<String, String> map = new HashMap<String, String>();
                map.put("date", object.getString("AFM_2").substring(0, 10));
                // list1.add(object.getString("AFM_12").substring(5, 10));
                if (object.has("AFM_12")) {
                    //    list2.add(object.getString("AFM_10"));
                    String afm10 = object.getString("AFM_12");
                    if (afm10 != null && afm10.length() > 0) {
                        map.put("result", afm10);
                    } else {
                        map.put("result", "0");
                    }
                } else {
                    map.put("result", "0");
                }
                lists.add(map);
            }
            drawChart(layout, ylabel7);
        } else if ("1".equals(tag)) {
            lists = new ArrayList<>();
            String[] ylabel8 = {"0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100"};

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Map<String, String> map = new HashMap<>();
                if (object.has("AFM_12")) {
                    map.put("date", object.getString("AFM_12").substring(0, 10));
                    //list1.add(object.getString("AFM_9").substring(5, 10));
                    if (object.has("AFM_4")) {
                        map.put("result", object.getString("AFM_4"));
                        // list2.add(object.getString("AFM_11"));
                    } else {
                        map.put("result", "0");
                        //list2.add("0");
                    }
                    lists.add(map);
                }
            }
            drawChart(layout, ylabel8);
//            if (lists.size() > 0) {
//                sortLists(lists);
//            }
        } else if ("2".equals(tag)) {
            lists = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Map<String, String> map = new HashMap<>();
                if (object.has("AFM_19")) {
                    //list1.add(object.getString("AFM_6").substring(5, 10));
                    map.put("date", object.getString("AFM_19").substring(0, 10));
                    Log.e("date2-", object.getString("AFM_19").substring(0, 10));
                    if (object.has("AFM_2")) {
                        map.put("result", object.getString("AFM_2"));
                        Log.e("result2-", object.getString("AFM_2"));
                        //list2.add(object.getString("AFM_2"));
                    } else {
                        map.put("result", "0");
                        //list2.add("0");
                    }
                    lists.add(map);
                }
            }
            drawChart(layout, ylabel);
//            if (lists.size() > 0) {
//                sortLists(lists);
//            }
        } else if ("3".equals(tag)) {
            lists = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Map<String, String> map = new HashMap<>();
                Log.e("FirstText-chart-", object.toString());
                // list1.add(object.getString("AFM_16").substring(5, 10));
                map.put("date", object.getString("AFM_17").substring(0, 10));
                if (object.has("AFM_18")) {
                    // list2.add(object.getString("AFM_17"));
                    String afm17 = object.getString("AFM_18");
                    if (afm17 != null && afm17.length() > 0) {
                        map.put("result", afm17);
                    } else {
                        map.put("result", "0");
                    }
                    map.put("result", object.getString("AFM_18"));
                } else {
                    //  list2.add("0");
                    map.put("result", "0");
                }
                lists.add(map);
            }
            drawChart(layout, ylabel);
//            if (lists.size() > 0) {
//                sortLists(lists);
//            }
        } else if ("4".equals(tag)) {
            lists = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Map<String, String> map = new HashMap<>();
                Log.e("obj-char-4-", object.toString());
                //list1.add(object.getString("AFM_3").substring(5, 10));
                map.put("date", object.getString("AFM_3").substring(0, 10));
                if (object.has("AFM_4")) {
                    // list2.add(object.getString("AFM_4"));
                    map.put("result", object.getString("AFM_4"));
                } else {
                    // list2.add("0");
                    map.put("result", "0");
                }
                lists.add(map);
            }
            drawChart(layout, ylabel);
        }
    }

    private void drawChart(LinearLayout layout, String[] ylabel) {
        if (lists.size() > 0) {
            sortLists(lists);
            String[] xlabel = (String[]) list1.toArray(new String[list1.size()]);
            String[] datastr = (String[]) list2.toArray(new String[list2.size()]);

            getResult(layout, xlabel, datastr, ylabel);
        } else {
            setTvText();
        }
    }

    private void sortLists(List<Map<String, String>> list) {
        Collections.sort(list, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> stringStringMap, Map<String, String> t1) {
                Date date1 = DateUtil.stringToDate(stringStringMap.get("date"));
                Date date2 = DateUtil.stringToDate(t1.get("date"));
                Log.e("stadate1", stringStringMap.get("date"));
                Log.e("stadate2", t1.get("date"));
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date2.before(date1)) {
                    return 1;
                }
                return -1;
            }
        });
        list1 = new ArrayList<String>();
        list2 = new ArrayList<String>();
        for (int j = 0; j < list.size(); j++) {
            Map<String, String> map = list.get(j);
            list1.add(map.get("date").substring(5, 10));
            list2.add(map.get("result"));
        }
        Log.e("list12--", list1.toString() + "/" + list2.toString());
    }

    private void initLineChart(LinearLayout layout, String[] xlabel, int[] data, double total, int num, String[] ylabel) {
        agvScore = total / num;
        DecimalFormat df = new DecimalFormat("###.00");
        LineChartView lineChartView = new LineChartView(getActivity(), xlabel, ylabel, "", data, df.format(agvScore) + "");
        layout.addView(lineChartView);
    }

    @Override
    public void onGetDataSuccess(String jsonData) {

    }

    @Override
    public void onGetDataError() {

    }

    @Override
    public void onLoading(long total, long current) {

    }
}
