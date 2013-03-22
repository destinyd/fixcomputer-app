
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        menu_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int which) {
                        System.exit(0);
                    }
                };

                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.alter_activity_point))
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setMessage("确认要退出？")
                        .setNegativeButton(getString(android.R.string.cancel), null)
                        .setPositiveButton(getString(android.R.string.ok), OkClick)
                        .show();


            }

        });
    }
}
