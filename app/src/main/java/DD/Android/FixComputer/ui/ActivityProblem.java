package DD.Android.FixComputer.ui;

import DD.Android.FixComputer.R;
import DD.Android.FixComputer.core.*;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.github.kevinsawicki.wishlist.Toaster;
import com.umeng.analytics.MobclickAgent;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import roboguice.util.RoboAsyncTask;

import static DD.Android.FixComputer.core.Constants.Extra.PROBLEM;
import static DD.Android.FixComputer.core.Constants.Other.NOTIFICATION_ID;

public class ActivityProblem extends ActivityFC {

    @InjectView(R.id.tv_created_at)
    protected TextView tv_created_at;

    @InjectView(R.id.tv_status)
    protected TextView tv_status;

    @InjectView(R.id.ll_status_recodings)
    protected LinearLayout ll_status_recodings;
    @InjectView(R.id.rl_status_recoding_1)
    protected RelativeLayout rl_status_recoding_1;
    @InjectView(R.id.rl_status_recoding_2)
    protected RelativeLayout rl_status_recoding_2;
    @InjectView(R.id.rl_status_recoding_3)
    protected RelativeLayout rl_status_recoding_3;

    @InjectView(R.id.label_status_recoding_1)
    protected TextView label_status_recoding_1;
    @InjectView(R.id.label_status_recoding_2)
    protected TextView label_status_recoding_2;
    @InjectView(R.id.label_status_recoding_3)
    protected TextView label_status_recoding_3;

    @InjectView(R.id.tv_status_recoding_1)
    protected TextView tv_status_recoding_1;
    @InjectView(R.id.tv_status_recoding_2)
    protected TextView tv_status_recoding_2;
    @InjectView(R.id.tv_status_recoding_3)
    protected TextView tv_status_recoding_3;

    @InjectExtra(PROBLEM)
    protected Problem problem;

    String action= null,plus = "";

    View v_et;

    MenuItem menu_cancel;

//    @Inject
//    protected UserAvatarLoader avatarLoader;

    private RoboAsyncTask<Boolean> task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_problem);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tv_status.setText(problem.getStatusStr());
        tv_created_at.setText(PrettyDateFormat.defaultFormat(problem.getCreated_at()));

        start_get_problem(null);
        NotificationManager messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        messageNotificatioManager.cancel(NOTIFICATION_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.problem, menu);

        menu_cancel = menu.findItem(R.id.menu_cancel);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                start_get_problem(null);
                return true;
            case R.id.menu_cancel:
                plus = "";
                update_status("cancel");
                return true;
            default:
                if(item.getTitleCondensed() != null){
                    String tc = item.getTitleCondensed().toString();
                    action = tc;
                    if("token".equals(action) || "paid".equals(action)){
                        dialog_for_plus();
                        return true;
                    }
                    else if("contacted".equals(action) || "visited".equals(action) || "repaired".equals(action) || "finish".equals(action)){
                        plus = "";
                        update_status(action);
                        return true;
                    }
                }
                return super.onOptionsItemSelected(item);
        }
    }

    private void dialog_for_plus() {
        LayoutInflater inflater = LayoutInflater.from(this);
        v_et = inflater.inflate(R.layout.dialog_edittext, null);

        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface d, int which) {
                EditText et_message = (EditText)v_et.findViewById(R.id.et_message);
                plus = et_message.getText().toString();
                update_status(action);
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("plus")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(v_et)
                .setNegativeButton(getString(android.R.string.cancel), null)
                .setPositiveButton(getString(android.R.string.ok), OkClick)
                .show();

    }

    private void problem_to_view() {
        tv_status.setText(problem.getStatusStr());
        if("order".equals(problem.getStatus()))
            menu_cancel.setVisible(true);
//        ll_status_recodings.removeAllViews();
        int index = 1;
        final float scale = getResources().getDisplayMetrics().density;
        if(problem.getStatus_recodings() != null)
            for(StatusRecoding sr : problem.getStatus_recodings() ){
                if(index > 3)
                    break;
                RelativeLayout rl = getRl(index);

                TextView tvl = getTvl(index);
                TextView tvr = getTvr(index);
                tvl.setText(sr.getStatusStr());
                tvr.setText(PrettyDateFormat.defaultFormat(sr.getCreated_at()));

                rl.setVisibility(View.VISIBLE);
                index++;

//                RelativeLayout rl = new RelativeLayout(this,null,R.style.Style_Patterns_Content);
//                rl.setPadding(10,0,10,0);
//                int pixels = (int) (50 * scale + 0.5f);
//                rl.setMinimumHeight(pixels);
//
//                rl.setMinimumHeight(50);
//                rl.setGravity(RelativeLayout.CENTER_IN_PARENT);
//
//                TextView tvl = new TextView(this,null,R.style.Patterns_Text_Left);
//                tvl.setTextColor(getResources().getColor(R.color.font_attribute));
//                tvl.setText(sr.getStatusStr());
//                RelativeLayout.LayoutParams pl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//                pl.addRule(RelativeLayout.ALIGN_PARENT_LEFT, tvl.getId());
//
//                rl.addView(tvl);
//                TextView tvr = new TextView(this,null,R.style.Profile_Item_Right);
//                tvr.setTextColor(getResources().getColor(R.color.font_value));
//                tvr.setText(PrettyDateFormat.defaultFormat(sr.getCreated_at()));
//                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//                p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, tvr.getId());
//                tvr.setLayoutParams(p);
//                rl.addView(tvr);
//                LayoutInflater layoutInflater = (LayoutInflater)
//                        this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                ll_status_recodings.addView(layoutInflater.inflate(R.layout.include_patterns_divideline, ll_status_recodings, false));
//                ll_status_recodings.addView(rl);
            }
    }

    private TextView getTvr(int index) {
        switch (index){
            case 1:
                return tv_status_recoding_1;
            case 2:
                return tv_status_recoding_2;
            case 3:
                return tv_status_recoding_3;
            default:
                return null;
        }

    }

    private TextView getTvl(int index) {
        switch (index){
            case 1:
                return label_status_recoding_1;
            case 2:
                return label_status_recoding_2;
            case 3:
                return label_status_recoding_3;
            default:
                return null;
        }
    }

    private RelativeLayout getRl(int index) {
        switch (index){
            case 1:
                return rl_status_recoding_1;
            case 2:
                return rl_status_recoding_2;
            case 3:
                return rl_status_recoding_3;
            default:
                return null;
        }
    }

    private void start_get_problem(View v) {
        if (task != null) {
            return;
        }

        progressDialogShow(this);

        task = new RoboAsyncTask<Boolean>(this) {
            public Boolean call() throws Exception {
                problem = ServiceFC.getProblem(problem.get_id(), new DeviceUuidFactory(ActivityProblem.this).getDeviceUuid().toString());
                return true;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                Toaster.showLong(ActivityProblem.this, "获取故障信息失败");
            }

            @Override
            public void onSuccess(Boolean relationship) {
                problem_to_view();
            }

            @Override
            protected void onFinally() throws RuntimeException {
                progressDialogDismiss();
                task = null;
            }

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return task.cancel(mayInterruptIfRunning);
            }
        };
        task.execute();
    }
//
    public void update_status(String aciton) {
        if (task != null) {
            return;
        }

        this.action = action;

        progressDialogShow(this);

        task = new RoboAsyncTask<Boolean>(this) {
            public Boolean call() throws Exception {
                problem = ServiceFC.updateProblem(problem.get_id(),new DeviceUuidFactory(ActivityProblem.this).getDeviceUuid().toString(), action,plus);
                return true;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                Toaster.showLong(ActivityProblem.this, "取消失败,请检查网络原因或当前状态不能被取消");
            }

            @Override
            public void onSuccess(Boolean relationship) {
                menu_cancel.setVisible(false);
                problem_to_view();
            }

            @Override
            protected void onFinally() throws RuntimeException {
                progressDialogDismiss();
                task = null;
            }

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return task.cancel(mayInterruptIfRunning);
            }
        };
        task.execute();
    }
}
