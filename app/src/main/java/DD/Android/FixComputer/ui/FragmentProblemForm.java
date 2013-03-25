
package DD.Android.FixComputer.ui;

import DD.Android.FixComputer.FCApplication;
import DD.Android.FixComputer.R;
import DD.Android.FixComputer.R.id;
import DD.Android.FixComputer.R.string;
import DD.Android.FixComputer.core.DeviceUuidFactory;
import DD.Android.FixComputer.core.FCService;
import DD.Android.FixComputer.core.Problem;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.baidu.location.*;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.search.*;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.github.kevinsawicki.wishlist.Toaster;
import com.umeng.analytics.MobclickAgent;
import roboguice.inject.InjectView;
import roboguice.util.Ln;
import roboguice.util.RoboAsyncTask;

import java.util.TimerTask;

import static com.github.kevinsawicki.http.HttpRequest.post;

import static DD.Android.FixComputer.core.Constants.Extra.PROBLEM;

import static DD.Android.FixComputer.core.Constants.ToolBar.*;

/**
 * Activity to authenticate the ABUser against an API (example API on Parse.com)
 */
public class FragmentProblemForm extends
        FragmentFC {

    /**
     * PARAM_CONFIRMCREDENTIALS
     */
    public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";

    /**
     * PARAM_PASSWORD
     */
    public static final String PARAM_PASSWORD = "password";

    /**
     * PARAM_USERNAME
     */
    public static final String PARAM_USERNAME = "username";

    /**
     * PARAM_AUTHTOKEN_TYPE
     */
    public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";


    private AccountManager accountManager;

    @InjectView(id.et_phone)
    private EditText et_phone;

    @InjectView(id.et_name)
    private EditText et_name;

    @InjectView(id.actv_address)
    private AutoCompleteTextView actv_address;

    @InjectView(id.et_address_plus)
    private EditText et_address_plus;

    @InjectView(id.et_desc)
    private EditText et_desc;

    @InjectView(id.btn_submit)
    private Button btn_submit;

    private TextWatcher watcher = validationTextWatcher();

    private RoboAsyncTask<Boolean> postTask;

    Problem problem;

    //搜索
    MKSearch mSearch = null;
    TimerTask task = null;
    java.util.Timer timer = new java.util.Timer(true);

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    //    public NotifyLister mNotifyer=null;
    LocationData locData = null;


    private TextWatcher validationTextWatcher() {
        return new AdapterTextWatcher() {
            public void afterTextChanged(Editable gitDirEditText) {
                updateUIWithValidation();
            }

        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_problem_form, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        et_name.addTextChangedListener(watcher);
        et_phone.addTextChangedListener(watcher);
        actv_address.addTextChangedListener(watcher);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePostProblem(v);
            }
        });

        AdapterView.OnItemClickListener click_listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MKSuggestionInfo mk_si = (MKSuggestionInfo) adapterView.getItemAtPosition(i);
                actv_address.setText(
//                        mk_si.city +
                        mk_si.key);
                actv_address.requestFocus();
                actv_address.setSelection(actv_address.getText().length());
                mSearch.poiSearchInCity(mk_si.city, mk_si.key);
            }
        };
//        actv_q.setOnItemSelectedListener(selected_listener);

        actv_address.setOnItemClickListener(click_listener);

        mSearch = new MKSearch();
        mSearch.init(FCApplication.getInstance().mBMapManager, new MKSearchListener() {

            @Override
            public void onGetPoiResult(MKPoiResult mkPoiResult, int type, int error) {
            /*
             * 返回poi搜索结果。 参数： arg0 - 搜索结果 arg1 - 返回结果类型: MKSearch.TYPE_POI_LIST MKSearch.TYPE_AREA_POI_LIST MKSearch.TYPE_CITY_LIST arg2 - 错误号，0表示正确返回
             */
//                Toaster.showLong(getActivity(),"onGetPoiResult" + String.valueOf(mkPoiResult.getNumPois()));
            }

            @Override
            public void onGetTransitRouteResult(MKTransitRouteResult mkTransitRouteResult, int i) {
//                Toaster.showLong(getActivity(), "onGetTransitRouteResult");
            }

            @Override
            public void onGetDrivingRouteResult(MKDrivingRouteResult mkDrivingRouteResult, int i) {
//                Toaster.showLong(getActivity(), "onGetDrivingRouteResult");
            }

            @Override
            public void onGetWalkingRouteResult(MKWalkingRouteResult mkWalkingRouteResult, int i) {
//                Toaster.showLong(getActivity(), "onGetWalkingRouteResult");
            }

            @Override
            public void onGetAddrResult(MKAddrInfo mkAddrInfo, int error) {
                if (error != 0) {
                    String str = String.format("错误号：%d", error);
                    Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
                    return;
                }

                if ("".equals(mkAddrInfo.strAddr)) {
                    Toaster.showLong(getActivity(), "您的地址未能定位成功，请自行填写。");
                    actv_address.setText(mkAddrInfo.strAddr);//.strAddr);
                } else {
                    Toaster.showLong(getActivity(), "您所在地址 " + mkAddrInfo.strAddr + "，如果有偏差请自行修改。");
                    actv_address.setText(mkAddrInfo.strAddr);//.strAddr);
                }
                //关闭gps定位
                mLocClient.stop();
            }

            @Override
            public void onGetBusDetailResult(MKBusLineResult mkBusLineResult, int i) {
//                Toaster.showLong(getActivity(), "onGetBusDetailResult");
            }

            @Override
            public void onGetSuggestionResult(MKSuggestionResult mkSuggestionResult, int i) {
                if (i != 0 || mkSuggestionResult == null || mkSuggestionResult.getSuggestionNum() == 0) {
                    Toaster.showLong(getActivity(), "抱歉，未找到结果");
                    return;
                }
                Log.e("onGetSuggestionResult", String.valueOf(mkSuggestionResult.getSuggestionNum()));
                AdapterMKSuggestionResults suggestion_adapter =
                        new AdapterMKSuggestionResults(
                                getActivity(),
                                android.R.layout.simple_list_item_1,
                                mkSuggestionResult.getAllSuggestions()
                        );
//                actv_q.setAdapter(suggestionString);
                actv_address.setAdapter(suggestion_adapter);
                actv_address.showDropDown();
            }

            @Override
            public void onGetPoiDetailSearchResult(int i, int error) {
//                Toaster.showLong(getActivity(), "onGetPoiDetailSearchResult");
            }
        });

        actv_address.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent != null) {
                    start_search();
                }
//                task = new TimerTask() {
//                    public void run() {
//                        suggestionSearchButtonProcess(actv_q);//每次需要执行的代码放到这里面。
//                    }
//                };
//                suggestionSearchButtonProcess(view);
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        //定位开始
        locData = new LocationData();
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    private void start_search() {
        if (task != null) {
            task.cancel();
        }
        task = new TimerTask() {
            public void run() {
                suggestionSearchButtonProcess(actv_address);//每次需要执行的代码放到这里面。
            }
        };
        timer.schedule(task, 1500);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUIWithValidation();
    }

    private void updateUIWithValidation() {
        boolean populated = populated(et_phone) && populated(et_name) && populated(actv_address);
        btn_submit.setEnabled(populated);
    }

    private boolean populated(EditText editText) {
        return editText.length() > 0;
    }

    public void handlePostProblem(View view) {
        if (postTask != null)
            return;

        progressDialogShow(getActivity());

        problem = new Problem();
        problem.setPhone(et_phone.getText().toString());
        problem.setName(et_name.getText().toString());
        problem.setAddress(actv_address.getText().toString());
        problem.setAddress_plus(et_address_plus.getText().toString());
        problem.setDesc(et_desc.getText().toString());
        if(locData != null && !(locData.latitude == 4.9E-324 && locData.longitude == 4.9E-324)){
            problem.setLat(locData.latitude);
            problem.setLng(locData.longitude);
        }
        else{
            problem.setLat(0.0);
            problem.setLng(0.0);
        }

        postTask = new RoboAsyncTask<Boolean>(getActivity()) {
            public Boolean call() throws Exception {

                problem = FCService.sendProblem(problem, new DeviceUuidFactory(getActivity()).getDeviceUuid().toString());

                if (problem != null)
                    return true;
                else
                    return false;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                Throwable cause = e.getCause() != null ? e.getCause() : e;

                String message;
                message = getResources().getString(
                        string.message_post_problem_failure);

                Toaster.showLong(getActivity(), message);
            }

            @Override
            public void onSuccess(Boolean authSuccess) {
                onPostSuccess(authSuccess);
            }

            @Override
            protected void onFinally() throws RuntimeException {
//                hideProgress();
                progressDialogDismiss();
//                postTask.update_status(true);
                postTask = null;
            }
        };
        postTask.execute();
    }


    /**
     * Hide progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void hideProgress() {
        getActivity().dismissDialog(0);
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void showProgress() {
        getActivity().showDialog(0);
    }

    public void onPostSuccess(boolean result) {
        if (result) {
            MobclickAgent.onEvent(getActivity(), "post Problem");
            Bundle args = new Bundle();
            args.putSerializable(PROBLEM, problem);
            FragmentProblems fp = FragmentProblems.getInstance();
            fp.setArguments(args);
            clean_edittext();
            ((ActivityMain) getActivity()).mViewPager.setCurrentItem(2);
        } else {
            Ln.d("onPostSuccess: failed to post");
            Toaster.showLong(getActivity(),
                    string.message_post_problem_failure);
        }
    }

    private void clean_edittext() {
        et_phone.setText("");
        et_name.setText("");
        actv_address.setText("");
        et_address_plus.setText("");
        et_desc.setText("");
        btn_submit.setEnabled(false);
    }

    void suggestionSearchButtonProcess(View v) {
//        mSearch.poiSearchInCity("柳州",actv_address.getText().toString());
        mSearch.suggestionSearch(actv_address.getText().toString());
    }


    //定位

    /**
     * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;

//            if(locData == null || locData.latitude != location.getLatitude() || locData.longitude != location.getLongitude())
//            {
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            GeoPoint poi = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
            mSearch.reverseGeocode(poi);
//                mSearch.geocode(editGeoCodeKey.getText().toString(), editCity.getText().toString());
//            }
//            locData.direction = 2.0f;
//            locData.accuracy = location.getRadius();
//            locData.direction = location.getDerect();
//            Log.d("loctest",String.format("before: lat: %f lon: %f", location.getLatitude(),location.getLongitude()));
            // GeoPoint p = CoordinateConver.fromGcjToBaidu(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
            //  Log.d("loctest",String.format("before: lat: %d lon: %d", p.getLatitudeE6(),p.getLongitudeE6()));
//            myLocationOverlay.setData(locData);
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                return;
            }
            actv_address.setText(poiLocation.getAddrStr());
        }
    }
//
//    public class NotifyLister extends BDNotifyListener {
//        public void onNotify(BDLocation mlocation, float distance) {
//        }
//    }
}
