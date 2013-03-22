
package DD.Android.FixComputer.ui;

import DD.Android.FixComputer.R;
import DD.Android.FixComputer.R.id;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.*;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.slidingmenu.lib.SlidingMenu;
import roboguice.inject.InjectView;
import roboguice.util.RoboAsyncTask;

import java.util.ArrayList;
import java.util.List;

import static com.github.kevinsawicki.http.HttpRequest.post;

/**
 * Activity to authenticate the ABUser against an API (example API on Parse.com)
 */
public class ActivityMain extends
        RoboSherlockFragmentActivity {

    @InjectView(id.btn_toolbar_0)
    private TextView btn_toolbar_0;

    @InjectView(id.btn_toolbar_1)
    private TextView btn_toolbar_1;

    @InjectView(id.btn_toolbar_2)
    private TextView btn_toolbar_2;

    @InjectView(id.btn_toolbar_3)
    private TextView btn_toolbar_3;

    List<TextView> list_btn;


    private RoboAsyncTask<Boolean> authenticationTask;

    public AdapterFC mSectionsPagerAdapter;
    public ViewPager mViewPager;

    SlidingMenu menu;

    int toolbar_item_selected_bg,toolbar_item_bg;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);


        setContentView(R.layout.act_carousel);
        mSectionsPagerAdapter = new AdapterFC(
                getSupportFragmentManager());

        list_btn = new ArrayList<TextView>();

        list_btn.add(btn_toolbar_0);
        list_btn.add(btn_toolbar_1);
        list_btn.add(btn_toolbar_2);
        list_btn.add(btn_toolbar_3);

        toolbar_item_selected_bg = getResources().getColor(R.color.toolbar_item_selected_bg);
        toolbar_item_bg = getResources().getColor(R.color.toolbar_item_bg);

        btn_toolbar_1.setBackgroundColor(toolbar_item_selected_bg);


        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pop_btn_toolbar(position);
//                set_btn_toolbar_press(position);
                if (position == 3) {
                    show_sliding_menu();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        init_sliding_menu();
    }

//    private void set_btn_toolbar_press(int position) {
//        Button btn = (Button) getViewResourceByName("btn_toolbar_" + String.valueOf(position));
//        btn.setPressed(true);
//    }

    private void pop_btn_toolbar(int id) {
        for(TextView tv : list_btn){
            tv.setBackgroundColor(toolbar_item_bg);
        }
        list_btn.get(id).setBackgroundColor(toolbar_item_selected_bg);
    }

    private void init_sliding_menu() {
        menu = new SlidingMenu(this);
        menu.setSlidingEnabled(false);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
//        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_frame);

    }

    public void show_sliding_menu() {
        menu.showContent();
    }


    private View getViewResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "id", packageName);
        return findViewById(resId);
    }

    public void handleBtnClick(View view) {
        int id = Integer.parseInt(String.valueOf(((TextView) view).getContentDescription()));
        mViewPager.setCurrentItem(id);
//        if(id == 3){
//            show_sliding_menu();
//        }
    }

}
