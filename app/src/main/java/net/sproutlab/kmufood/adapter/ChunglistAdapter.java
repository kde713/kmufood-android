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
import net.sproutlab.kmufood.data.chungFooddata;
import net.sproutlab.kmufood.dialog.FoodInfoDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kde713 on 2016. 9. 7..
 */
public class ChunglistAdapter extends PagerAdapter implements CardAdapter {
    private List<CardView> mViews = new ArrayList<>();
    ;

    private String[] mTitle;
    private String[][] mFood;
    private String[][] mPrice;
    private int mCount;
    private float mBaseElevation;
    private Context c;

    public ChunglistAdapter(Context c) {
        chungFooddata mData = new chungFooddata(c);
        this.c = c;
        this.mFood = mData.loadMenu();
        this.mPrice = mData.loadPrice();
        this.mCount = mData.getFoodCount();
        this.mTitle = new String[]{
                c.getString(R.string.monday), c.getString(R.string.tuesday),
                c.getString(R.string.wednesday), c.getString(R.string.thursday),
                c.getString(R.string.friday), c.getString(R.string.saturday)
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
                .inflate(R.layout.card_chungfood, container, false);

        /* Setting up view */
        int[] containerset = {
                R.id.chung_section1,
                R.id.chung_section2,
                R.id.chung_section3,
                R.id.chung_section4,
                R.id.chung_section5,
                R.id.chung_section6,
                R.id.chung_section7
        };
        int[] foodset = {
                R.id.chung_s1_menu,
                R.id.chung_s2_menu,
                R.id.chung_s3_menu,
                R.id.chung_s4_menu,
                R.id.chung_s5_menu,
                R.id.chung_s6_menu,
                R.id.chung_s7_menu
        };
        int[] priceset = {
                R.id.chung_s1_price,
                R.id.chung_s2_price,
                R.id.chung_s3_price,
                R.id.chung_s4_price,
                R.id.chung_s5_price,
                R.id.chung_s6_price,
                R.id.chung_s7_price
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

        if (emptyCount >= mCount) {
            view.findViewById(R.id.card_content).setVisibility(View.GONE);
            view.findViewById(R.id.card_nomsg).setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.btn_foodinfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new FoodInfoDialog(c, c.getString(R.string.chung_title), c.getString(R.string.temp_foodinfo_chungfood))).show();
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
