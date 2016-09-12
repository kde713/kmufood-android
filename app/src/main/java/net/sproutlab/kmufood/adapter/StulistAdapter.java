package net.sproutlab.kmufood.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.data.stuFooddata;
import net.sproutlab.kmufood.dialog.FoodInfoDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kde713 on 2016. 9. 7..
 */
public class StulistAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews = new ArrayList<>();
    private String[] mTitle;
    private String[][] mFood;
    private String[][] mPrice;
    private int mCount;
    private float mBaseElevation;
    private Context c;

    public StulistAdapter(Context c) {
        stuFooddata mData = new stuFooddata(c);
        this.c = c;
        this.mFood = mData.loadMenu();
        this.mPrice = mData.loadPrice();
        this.mCount = mData.getFoodCount();
        this.mTitle = new String[]{
                c.getString(R.string.monday), c.getString(R.string.tuesday),
                c.getString(R.string.wednesday), c.getString(R.string.thursday),
                c.getString(R.string.friday), c.getString(R.string.saturday),
                c.getString(R.string.sunday)
        };
        for (String ignored : this.mTitle) {
            this.mViews.add(null);
        }
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.card_stufood, container, false);

        /* Setting up view */
        int[] containerset = {
                R.id.stu_section1,
                R.id.stu_section2,
                R.id.stu_section3_1, R.id.stu_section3_2,
                R.id.stu_section4,
                R.id.stu_section5,
                R.id.stu_section6,
                R.id.stu_section7,
                R.id.stu_section8,
                R.id.stu_section9,
                R.id.stu_section10
        };
        int[] foodset = {
                R.id.stu_s1_menu,
                R.id.stu_s2_menu,
                R.id.stu_s3_1_menu, R.id.stu_s3_2_menu,
                R.id.stu_s4_menu,
                R.id.stu_s5_menu,
                R.id.stu_s6_menu,
                R.id.stu_s7_menu,
                R.id.stu_s8_menu,
                R.id.stu_s9_menu,
                R.id.stu_s10_menu
        };
        int[] priceset = {
                R.id.stu_s1_price,
                R.id.stu_s2_price,
                R.id.stu_s3_1_price, R.id.stu_s3_2_price,
                R.id.stu_s4_price,
                R.id.stu_s5_price,
                R.id.stu_s6_price,
                R.id.stu_s7_price,
                R.id.stu_s8_price,
                R.id.stu_s9_price,
                R.id.stu_s10_price
        };

        ((TextView) view.findViewById(R.id.card_title)).setText(mTitle[position]);

        int emptyCount = 0;

        for (int i = 0; i < mCount; i++) {
            if (mFood[position][i].isEmpty()) {
                view.findViewById(containerset[i]).setVisibility(View.GONE);
                emptyCount++;
            } else {
                ((TextView) view.findViewById(foodset[i])).setText(mFood[position][i]);
                ((TextView) view.findViewById(priceset[i])).setText(mPrice[position][i]);
            }
        }

        if (mFood[position][2].isEmpty() && mFood[position][3].isEmpty()) {
            view.findViewById(R.id.stu_section3).setVisibility(View.GONE);
        }

        if (emptyCount >= mCount) {
            view.findViewById(R.id.card_content).setVisibility(View.GONE);
            view.findViewById(R.id.card_nomsg).setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.btn_foodinfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new FoodInfoDialog(c, c.getString(R.string.stu_title), c.getString(R.string.temp_foodinfo_stufood))).show();
            }
        });

        container.addView(view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
//        super.destroyItem(container, position, object);
    }
}
