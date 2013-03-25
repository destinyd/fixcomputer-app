
package DD.Android.FixComputer.ui;

import DD.Android.FixComputer.R;
import DD.Android.FixComputer.R.id;
import DD.Android.FixComputer.core.DeviceUuidFactory;
import DD.Android.FixComputer.core.ServiceFC;
import DD.Android.FixComputer.core.Problem;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.costum.android.widget.LoadMoreListView;
import com.github.kevinsawicki.wishlist.Toaster;
import roboguice.inject.InjectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static DD.Android.FixComputer.core.Constants.Extra.PROBLEM;

/**
 * Activity to authenticate the ABUser against an API (example API on Parse.com)
 */
public class FragmentProblems extends
        FragmentFC {
    @InjectView(id.lv_list)
    private LoadMoreListView lv_list;
    //    @InjectExtra(PROBLEM)
    protected Problem problem;

    List<Problem> problems = null;

    static FragmentProblems factory = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        factory = this;
        initProblems();
        return inflater.inflate(R.layout.fragment_problems, null);
    }

    @Override
    public void onDestroy() {
        factory = null;
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    static public FragmentProblems getInstance(){
        return factory;
    }

    private void initProblems() {
        if(problems == null)
            problems = new ArrayList<Problem>();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                onListItemClick((LoadMoreListView) parent, view, position, id);
            }
        });
        new GetProblems().execute();
    }

    public void onListItemClick(LoadMoreListView l, View v, int position, long id) {
        Problem problem = ((Problem) l.getItemAtPosition(position));
        startActivity(new Intent(getActivity(), ActivityProblem.class).putExtra(PROBLEM, problem));
    }

    @Override
    public void setArguments(Bundle args) {
        initProblems();
        problem = (Problem) args.getSerializable(PROBLEM);
        if (problem.get_id() != null) {
            problems.add(0,problem);
            initListData(problems);
        }
    }

    private void initListData(List<Problem> requests) {
        AdapterProblems adapter = new AdapterProblems(
                getActivity().getLayoutInflater(), requests,
                false);
        lv_list.setAdapter(adapter);

    }

    private class GetProblems extends AsyncTask<Void, String, Void> {

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void... params/*参数1*/) {
            try {
                problems = ServiceFC.getProblems(new DeviceUuidFactory(getActivity()).getDeviceUuid().toString());
            } catch (IOException e) {
                Toaster.showLong(getActivity(),"读取失败");
            }
            return null;
        }



        //步骤4：定义后台进程执行完后的处理，本例，采用Toast

        protected void onPostExecute(Void result/*参数3*/) {
            if (problems != null)
                initListData(problems);
            progressDialogDismiss();
        }
    }
}
