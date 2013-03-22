

package DD.Android.FixComputer.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static DD.Android.FixComputer.core.Constants.ToolBar.*;

/**
 * Pager adapter
 */
public class AdapterFC extends FragmentPagerAdapter {

    public AdapterFC(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a DummySectionFragment (defined as a static inner class
        // below) with the page number as its lone argument.

        switch (position){
            case PRICING:
                return new FragmentPricing();
            case PROBLEM_FORM:
                return new FragmentProblemForm();
            case PROBLEMS:
//                Bundle args = new Bundle();
//                args.putSerializable(PROBLEM,new Problem());
                FragmentProblems fl = new FragmentProblems();
//                fl.setArguments(args);
                return fl;
            case MENU:
                return new FragmentMenu();
            default:
                return null;
        }
//        Bundle args = new Bundle();
//        args.putInt("no", position + 1);
//        fragment.setArguments(args);

//        return fragment;

    }



    @Override
    public int getCount() {
        return COUNT;
    }


}
