package com.bosong.demolibrary;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by bosong on 2016/12/2.
 */

public class DetailTableLayout extends TableLayout {
    private static final int TEXT_SIZE = 12; // in dip
    private static final int TITLE_WIDTH = 45; // in dip
    private static final int ROW_HEIGHT = 43; // in dip

    private static final int DEFAULT_ITEM_COUNT = 6;
    private static final int BORDER_THICKNESS_NORMAL = 1; // in dip
    private static final int BORDER_THICKNESS_BOLD = 2; // in dip

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BORDER_THICKNESS_NORMAL, BORDER_THICKNESS_BOLD})
    private @interface BorderThickness{}
    private Context mContext;
    private List<TableRowItem> mData;
    private int mWidth;
    private int mMaxColumns; // 除去title的最多列数

    public DetailTableLayout(Context context) {
        this(context, null);
    }

    public DetailTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void init(List<TableRowItem> data, int width){
        setVisibility(GONE);
        mData = data;
        mWidth = width;
        mMaxColumns = DEFAULT_ITEM_COUNT;
        //findMaxColumns(); 先注释掉，最多只显示6列

        updateLayout();
    }

    /**
     * 查找属性值最多的，最小值为6
     */
    private void findMaxColumns(){
        if(mData == null){
            return;
        }
        int maxColumn = 0;
        for(TableRowItem rowItem : mData){
            if(rowItem.items.size() > maxColumn){
                maxColumn = rowItem.items.size();
            }
        }
        if(maxColumn > DEFAULT_ITEM_COUNT){ // 最小设为6
            mMaxColumns = maxColumn;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void updateLayout(){
        if(mData == null){
            return;
        }
        addBorderLine(BORDER_THICKNESS_BOLD);
        for (TableRowItem rowData : mData){
            addTableRow(rowData);
        }
        addBorderLine(BORDER_THICKNESS_NORMAL); // 上面的每一行结尾处添加了BORDER_THICKNESS_NORMAL
    }

    private void addBorderLine(@BorderThickness int height){ // height in dip
        View line = new View(mContext);
        line.setBackgroundColor(0xff98989f);
        addView(line);
        line.getLayoutParams().height = Utils.dip2px(mContext, height);
    }

    private void addTableRow(TableRowItem rowData){

        TableRow tableRow = new TableRow(mContext);
        addTitleToTableRow(tableRow, rowData.title);
        addTitleRightBorder(tableRow);
        int itemWidth = (mWidth - Utils.dip2px(mContext, TITLE_WIDTH))/ mMaxColumns;
        List<String> items = rowData.items;
        for (String item : items){
            int index = items.indexOf(item);
            TextView propText = new TextView(mContext);
            if(index == rowData.selectedIndex){
                propText.setSelected(true);
                propText.setTypeface(null, Typeface.BOLD);
            }
            propText.setGravity(Gravity.CENTER);
            propText.setBackgroundResource(R.drawable.detail_prop_item_bg);
            propText.setTextColor(getResources().getColorStateList(R.color.detail_prop_item_text_color));
            propText.setText(item);
            propText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE);
            tableRow.addView(propText, new TableRow.LayoutParams(itemWidth, Utils.dip2px(mContext, ROW_HEIGHT)));
        }

        addView(tableRow);
        addBorderLine(BORDER_THICKNESS_NORMAL);
    }

    private void addTitleToTableRow(TableRow tableRow, String title){
        TextView titleText = new TextView(mContext);
        titleText.setTextColor(0xff222222);
        titleText.setText(title);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE);
        titleText.setGravity(Gravity.CENTER);
        tableRow.addView(titleText, new TableRow.LayoutParams(Utils.dip2px(mContext, TITLE_WIDTH), MATCH_PARENT));
    }

    private void addTitleRightBorder(TableRow tableRow){
        View line = new View(mContext);
        line.setBackgroundColor(0xff98989f);
        tableRow.addView(line, new TableRow.LayoutParams(Utils.dip2px(mContext, BORDER_THICKNESS_NORMAL), MATCH_PARENT));
    }

    public void showIn(ViewGroup parent){
        parent.addView(this);
        getLayoutParams().height = MATCH_PARENT;
        getLayoutParams().width = MATCH_PARENT;
        setVisibility(VISIBLE);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);
        startAnimation(fadeIn);
    }

    public static class TableRowItem{
        public TableRowItem(String title, List<String> items, int selectedIndex){
            this.title = title;
            this.items = items;
            this.selectedIndex = selectedIndex;
        }
        public String title;
        public int selectedIndex;
        public List<String> items;;
    }
}
