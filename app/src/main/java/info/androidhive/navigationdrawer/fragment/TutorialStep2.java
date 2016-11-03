package info.androidhive.navigationdrawer.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.animation.ValueAnimator;

import org.codepond.wizardroid.WizardStep;
import org.greenrobot.eventbus.EventBus;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.other.UpdateStepperEvent;

/**
 * Created by raul on 30/10/2016.
 */

public class TutorialStep2 extends WizardStep {

    private SeekBar volumeControl;
    private TextView totalToPay;
    private RelativeLayout relativeLayout;

    private static final int SNAP_MIN = 0;
    private static final int SNAP_MIDDLE = 50;
    private static final int SNAP_MAX = 100;

    private static final int LOWER_HALF = (SNAP_MIN + SNAP_MIDDLE) / 2;
    private static final int UPPER_HALF = (SNAP_MIDDLE + SNAP_MAX) / 2;

    private View.OnClickListener yesOnClickListener;
    private View.OnClickListener noOnClickListener;

    //Wire the layout to the step
    public TutorialStep2() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_choose_time, container, false);

        volumeControl  = (SeekBar) v.findViewById(R.id.volume_bar);
        totalToPay     = (TextView) v.findViewById(R.id.total_to_pay);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.parent_step);

        setVolumeControlListener();

        return v;
    }

    private void setVolumeControlListener() {
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;
            double time         = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double costPerMinutes = 1.5;
                double total          = progressChanged * costPerMinutes;

                if (progressChanged > 60) {
                    time = (double) progressChanged / 60;
                    if (time == 1) {
                        totalToPay.setText("You have choosen: " + progressChanged + " - " + time + " hour, you have to pay: $" + total);
                    } else {
                        totalToPay.setText("You have choosen: " + progressChanged + " - " + time + " hours, you have to pay: $" + total);
                    }
                } else {
                    time = progressChanged;
                    if (time == 1) {
                        totalToPay.setText("You have choosen: " + progressChanged + " - " + time + " minute, you have to pay: $" + total);
                    } else {
                        totalToPay.setText("You have choosen: " + progressChanged + " - " + time + " minutes, you have to pay: $" + total);
                    }
                }

                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Do you accept?", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (progressChanged > 60) {
                                    if (time == 1) {
                                        Toast.makeText(getActivity(), "Click finish to accept.", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Click finish to accept.", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    if (time == 1) {
                                        Toast.makeText(getActivity(), "Click finish to accept.", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Click finish to accept.", Toast.LENGTH_LONG).show();
                                    }
                                }
                                EventBus.getDefault().post(new UpdateStepperEvent("finish"));
                            }
                        });
                snackbar.show();
            }
        });
    }

    private static void setProgressAnimatedJdk(final SeekBar seekBar, int from, int to) {
        ValueAnimator anim = ValueAnimator.ofInt(from, to);
        anim.setDuration(100);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animProgress = (Integer) animation.getAnimatedValue();
                seekBar.setProgress(animProgress);
            }
        });
        anim.start();
    }

}