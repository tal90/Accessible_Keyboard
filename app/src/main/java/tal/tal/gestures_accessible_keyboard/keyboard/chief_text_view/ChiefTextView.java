package tal.tal.gestures_accessible_keyboard.keyboard.chief_text_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import tal.tal.gestures_accessible_keyboard.keyboard.Consts;
import tal.tal.gestures_accessible_keyboard.keyboard.KeysOrganizer;

/**
 * Created by talra on 29-Aug-16.
 */
public class ChiefTextView extends TextView
{
    private static final String TAG = "ChiefTextView";
    private int mFontSize;
    private Context mContext;
    private boolean mIsTypedTextPassword = false;
    private KeysOrganizer mKeysOrganizer = null;

    private long mLastChiefTouchTime = 0;

    //region Constants
    private final int LONG_CLICK_MIN_TIME = 1500;

    private final int LEFT_INVISIBLE_KEY = 1;
    private final int RIGHT_INVISIBLE_KEY = 2;
    private final int TRIGGERING_INVISIBLE_KEY_WINDOW = 2500;
    //endregion Constants

    private int mLastInvisibleClickedKey = 0;
    private long mLastInvisibleClickTime = 0;
    private int mInvisibleKeysCounter = 0;

    public void setUpChief(Context context)
    {
        Log.v(TAG, "setUpChief");
        mContext = context;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mFontSize = sharedPreferences.getInt(Consts.SharedPref_FontSize_TAG, 85);
        setTextSize(mFontSize);
    }

    //region Getters & Setters
    public void setChiefTouchListener(View RootElement)
    {
        Log.v(TAG, "setChiefTouchListener");

        ChiefTouchListener chiefTouchListener = new ChiefTouchListener();

        getRootView().setOnClickListener(chiefTouchListener);
        getRootView().setOnLongClickListener(chiefTouchListener);
        getRootView().setOnTouchListener(chiefTouchListener);
        getRootView().setOnHoverListener(chiefTouchListener);

        RootElement.setOnClickListener(chiefTouchListener);
        RootElement.setOnLongClickListener(chiefTouchListener);
        RootElement.setOnTouchListener(chiefTouchListener);
        RootElement.setOnHoverListener(chiefTouchListener);
    }


    public void setIsTypedTextPassword(boolean mIsTypedTextPassword)
    {
        this.mIsTypedTextPassword = mIsTypedTextPassword;
    }

    public void SetChiefText(String Str)
    {
        Log.v(TAG, "SetChiefText - " + Str);
        if (mIsTypedTextPassword)
        {
            if (Str.length() > 1)
            {
                String start = Str.substring(0, Str.length() - 1);
                String fs = "" + start.charAt(Str.length() - 1);
                setText(GetStarsFromString(start) + fs);
            } else setText(Str);
        } else setText(Str);

    }

    public String GetStarsFromString(String str)       // TODO - recheck if ReImplementation needed..!!
    {
        String StarsString = "";
        for (int i = 0; i < str.length(); i++)
            StarsString += '*';

        return StarsString;
    }

    public void setKeysOrganizer(KeysOrganizer keysOrganizer)
    {
        mKeysOrganizer = keysOrganizer;
    }

    //endregion

    //region Constructors
    public ChiefTextView(Context context)
    {
        super(context);
        setUpChief(context);
    }

    public ChiefTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setUpChief(context);
    }

    public ChiefTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        setUpChief(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChiefTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUpChief(context);
    }

    //endregion

    //region ?????
     /*
        public void onFontSizeChanged(int newFontSize)      // TODO - RECHECK ABOUT THE SHAREDPREFS..
        {
            mFontSize = newFontSize;
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            sharedPreferences.edit()
                    .putInt(Consts.SharedPref_FontSize_TAG, mFontSize)
                    .commit();
        }
*/
    //endregion

    private class ChiefTouchListener implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener, View.OnHoverListener
    {
        @Override
        public void onClick(View view)
        {   // TODO - Ask if there's any difference between regular text behaviour and password's behaviour
            Log.v(TAG, "OnClick");

            if (mKeysOrganizer == null)
                return;

            mKeysOrganizer.ReadDescription(getText().toString());
        }

        @Override
        public boolean onLongClick(View view)
        {
            Log.v(TAG, "onLongClick");
            if (mKeysOrganizer == null)
                return false;

            mKeysOrganizer.Spell(getText().toString());
            return true;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent)
        {
            switch (motionEvent.getActionMasked())
            {
                case MotionEvent.ACTION_HOVER_ENTER:
                case MotionEvent.ACTION_DOWN:
                    Log.v(TAG, "ACTION_DOWN");

                    mLastChiefTouchTime = System.currentTimeMillis();
                    break;

                case MotionEvent.ACTION_HOVER_MOVE:
                case MotionEvent.ACTION_MOVE:
                    Log.v(TAG, "ACTION_MOVE");
                    return true;


                case MotionEvent.ACTION_HOVER_EXIT:
                case MotionEvent.ACTION_UP:
                    Log.v(TAG, "ACTION_UP");

                    // DETECTING LONG CLICK!
                    if (System.currentTimeMillis() - mLastChiefTouchTime >= LONG_CLICK_MIN_TIME)
                    {
                        onLongClick(null);
                        return true;
                    }

                    break;

                default:
                    Log.v(TAG, "DEF - " + motionEvent.getActionMasked());
                    return true;
            }

            DetectInvisibleKey(motionEvent);

            return true;
        }


        @Override
        public boolean onHover(View view, MotionEvent motionEvent)
        {
            return onTouch(view, motionEvent);
        }

        //region INVISIBLE KEYS


        // Sets LastInvisibleClickedKey As The Correct One!
        public void DetectInvisibleKey(MotionEvent motionEvent)
        {
            long CurrTime = System.currentTimeMillis();

            if (mKeysOrganizer == null)
                return;

            if (CurrTime - mLastInvisibleClickTime > TRIGGERING_INVISIBLE_KEY_WINDOW)
            {
                // TIME WINDOW FOR LAST INVISIBLE CLICK IS UP, REINITIALIZING FOR NEW WINDOW
                Log.v(TAG, "TRIGGERING WINDOW TIME IS UP!");
                mInvisibleKeysCounter = 0;
                mLastInvisibleClickedKey = 0;
                mLastInvisibleClickTime = CurrTime;
            }

            switch (motionEvent.getActionMasked())
            {
                case MotionEvent.ACTION_HOVER_ENTER:
                case MotionEvent.ACTION_DOWN:
                    DetermineInvisibleKey((int) motionEvent.getX());
                    return;

                case MotionEvent.ACTION_HOVER_EXIT:
                case MotionEvent.ACTION_UP:
                    int TmpLastInvisibleKeyClicked = mLastInvisibleClickedKey;
                    DetermineInvisibleKey((int) motionEvent.getX());

                    Log.v(TAG, "TMP = " + TmpLastInvisibleKeyClicked + ", Last = " + mLastInvisibleClickedKey);

                    if (TmpLastInvisibleKeyClicked == mLastInvisibleClickedKey)
                    {
                        Log.v(TAG, "INVISIBLE KEY'S COUNTER = " + mInvisibleKeysCounter);
                        mInvisibleKeysCounter++;

                        if (mInvisibleKeysCounter == 3  && mLastInvisibleClickedKey > 0)
                        {
                            mInvisibleKeysCounter = 0;
                            OnInvisibleKeyClick(mLastInvisibleClickedKey);
                        }
                        else if (mLastInvisibleClickedKey == 0 && mInvisibleKeysCounter == 4)
                        {
                            Log.v(TAG, "Opening Settings Activity !");
                            mKeysOrganizer.OpenSettingsActivity();
                        }
                    } else Log.v(TAG, "NO INVISIBLE KEY WAS CLICKED!" + "TMP = " + TmpLastInvisibleKeyClicked + ", Last = " + mLastInvisibleClickedKey);

                    return;
            }
        }

        private int DetermineInvisibleKey(int X)
        {
            mLastInvisibleClickedKey = 0;
            int SingleInvisibleKeySize = getRootView().getWidth() / 3;

            if (X <= SingleInvisibleKeySize)
            {
                Log.v(TAG, "Left Invisible Key Was Pressed");
                mLastInvisibleClickedKey = LEFT_INVISIBLE_KEY;
            } else if (X > SingleInvisibleKeySize * 2 && X <= SingleInvisibleKeySize * 3)
            {
                Log.v(TAG, "Right Invisible Key Was Pressed");
                mLastInvisibleClickedKey = RIGHT_INVISIBLE_KEY;
            }

            return mLastInvisibleClickedKey;
        }

        private void OnInvisibleKeyClick(int Key)
        {
            Log.v(TAG, "OnInvisibleKeyClick");

            if (mKeysOrganizer == null)
                return;

            switch (Key)
            {
                case RIGHT_INVISIBLE_KEY:
                    Log.v(TAG, "RIGHT INVISIBLE KEY WAS TRIGGERED!");
                    mKeysOrganizer.OnRightInvisibleKeyClick();
                    break;

                case LEFT_INVISIBLE_KEY:
                    Log.v(TAG, "LEFT INVISIBLE KEY WAS TRIGGERED!");
                    mKeysOrganizer.OnLeftInvisibleKeyClick();
                    break;
            }
        }

        //endregion
    }
}
