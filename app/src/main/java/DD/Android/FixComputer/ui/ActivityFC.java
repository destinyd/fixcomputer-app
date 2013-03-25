package DD.Android.FixComputer.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import com.actionbarsherlock.view.MenuItem;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

/**
 * Base activity for a Bootstrap activity which does not use fragments.
 */
public abstract class ActivityFC extends RoboSherlockActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  // This is the home button in the top left corner of the screen.
                // Dont call finish! Because activity could have been started by an outside activity and the home button would not operated as expected!
                Intent homeIntent = new Intent(this, ActivityFC.class);
                homeIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(homeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ProgressDialog progressDialog = null;

    protected void progressDialogDismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void progressDialogShow(Activity activity) {
        progressDialogShow(activity, "正在拼命读取中...");
    }

    protected void progressDialogShow(Activity activity, String message) {
        progressDialogShow(activity, message, true);
    }

    protected void progressDialogShow(Activity activity, String message, boolean cancelable) {
        progressDialogDismiss();
        progressDialog = ProgressDialog.show(activity, "", message, true, cancelable);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                progressDialogDismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        progressDialogDismiss();
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
