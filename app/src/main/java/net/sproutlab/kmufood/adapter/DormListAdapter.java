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
public class DormListAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> viewList;
    private String[] dayTitles;
    private Sikdan[][] dormMenu;
    private float mBaseElevation;
    private Context c;

    public DormListAdapter(Context c) {
        this.c = c;
        this.dormMenu = (new MenuDataHelper(c)).loadDormFood();
        this.dayTitles = new String[]{
                c.getString(R.string.monday), c.getString(R.string.tuesday),
                c.getString(R.string.wednesday), c.getString(R.string.thursday),
                c.getString(R.string.friday), c.getString(R.string.saturday),
                c.getString(R.string.sunday)
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
                .inflate(R.layout.card_dormfood, container, false);

        /* Setting up view */
        int[] containerset = {
                R.id.dorm_section1,
                R.id.dorm_section2_1, R.id.dorm_section2_2,
                R.id.dorm_section3
        };
        int[] foodset = {
                R.id.dorm_s1_menu,
                R.id.dorm_s2_1_menu, R.id.dorm_s2_2_menu,
                R.id.dorm_s3_menu
        };
        int[] priceset = {
                R.id.dorm_s1_price,
                R.id.dorm_s2_1_price, R.id.dorm_s2_2_price,
                R.id.dorm_s3_price
        };

        ((TextView) view.findViewById(R.id.card_title)).setText(dayTitles[position]);

        int emptyCount = 0;

        for (int i = 0; i < dormMenu[position].length; i++) {
            if (dormMenu[position][i].menu.isEmpty()) {
                view.findViewById(containerset[i]).setVisibility(View.GONE);
                emptyCount++;
            } else {
                ((TextView) view.findViewById(foodset[i])).setText(dormMenu[position][i].menu);
                ((TextView) view.findViewById(priceset[i])).setText(dormMenu[position][i].price);
            }
        }

        if (dormMenu[position][1].menu.isEmpty() && dormMenu[position][2].menu.isEmpty()) {
            view.findViewById(R.id.dorm_section2).setVisibility(View.GONE);
        }

        if (emptyCount >= dormMenu[position].length) {
            view.findViewById(R.id.card_content).setVisibility(View.GONE);
            view.findViewById(R.id.card_nomsg).setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.btn_foodinfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new FoodInfoDialog(c, c.getString(R.string.dorm_title), c.getString(R.string.temp_foodinfo_dormfood))).show();
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
