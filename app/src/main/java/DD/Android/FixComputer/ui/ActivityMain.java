
package DD.Android.FixComputer.ui;

import DD.Android.FixComputer.R;
import DD.Android.FixComputer.R.id;
import DD.Android.FixComputer.core.PropertiesController;
import DD.Android.FixComputer.core.Settings;
import DD.Android.FixComputer.service.ProblemsService;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.actionbarsherlock.view.MenuItem;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.NotificationType;
import com.umeng.fb.UMFeedbackService;
import com.umeng.newxp.common.ExchangeConstants;
import com.umeng.newxp.controller.ExchangeDataService;
import com.umeng.newxp.view.ExchangeViewManager;
import com.umeng.update.UmengUpdateAgent;
import roboguice.inject.InjectView;
import roboguice.util.RoboAsyncTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    int toolbar_item_selected_bg, toolbar_item_bg;

    Intent serviceIntent;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);


        setContentView(R.layout.act_carousel);
        mSectionsPagerAdapter = new AdapterFC(this,
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
                switch (position) {
                    case 2:
                        show_sliding_menu();
                        break;
//                    case 3:
//                        show_menu();
//                        break;
                    default:
                        hide_sliding_menu();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        init_sliding_menu();
        MobclickAgent.updateOnlineConfig(this);
        UmengUpdateAgent.update(this);
        UMFeedbackService.enableNewReplyNotification(this, NotificationType.AlertDialog);
        MobclickAgent.setDebugMode(true);
        PropertiesController.readConfiguration();
        start_message_service();
    }

    private void start_message_service() {
        serviceIntent = new Intent(this, ProblemsService.class);
        startService(serviceIntent);
    }

//    private void set_btn_toolbar_press(int position) {
//        Button btn = (Button) getViewResourceByName("btn_toolbar_" + String.valueOf(position));
//        btn.setPressed(true);
//    }

    private void pop_btn_toolbar(int id) {
        for (TextView tv : list_btn) {
            tv.setBackgroundColor(toolbar_item_bg);
        }
        list_btn.get(id).setBackgroundColor(toolbar_item_selected_bg);
    }

    private void init_sliding_menu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
//        menu.setShadowWidthRes(R.dimen.shadow_width);
//        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.fragment_menu);

        hide_sliding_menu();
    }

    private void hide_sliding_menu() {
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setFadeDegree(1.0f);
    }

    private void show_sliding_menu() {
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                menu.toggle();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void handleMenu(View v) {
        show_menu();
    }

    public void show_menu() {
        if (!menu.isMenuShowing())
            menu.showMenu(true);
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

    public void handleAd(View view) {
        ExchangeDataService service = new ExchangeDataService();
        new ExchangeViewManager(this, service)
                .addView(ExchangeConstants.type_list_curtain, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void menu_click(View v) {
        TextView menu_feedback = (TextView) findViewById(id.menu_feedback);
        TextView menu_exit = (TextView) findViewById(id.menu_exit);
        TextView menu_full_exit = (TextView) findViewById(id.menu_full_exit);
        TextView menu_settings = (TextView) findViewById(id.menu_settings);

        if (menu_feedback.equals(v)) {
            UMFeedbackService.openUmengFeedbackSDK(this);
        } else if (menu_exit.equals(v)) {
            DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface d, int which) {
                    exit();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("确认要退出？")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setNegativeButton(getString(android.R.string.cancel), null)
                    .setPositiveButton(getString(android.R.string.ok), OkClick)
                    .show();
        } else if (menu_full_exit.equals(v)) {
            DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface d, int which) {

                    full_exit();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("确认要完全退出?")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage("这样将不再接收故障状态更改信息提醒!")
                    .setNegativeButton(getString(android.R.string.cancel), null)
                    .setPositiveButton(getString(android.R.string.ok), OkClick)
                    .show();
        } else if (menu_settings.equals(v)) {
            startActivity(new Intent(this, ActivitySettings.class));
        }
    }

    private void full_exit() {
        stopService(serviceIntent);
        exit();
    }

    private void exit() {
        MobclickAgent.onKillProcess(ActivityMain.this);
        System.exit(0);
    }


}
