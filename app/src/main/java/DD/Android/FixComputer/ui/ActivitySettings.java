package DD.Android.FixComputer.ui;

import DD.Android.FixComputer.R;
import DD.Android.FixComputer.core.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.umeng.analytics.MobclickAgent;
import roboguice.inject.InjectView;
import roboguice.util.RoboAsyncTask;

public class ActivitySettings extends ActivityFC {

//    @InjectView(R.id.rl_isNotifi)
//    protected RelativeLayout rl_isNotifi;
    @InjectView(R.id.rl_isSoundNotifi)
    protected RelativeLayout rl_isSoundNotifi;
    @InjectView(R.id.rl_isShockNotifi)
    protected RelativeLayout rl_isShockNotifi;
    @InjectView(R.id.rl_isLightNotifi)
    protected RelativeLayout rl_isLightNotifi;
    @InjectView(R.id.rl_isNotDisturb)
    protected RelativeLayout rl_isNotDisturb;

    @InjectView(R.id.cb_isNotifi)
    protected CheckBox cb_isNotifi;
    @InjectView(R.id.cb_isSoundNotifi)
    protected CheckBox cb_isSoundNotifi;
    @InjectView(R.id.cb_isShockNotifi)
    protected CheckBox cb_isShockNotifi;
    @InjectView(R.id.cb_isLightNotifi)
    protected CheckBox cb_isLightNotifi;
    @InjectView(R.id.cb_isNotDisturb)
    protected CheckBox cb_isNotDisturb;

//    @Inject
//    protected UserAvatarLoader avatarLoader;

    private RoboAsyncTask<Boolean> task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_settings);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("设置");
        PropertiesController.readConfiguration();
        settings_to_view();
    }

    @Override
    protected void onDestroy() {
        PropertiesController.writeConfiguration();
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void settings_to_view() {
        notifi_rl_enable(sets().isNotifi);
        cb_isNotifi.setChecked(sets().isNotifi);
        cb_isSoundNotifi.setChecked(sets().isSoundNotifi);
        cb_isShockNotifi.setChecked(sets().isShockNotifi);
        cb_isLightNotifi.setChecked(sets().isLightNotifi);
        cb_isNotDisturb.setChecked(sets().isNotDisturb);
    }

    private void notifi_rl_enable(boolean isEnable) {
        cb_isSoundNotifi.setEnabled(isEnable);
        cb_isShockNotifi.setEnabled(isEnable);
        cb_isLightNotifi.setEnabled(isEnable);
        cb_isNotDisturb.setEnabled(isEnable);
//        rl_isSoundNotifi.setEnabled(isEnable);
//        rl_isLightNotifi.setEnabled(isEnable);
//        rl_isShockNotifi.setEnabled(isEnable);
//        rl_isNotDisturb.setEnabled(isEnable);
    }

    public void handleCbChange(View v){
        CheckBox cb = (CheckBox)v;
        if(cb_isNotifi.equals(v)){
            notifi_rl_enable(cb.isChecked());
            sets().isNotifi = cb.isChecked();
        }
        else if(cb_isSoundNotifi.equals(v)){
            sets().isSoundNotifi = cb.isChecked();
        }
        else if(cb_isShockNotifi.equals(v)){
            sets().isShockNotifi = cb.isChecked();
        }
        else if(cb_isLightNotifi.equals(v)){
            sets().isLightNotifi = cb.isChecked();
        }
        else if(cb_isNotDisturb.equals(v)){
            sets().isNotDisturb = cb.isChecked();
        }
    }

    private Settings sets(){
        return Settings.getFactory();
    }

    @Override
    protected void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        MobclickAgent.onResume(this);
    }

//
//    private void dialog_for_plus() {
//        LayoutInflater inflater = LayoutInflater.from(this);
//        v_et = inflater.inflate(R.layout.dialog_edittext, null);
//
//        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface d, int which) {
//                EditText et_message = (EditText)v_et.findViewById(R.id.et_message);
//                plus = et_message.getText().toString();
//                update_status(action);
//            }
//        };
//
//        new AlertDialog.Builder(this)
//                .setTitle("plus")
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .setView(v_et)
//                .setNegativeButton(getString(android.R.string.cancel), null)
//                .setPositiveButton(getString(android.R.string.ok), OkClick)
//                .show();
//
//    }
//
//    private void problem_to_view() {
//        tv_status.setText(problem.getStatusStr());
//        if("order".equals(problem.getStatus()))
//            menu_cancel.setVisible(true);
////        ll_status_recodings.removeAllViews();
//        int index = 1;
//        final float scale = getResources().getDisplayMetrics().density;
//        if(problem.getStatus_recodings() != null)
//            for(StatusRecoding sr : problem.getStatus_recodings() ){
//                if(index > 3)
//                    break;
//                RelativeLayout rl = getRl(index);
//
//                TextView tvl = getTvl(index);
//                TextView tvr = getTvr(index);
//                tvl.setText(sr.getStatusStr());
//                tvr.setText(PrettyDateFormat.defaultFormat(sr.getCreated_at()));
//
//                rl.setVisibility(View.VISIBLE);
//                index++;
//
//            }
//    }
//
//    private void start_get_problem(View v) {
//        if (task != null) {
//            return;
//        }
//
//        progressDialogShow(this);
//
//        task = new RoboAsyncTask<Boolean>(this) {
//            public Boolean call() throws Exception {
//                problem = ServiceFC.getProblem(problem.get_id(), new DeviceUuidFactory(ActivitySettings.this).getDeviceUuid().toString());
//                return true;
//            }
//
//            @Override
//            protected void onException(Exception e) throws RuntimeException {
//                Toaster.showLong(ActivitySettings.this, "获取故障信息失败");
//            }
//
//            @Override
//            public void onSuccess(Boolean relationship) {
//                problem_to_view();
//            }
//
//            @Override
//            protected void onFinally() throws RuntimeException {
//                progressDialogDismiss();
//                task = null;
//            }
//
//            @Override
//            public boolean cancel(boolean mayInterruptIfRunning) {
//                return task.cancel(mayInterruptIfRunning);
//            }
//        };
//        task.execute();
//    }
////
//    public void update_status(String paction) {
//        if (task != null) {
//            return;
//        }
//
//        action = paction;
//
//        progressDialogShow(this);
//
//        task = new RoboAsyncTask<Boolean>(this) {
//            public Boolean call() throws Exception {
//                problem = ServiceFC.updateProblem(problem.get_id(),new DeviceUuidFactory(ActivitySettings.this).getDeviceUuid().toString(), action,plus);
//                return true;
//            }
//
//            @Override
//            protected void onException(Exception e) throws RuntimeException {
//                Toaster.showLong(ActivitySettings.this, "取消失败,请检查网络原因或当前状态不能被取消");
//            }
//
//            @Override
//            public void onSuccess(Boolean relationship) {
//                menu_cancel.setVisible(false);
//                problem_to_view();
//            }
//
//            @Override
//            protected void onFinally() throws RuntimeException {
//                progressDialogDismiss();
//                task = null;
//            }
//
//            @Override
//            public boolean cancel(boolean mayInterruptIfRunning) {
//                return task.cancel(mayInterruptIfRunning);
//            }
//        };
//        task.execute();
//    }
}
