package DD.Android.FixComputer.ui;

import DD.Android.FixComputer.core.PrettyDateFormat;
import DD.Android.FixComputer.core.Problem;
import android.view.LayoutInflater;
import DD.Android.FixComputer.R;

import java.util.List;

public class AdapterProblems extends AdapterAlternatingColorList<Problem> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public AdapterProblems(LayoutInflater inflater, List<Problem> items,
                           boolean selectable) {
        super(R.layout.item_problems, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public AdapterProblems(LayoutInflater inflater, List<Problem> items) {
        super(R.layout.item_problems, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_created_at, R.id.tv_status};
    }

    @Override
    protected void update(int position, Problem item) {
        super.update(position, item);

        setText(0, PrettyDateFormat.defaultFormat(item.getCreated_at()));
        setText(1, item.getStatusStr());
    }
}