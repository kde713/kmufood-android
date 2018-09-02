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
import net.sproutlab.kmufood.api.models.Sikdan;
import net.sproutlab.kmufood.dialog.FoodInfoDialog;
import net.sproutlab.kmufood.utils.MenuDataHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kde713 on 2016. 9. 7..
 */
public class LawListAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews = new ArrayList<>();
    private String[] dayTitles;
    private Sikdan[][] lawMenu;
    private float mBaseElevation;
    private Context c;

    public LawListAdapter(Context c) {
        this.c = c;
        this.lawMenu = (new MenuDataHelper(c)).loadLawFood();
        this.dayTitles = new String[]{
                c.getString(R.string.monday), c.getString(R.string.tuesday),
                c.getString(R.string.wednesday), c.getString(R.string.thursday),
                c.getString(R.string.friday)
        };
        for (String ignored : this.dayTitles) {
            mViews.add(null);
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
        return dayTitles.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.card_lawfood, container, false);

        /* Setting up view */
        int[] containerset = {
                R.id.law_section1,
                R.id.law_section2,
                R.id.law_section3,
                R.id.law_section4,
                R.id.law_section5,
                R.id.law_section6,
                R.id.law_section7
        };
        int[] foodset = {
                R.id.law_s1_menu,
                R.id.law_s2_menu,
                R.id.law_s3_menu,
                R.id.law_s4_menu,
                R.id.law_s5_menu,
                R.id.law_s6_menu,
                R.id.law_s7_menu
        };
        int[] priceset = {
                R.id.law_s1_price,
                R.id.law_s2_price,
                R.id.law_s3_price,
                R.id.law_s4_price,
                R.id.law_s5_price,
                R.id.law_s6_price,
                R.id.law_s7_price
        };

        ((TextView) view.findViewById(R.id.card_title)).setText(dayTitles[position]);

        int emptyCount = 0;

        for (int i = 0; i < lawMenu[position].length; i++) {
            if (lawMenu[position][i].menu.isEmpty()) {
                view.findViewById(containerset[i]).setVisibility(View.GONE);
                emptyCount++;
            } else {
                ((TextView) view.findViewById(foodset[i])).setText(lawMenu[position][i].menu);
                ((TextView) view.findViewById(priceset[i])).setText(lawMenu[position][i].menu);
            }
        }

        if (emptyCount >= lawMenu[position].length) {
            view.findViewById(R.id.card_content).setVisibility(View.GONE);
            view.findViewById(R.id.card_nomsg).setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.btn_foodinfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new FoodInfoDialog(c, c.getString(R.string.law_title), c.getString(R.string.temp_foodinfo_lawfood))).show();
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
