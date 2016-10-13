package tal.tal.gestures_accessible_keyboard.activities.settings;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import tal.tal.gestures_accessible_keyboard.R;
import tal.tal.gestures_accessible_keyboard.keyboard.Consts;

public class SettingsActivity extends AppCompatActivity
{
    private int mFontSize;
    private TextView mFont_TextView;
    private ImageButton mbtnUp;
    private ImageButton mbtnDown;
    private CheckBox mVibrate;
    private boolean mVibrateIsON;
    private int mUsedMethodNumber;
    private ImageButton mbtnMethodUp;
    private ImageButton mbtnMethodDown;
    private TextView mMethod_TextView;
    private ImageButton mbtnQMark;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mFontSize = sharedPreferences.getInt(Consts.SharedPref_FontSize_TAG, 85);
        mUsedMethodNumber = sharedPreferences.getInt(Consts.SharedPref_Method_TAG, 1);
        mVibrateIsON = sharedPreferences.getBoolean(Consts.SharedPref_Vibrate_TAG, true);

        setUpAllButtonsAndViewFunctionalities();
    }

    public void setUpAllButtonsAndViewFunctionalities()
    {
        mFont_TextView = (TextView) findViewById(R.id.Font_TextView);
        mFont_TextView.setTextSize(mFontSize);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean ReturnedValue;

        mVibrate = (CheckBox) findViewById(R.id.Vibrate_CheckBox);
        mVibrate.setChecked(mVibrateIsON);

        ReturnedValue = sharedPreferences.getBoolean(Consts.SharedPref_Vibrate_TAG, true);
        mVibrate.setChecked(ReturnedValue);

        mVibrate.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sharedPreferences.edit()
                                .putBoolean(Consts.SharedPref_Vibrate_TAG, isChecked)
                                .commit();
                    }
                });

        mbtnUp = (ImageButton) findViewById(R.id.btnUp);
        mbtnDown = (ImageButton) findViewById(R.id.btnDown);

        mbtnUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mFontSize += 5;
                mFont_TextView.setTextSize(mFontSize);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit()
                        .putInt(Consts.SharedPref_FontSize_TAG, mFontSize)
                        .commit();
            }
        });

        mbtnDown.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mFontSize -= 5;
                        mFont_TextView.setTextSize(mFontSize);

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sharedPreferences.edit()
                                .putInt(Consts.SharedPref_FontSize_TAG, mFontSize)
                                .commit();
                    }
                });

        mMethod_TextView = (TextView) findViewById(R.id.Method_TextView);
        mMethod_TextView.setText(String.valueOf(mUsedMethodNumber));


        mbtnMethodUp = (ImageButton) findViewById(R.id.MethodBtnUp);
        mbtnMethodDown = (ImageButton) findViewById(R.id.MethodBtnDown);

        mbtnQMark = (ImageButton) findViewById(R.id.MethodQMark);
        SetQMarkContentDescription(mUsedMethodNumber);

        mbtnMethodUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mUsedMethodNumber++;
                if (mUsedMethodNumber > Consts.MaxAvailableMethods)
                    mUsedMethodNumber = 1;
                mMethod_TextView.setText(String.valueOf(mUsedMethodNumber));
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit()
                        .putInt(Consts.SharedPref_Method_TAG, mUsedMethodNumber)
                        .commit();
                SetQMarkContentDescription(mUsedMethodNumber);

            }
        });

        mbtnMethodDown.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mUsedMethodNumber--;
                if (mUsedMethodNumber < 1)
                    mUsedMethodNumber = Consts.MaxAvailableMethods;
                mMethod_TextView.setText(String.valueOf(mUsedMethodNumber));
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit()
                        .putInt(Consts.SharedPref_Method_TAG, mUsedMethodNumber)
                        .commit();
                SetQMarkContentDescription(mUsedMethodNumber);
            }
        });

    }


    private void SetQMarkContentDescription(int usedMethodNum)
    {
        if (mbtnQMark == null)
            mbtnQMark = (ImageButton) findViewById(R.id.MethodQMark);

        switch (usedMethodNum)
        {
            default:
            case 1:
                mbtnQMark.setContentDescription("Method 1 - Using swipes to switch between characters.");
                break;
            case 2:
                mbtnQMark.setContentDescription("Method 2 - Pressing with the wanted amount of fingers to determine the wanted character.");
                break;
            case 3:
                mbtnQMark.setContentDescription("Method 3 - Pressing more than once on the same key to change character");
                break;
            case 4:
                mbtnQMark.setContentDescription("Method 4 - LALA LI LA!");      // TODO - COMPLEETE!
                break;

        }
    }

}
