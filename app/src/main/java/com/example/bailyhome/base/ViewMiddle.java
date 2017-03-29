package com.example.bailyhome.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.bailyhome.R;
import com.example.bailyhome.adapter.TextAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ViewMiddle extends LinearLayout implements ViewBaseAction {

    private ListView mListView;
    //	private final String[] items = new String[] { "item1", "item2", "item3", "item4", "item5", "item6" };//显示字段
//	private final String[] itemsVaule = new String[] { "1", "2", "3", "4", "5", "6" };//隐藏id
    private OnSelectListener mOnSelectListener;
    private TextAdapter adapter;
    private String showText = "item1";
    private Context mContext;
    public ArrayList<String> listStr = new ArrayList<String>();
    private String mDistance;

    public String getShowText() {
        return showText;
    }

    public ViewMiddle(Context context, ArrayList<String> list) {
        super(context);
        this.mContext = context;
        this.listStr = list;
        init(context, list);
    }

    public ViewMiddle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, listStr);
    }

    public ViewMiddle(Context context, AttributeSet attrs, int defStyle, ArrayList<String> listStr) {
        super(context, attrs, defStyle);
        init(context, listStr);
    }

    public ViewMiddle(Context context, AttributeSet attrs, ArrayList<String> listStr) {
        super(context, attrs);
        init(context, listStr);
    }

    private void init(Context context, final ArrayList<String> liststr) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_distance, this, true);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.type_window_d));
        //setBackgroundDrawable(getResources().getDrawable(R.mipmap.choosearea_bg_mid));
        //setBackgroundColor(getResources().getColor(R.color.lawngreen));
        mListView = (ListView) findViewById(R.id.listView);
        //adapter = new TextAdapter(context, liststr, R.mipmap.choose_item_right, R.drawable.choose_eara_item_selector);
        adapter = new TextAdapter(context, liststr, R.color.white, R.drawable.choose_eara_item_selector);

        adapter.setTextSize(17);
        if (mDistance != null) {
            for (int i = 0; i < liststr.size(); i++) {
                if (liststr.get(i).equals(mDistance)) {
                    adapter.setSelectedPositionNoNotify(i);
                    showText = liststr.get(i);
                    break;
                }
            }
        }
        mListView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                if (mOnSelectListener != null) {
                    adapter.setSelectedPosition(position);
                    showText = liststr.get(position);
                    try {
                        mOnSelectListener.getValue(position + "", liststr.get(position));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        public void getValue(String distance, String showText) throws Exception;
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
}
