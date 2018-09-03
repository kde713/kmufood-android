package net.sproutlab.kmufood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.adapter.ChungListAdapter;
import net.sproutlab.kmufood.adapter.ShadowTransformer;
import net.sproutlab.kmufood.dialog.OtherFoodDialog;
import net.sproutlab.kmufood.dialog.OtherFoodInterface;
import net.sproutlab.kmufood.utils.PrefHelper;

import java.util.Calendar;

public class ChungFoodActivity extends AppCompatActivity implements View.OnClickListener, OtherFoodInterface {

    private final String FOOD_CODE = "chung";

    private PrefHelper prefHelper;
    private ImageButton btn_favorite;
    private boolean isFavorite;

    private long backPressedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chungfood);

        btn_favorite = findViewById(R.id.btn_favorite);
        ViewPager viewPager = findViewById(R.id.viewPager);

        prefHelper = new PrefHelper(this);
        ChungListAdapter listAdapter = new ChungListAdapter(this);
        ShadowTransformer cardShadowTransformer = new ShadowTransformer(viewPager, listAdapter);

        Calendar c = Calendar.getInstance();
        int curindex = c.get(Calendar.DAY_OF_WEEK);
        if (curindex == 1) curindex = 0;
        else curindex -= 2;

        viewPager.setAdapter(listAdapter);
        viewPager.setPageTransformer(true, cardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(curindex);

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
                (new OtherFoodDialog(ChungFoodActivity.this)).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedAt + 2000 > System.currentTimeMillis()) {
            this.finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.msg_exit), Toast.LENGTH_SHORT).show();
        }
        backPressedAt = System.currentTimeMillis();
    }

    @Override
    public void switchAction(String foodcode) {
        if (!foodcode.equals(FOOD_CODE)) {
            Intent newActivity;
            switch (foodcode) {
                case "chung":
                    newActivity = new Intent(this, ChungFoodActivity.class);
                    break;
                case "stu":
                    newActivity = new Intent(this, StuFoodActivity.class);
                    break;
                case "law":
                    newActivity = new Intent(this, LawFoodActivity.class);
                    break;
                case "staff":
                    newActivity = new Intent(this, StaffFoodActivity.class);
                    break;
                case "dorm":
                    newActivity = new Intent(this, DormFoodActivity.class);
                    break;
                default:
                    return;
            }
            startActivity(newActivity);
        }
    }
}