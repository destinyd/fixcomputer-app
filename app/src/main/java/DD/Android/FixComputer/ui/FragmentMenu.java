
package DD.Android.FixComputer.ui;

import DD.Android.FixComputer.R;
import DD.Android.FixComputer.R.id;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.view.MenuItem;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.UMFeedbackService;
import roboguice.inject.InjectView;

/**
 * Activity to authenticate the ABUser against an API (example API on Parse.com)
 */
public class FragmentMenu extends
        FragmentFC {
    //    @InjectView(id.lv_menu)
//    private ListView lv_menu;
    @InjectView(id.menu_exit)
    private TextView menu_exit;
    @InjectView(id.menu_feedback)
    private TextView menu_feedback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        View.OnClickListener clicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_click(v);
            }
        };
        menu_exit.setOnClickListener(clicker);

        menu_feedback.setOnClickListener(clicker);

    }

    public void menu_click(View v) {
        if (menu_feedback.equals(v)) {
            UMFeedbackService.openUmengFeedbackSDK(getActivity());
        } else if (menu_exit.equals(v)) {
            DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface d, int which) {
                    MobclickAgent.onKillProcess(getActivity());
                    System.exit(0);
                }
            };

            new AlertDialog.Builder(getActivity())
                    .setTitle("确认要退出？")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setNegativeButton(getString(android.R.string.cancel), null)
                    .setPositiveButton(getString(android.R.string.ok), OkClick)
                    .show();
        }
    }
}
