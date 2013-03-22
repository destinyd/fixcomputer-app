
package DD.Android.FixComputer.ui;

import DD.Android.FixComputer.R;
import DD.Android.FixComputer.R.id;
import DD.Android.FixComputer.core.Problem;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import static DD.Android.FixComputer.core.Constants.Extra.PROBLEM;

/**
 * Activity to authenticate the ABUser against an API (example API on Parse.com)
 */
public class FragmentMenu extends
        FragmentFC {
    @InjectView(id.lv_menu)
    private ListView lv_menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
