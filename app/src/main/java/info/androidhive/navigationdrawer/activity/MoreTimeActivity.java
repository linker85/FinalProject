package info.androidhive.navigationdrawer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.fragment.TutorialStep2;

public class MoreTimeActivity extends AppCompatActivity {

    private Handler mHandler;

    private boolean canExtendTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_time);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Extend time ");

        mHandler = new Handler();

        canExtendTime = false; // from database

        loadStep2Fragment();
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu.
     * Loads the fragment returned from getHomeFragment() function into FrameLayout.
     * It also takes care of other things like changing the toolbar title, hiding / showing fab,
     * invalidating the options menu so that new menu can be loaded for different fragment.
     */
    private void loadStep2Fragment() {
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {

                Bundle bundle = new Bundle();
                bundle.putBoolean("canExtendTime", canExtendTime);

                // update the main content by replacing fragments
                Fragment fragment = new TutorialStep2();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_more_time, fragment, "id_more_time");
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
