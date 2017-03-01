package com.bosong.demolibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FrameLayout mTableParent;
    private PieView mPieView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTableParent = (FrameLayout) findViewById(R.id.fl_table);
        mPieView = (PieView) findViewById(R.id.pie_view);

        DetailTableLayout table = generateTable();
        table.showIn(mTableParent);

        mPieView.setPercent1(0.3f);
    }

    private DetailTableLayout generateTable(){
        DetailTableLayout table = new DetailTableLayout(this);
        List<String> hou = Arrays.asList("超薄", "薄", "常规", "厚", "加厚", "加绒", "羽绒");
        List<String> tan = Arrays.asList("无弹", "微弹");
        List<String> ruan = Arrays.asList("超软", "适中", "偏硬");
        List<DetailTableLayout.TableRowItem> data = new ArrayList<>();
        data.add(new DetailTableLayout.TableRowItem("厚薄", hou, 4));
        data.add(new DetailTableLayout.TableRowItem("弹性", tan, 1));
        data.add(new DetailTableLayout.TableRowItem("软硬度", ruan, 0));
        int width = DeviceUtils.getWindowWidth(this);
        table.init(data, width);
        return table;
    }
}
