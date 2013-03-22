package DD.Android.FixComputer.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.baidu.mapapi.search.MKSuggestionInfo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dd
 * Date: 13-1-28
 * Time: 下午12:15
 * To change this template use File | Settings | File Templates.
 */
public class AdapterMKSuggestionResults extends ArrayAdapter<MKSuggestionInfo>  {
    private ArrayList<MKSuggestionInfo> mData;

//    public AdaMKSuggestionResults(Context context, int textViewResourceId) {
//        super(context, textViewResourceId);
//        mData = new ArrayList<MKSuggestionInfo>();
//    }

    public AdapterMKSuggestionResults(Context context, int textViewResourceId, ArrayList<MKSuggestionInfo> pdata) {
        super(context, textViewResourceId);
        if(pdata == null)
            throw new NullPointerException();
        mData = pdata;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public MKSuggestionInfo getItem(int index) {
        return mData.get(index);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);    //To change body of overridden methods use File | Settings | File Templates.
        TextView originalView = (TextView) super.getView(position, convertView, parent); // Get the original view

        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final TextView view = (TextView) inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);

        // Start tweaking
//        view.setText(originalView.toString());
        MKSuggestionInfo msi = mData.get(position);
        view.setText(msi.city + msi.key);
        return view;
    }
//
//    @Override
//    public Filter getFilter() {
//        Filter myFilter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults filterResults = new FilterResults();
//                if (constraint != null) {
//                    // A class that queries a web API, parses the data and returns an ArrayList<MKSuggestionResult>
//                    MKSuggestionResultFetcher fetcher = new MKSuggestionResultFetcher();
//                    try {
//                        mData = fetcher.retrieveResults(constraint.toString());
//                    } catch (Exception e) {
//                    }
//                    // Now assign the values and count to the FilterResults object
//                    filterResults.values = mData;
//                    filterResults.count = mData.size();
//                }
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence contraint, FilterResults results) {
//                if (results != null && results.count > 0) {
//                    notifyDataSetChanged();
//                } else {
//                    notifyDataSetInvalidated();
//                }
//            }
//        };
//        return myFilter;
//    }
}