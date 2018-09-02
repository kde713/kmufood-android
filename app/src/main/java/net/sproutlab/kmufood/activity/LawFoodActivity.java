package net.sproutlab.kmufood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.adapter.LawListAdapter;
import net.sproutlab.kmufood.adapter.ShadowTransformer;
import net.sproutlab.kmufood.dialog.OtherFoodDialog;
import net.sproutlab.kmufood.dialog.OtherFoodInterface;
import net.sproutlab.kmufood.utils.PrefHelper;

import java.util.Calendar;

public class LawFoodActivity extends AppCompatActivity implements View.OnClickListener, OtherFoodInterface {

    private final String FOOD_CODE = "law";

    private PrefHelper prefHelper;
    private ImageButton btn_favorite;
    private boolean isFavorite = false;

    private ViewPager mViewPager;
    private LawListAdapter mAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawfood);

        btn_favorite = (ImageButton) findViewById(R.id.btn_favorite);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        prefHelper = new PrefHelper(this);
        mAdapter = new LawListAdapter(this);
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mAdapter);

        Calendar c = Calendar.getInstance();
        int curindex = c.get(Calendar.DAY_OF_WEEK);
        if (curindex == 1 || curindex == 7) curindex = 0;
        else curindex -= 2;

        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(curindex);

        updatePreferIndicator();

        findViewById(R.id.btn_otherfood).setOnClickListener(this);
        btn_favorite.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePreferIndicator();
    }

    private void updatePreferIndicator() {
        if (prefHelper.getPreferFood().equals(FOOD_CODE)) {
            btn_favorite.setImageResource(R.drawable.ic_star_on);
            isFavorite = true;
        } else {
            btn_favorite.setImageResource(R.drawable.ic_star_off);
            isFavorite = false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_favorite:
                if (!isFavorite) {
                    prefHelper.setPreferFood(FOOD_CODE);
                    btn_favorite.setImageResource(R.drawable.ic_star_on);
                    isFavorite = true;
                }
                break;
            case R.id.btn_otherfood:
                (new OtherFoodDialog(LawFoodActivity.this)).show();
                break;
        }
    }

    @Override
    public void switchAction(String foodcode) {
        switch (foodcode) {
            case "stu":
                startActivity((new Intent(this, StuFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "staff":
                startActivity((new Intent(this, StaffFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "dorm":
                startActivity((new Intent(this, DormFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "chung":
                startActivity((new Intent(this, ChungFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }
}