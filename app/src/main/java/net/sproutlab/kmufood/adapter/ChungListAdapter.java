package net.sproutlab.kmufood.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.api.models.Sikdan;
import net.sproutlab.kmufood.dialog.FoodInfoDialog;
import net.sproutlab.kmufood.utils.MenuDataHelper;
import net.sproutlab.kmufood.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kde713 on 2016. 9. 7..
 */
public class ChungListAdapter extends PagerAdapter implements CardAdapter {
    private List<CardView> viewList;

    private String[] dayTitles;
    private Sikdan[][] chungMenu;
    private float mBaseElevation;
    private Context c;

    public ChungListAdapter(Context c) {
        this.c = c;
        this.chungMenu = (new MenuDataHelper(c)).loadChungFood();
        this.dayTitles = new String[]{
                c.getString(R.string.monday), c.getString(R.string.tuesday),
                c.getString(R.string.wednesday), c.getString(R.string.thursday),
                c.getString(R.string.friday), c.getString(R.string.saturday)
        };
        this.viewList = new ArrayList<>(Collections.<CardView>nCopies(this.dayTitles.length, null));
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return viewList.get(position);
    }

    @Override
    public int getCount() {
        return dayTitles.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
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

        ((TextView) view.findViewById(R.id.card_title)).setText(dayTitles[position]);

        int emptyCount = 0;

        for (int i = 0; i < chungMenu[position].length; i++) {
            if (chungMenu[position][i].menu.isEmpty()) {
                view.findViewById(containerset[i]).setVisibility(View.GONE);
                emptyCount++;
            } else {
                ((TextView) view.findViewById(foodset[i])).setText(chungMenu[position][i].menu);
                ((TextView) view.findViewById(priceset[i])).setText(chungMenu[position][i].price);
            }
        }

        if (emptyCount >= chungMenu[position].length) {
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
        CardView cardView = view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        viewList.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
