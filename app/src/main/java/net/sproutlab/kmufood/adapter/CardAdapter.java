package net.sproutlab.kmufood.adapter;

import android.support.v7.widget.CardView;

/**
 * Created by kde713 on 2016. 9. 7..
 */
interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();

}
