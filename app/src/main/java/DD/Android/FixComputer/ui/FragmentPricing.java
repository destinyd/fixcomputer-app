
package DD.Android.FixComputer.ui;

import DD.Android.FixComputer.R;
import DD.Android.FixComputer.R.id;
import DD.Android.FixComputer.core.DeviceUuidFactory;
import DD.Android.FixComputer.core.ServiceFC;
import DD.Android.FixComputer.core.Price;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.github.kevinsawicki.wishlist.Toaster;
import roboguice.inject.InjectView;

import java.io.IOException;
import java.util.List;

/**
 * Activity to authenticate the ABUser against an API (example API on Parse.com)
 */
public class FragmentPricing extends
        FragmentFC {
    //    @InjectView(id.iv_pricing)
//    private ImageView iv_pricing;
    @InjectView(id.lv_pricing)
    private ListView lv_pricing;

    List<Price> prices = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        new GetPrices().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pricing, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        prices_to_view();
    }


    private void initListData(List<Price> requests) {
        AdapterPricing adapter = new AdapterPricing(
                getActivity().getLayoutInflater(), requests,
                false);
        lv_pricing.setAdapter(adapter);

    }


    private class GetPrices extends AsyncTask<Void, String, Void> {

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void... params/*参数1*/) {
            try {
                prices = ServiceFC.getPrices(new DeviceUuidFactory(getActivity()).getDeviceUuid().toString());
            } catch (IOException e) {
                Toaster.showLong(getActivity(), "读取失败");
            }
            return null;
        }



        //步骤4：定义后台进程执行完后的处理，本例，采用Toast

        protected void onPostExecute(Void result/*参数3*/) {
            prices_to_view();
            progressDialogDismiss();
        }
    }

    private void prices_to_view() {
        if (prices != null)
            initListData(prices);
    }

}
