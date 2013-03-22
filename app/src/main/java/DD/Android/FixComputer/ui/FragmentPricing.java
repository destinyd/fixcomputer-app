
package DD.Android.FixComputer.ui;

import DD.Android.FixComputer.R;
import DD.Android.FixComputer.R.id;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import roboguice.inject.InjectView;

import static com.github.kevinsawicki.http.HttpRequest.post;

/**
 * Activity to authenticate the ABUser against an API (example API on Parse.com)
 */
public class FragmentPricing extends
        FragmentFC {
    @InjectView(id.iv_pricing)
    private ImageView iv_pricing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pricing, null);
    }

}
