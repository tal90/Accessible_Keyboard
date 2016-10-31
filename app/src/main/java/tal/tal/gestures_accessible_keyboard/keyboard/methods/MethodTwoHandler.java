package tal.tal.gestures_accessible_keyboard.keyboard.methods;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import tal.tal.gestures_accessible_keyboard.keyboard.KeysOrganizer;
import tal.tal.gestures_accessible_keyboard.keyboard.Speech.SpeechHelper;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.Key;

/**
 * Created by talra on 24-Aug-16.
 */
public class MethodTwoHandler implements IMethodHandlers, View.OnTouchListener, View.OnHoverListener
{
    private static final String TAG = "MethodTwoHandler";
    private KeysOrganizer mKeysOrganizer = null;
    private String mTypedText = "";
    //region LocateTouchedKey Global Vars
    private int mLastTouchXCoords = 0;
    private int mLastTouchYCoords = 0;
    private Key mLastTouchedKey = null;
    //endregion
    private int mFingerCounter = 0;
    private long mLastTouchDownTime = 0;
    private final int KEY_SELECTION_TIME_WINDOW = 300;
    private String mLastKeyMeaning = "";
    private long mDelayTimerTimeStamp = 0;
    private boolean mDeleteLastChar = true;

    public MethodTwoHandler(KeysOrganizer mKeysOrganizer)
    {
        Log.v(TAG, "MethodTwoHandler - Constructor");
        this.mKeysOrganizer = mKeysOrganizer;
    }


    public Key LocateTouchedKey(View v, MotionEvent motionEvent)
    {
        float X_Axis = motionEvent.getX();
        float Y_Axis = motionEvent.getY();
        int xLeft = (int) X_Axis;
        int yUp = (int) Y_Axis;

        if (xLeft == mLastTouchXCoords && yUp == mLastTouchYCoords && mLastTouchedKey != null)        // this exact touch has been occurred a moment ago..
        {
            return mLastTouchedKey;
        }

        mLastTouchXCoords = xLeft;
        mLastTouchYCoords = yUp;


        for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++)
        {
            View tmpRow = ((ViewGroup) v).getChildAt(i);
            Object tmpRowTag = tmpRow.getTag();

            if (tmpRowTag != null && tmpRowTag.equals("row"))
            {
                boolean isInRow = (tmpRow.getLeft() <= X_Axis && X_Axis <= tmpRow.getRight() &&
                        tmpRow.getTop() <= Y_Axis && Y_Axis <= tmpRow.getBottom());

                if (!isInRow)
                    continue;

                float X_Coordinate = X_Axis - tmpRow.getLeft();
                float Y_Coordinate = Y_Axis - tmpRow.getTop();

                for (int j = 0; j < ((ViewGroup) tmpRow).getChildCount(); j++)
                {
                    View nextChild = ((ViewGroup) tmpRow).getChildAt(j);

                    if (nextChild.getLeft() <= X_Coordinate && X_Coordinate <= nextChild.getRight() &&
                            nextChild.getTop() <= Y_Coordinate && Y_Coordinate <= nextChild.getBottom())
                    {
                        mLastTouchedKey = (Key) nextChild;
                        return mLastTouchedKey;
                    }
                }
            }
        }
        //  Log.v(TAG, "Key IS NULLLLLLLLLLLLL!!!!!");
        if (mLastTouchedKey != null)
            return mLastTouchedKey;
        return mKeysOrganizer.getKeys()[0];
    }


    //region Override Methods - IMethodHandlers
    @Override
    public boolean DeleteLastTypedTextCharacter()
    {
        if (mTypedText.length() == 0)
            return false;

        mTypedText = mTypedText.substring(0, mTypedText.length() - 1);
        return true;
    }

    @Override
    public String getTypedText()
    {
        return mTypedText;
    }

    @Override
    public String onKeyClick(Key key)
    {
        if (key == null)
            return "";

        String ReturnString = key.getKeyMeaning();
        Log.v(TAG, ReturnString + " Key Clicked!");

        if (mKeysOrganizer.IsRegularKey(key))
        {
            ConcatenateToTypedTextAndChief(key.getKeyMeaning());
        } else
        {
            mKeysOrganizer.CommittingAnIrregularKey(key.getKeyMeaning());
        }
        mKeysOrganizer.VibrateAfterKeyPressed();

        key.setState(1);

        return ReturnString;
    }

    @Override
    public void ConcatenateToTypedTextAndChief(String string)
    {
        mTypedText += string;
        mKeysOrganizer.SetTextInsideTheChief(mTypedText);
    }

    @Override
    public void SetTypedTextAnEmptyString()
    {
        mTypedText = "";
    }

    @Override
    public void setTypedText(String str)
    {
        mTypedText = str;
    }

    @Override
    public void BlankLastTouchedKey()
    {
        mLastTouchedKey = null;
    }

    //endregion

    //region Override Methods - OnTouch, OnHover
    @Override
    public boolean onHover(View view, MotionEvent motionEvent)
    {
        return onTouch(view, motionEvent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        int tmpNumOfFingers = motionEvent.getPointerCount();

        if (tmpNumOfFingers > 1)
        {
            if (tmpNumOfFingers > 4)
                return false;

            if (mLastTouchedKey == null)
                return false;

            if (mLastTouchedKey.getState() == tmpNumOfFingers)
                return false;

            if (mLastTouchedKey.getMaxStates() < tmpNumOfFingers)
                return false;

            if (!isFingerUpDelayTimerIsUp())
            {
                Log.v(TAG, "TIMERRRRR...");
                return false;
            }

            if (mFingerCounter == tmpNumOfFingers + 1)
            {
                Log.v(TAG, "Avoiding System false events");
                ResetFingerUpDelayTimer();
                mFingerCounter = tmpNumOfFingers;
                return false;
            }

            mFingerCounter = tmpNumOfFingers;

            mLastTouchedKey.setState(tmpNumOfFingers);
            mKeysOrganizer.ReadDescription(mLastTouchedKey.getContentDescription().toString());      //.getKeyMeaning());
            Log.v(TAG, "CLICK OPTION 1");
            ReplaceLastCharacter(mLastTouchedKey);
            mLastTouchedKey.setState(tmpNumOfFingers);
            return true;
        }

        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_HOVER_ENTER:
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG, "ACTION_DOWN!");
                mDeleteLastChar = true;
                if (isStatePickerTimeIsUp())
                {
                    LocateTouchedKey(view, motionEvent);
                    if (mKeysOrganizer.IsRegularKey(mLastTouchedKey))
                    {
                        Log.v(TAG, "CLICK OPTION 2");
                        mLastKeyMeaning = onKeyClick(mLastTouchedKey);
                    } else mDeleteLastChar = false;
                }
                ResetStatePickingTimer();
                return true;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.v(TAG, "ACTION_MOVE!");

                ResetStatePickingTimer();
                int LastKeySerNum = mLastTouchedKey.getSerialNum();
                LocateTouchedKey(view, motionEvent);
                if (mLastTouchedKey.getSerialNum() == LastKeySerNum)        // Same Key Pressed
                {
                    mLastTouchedKey.setState(1);
                    if (!mLastKeyMeaning.equals(mLastTouchedKey.getKeyMeaning()))
                    {
                        mKeysOrganizer.ReadDescription(mLastTouchedKey.getContentDescription().toString());         //  getKeyMeaning());
                        Log.v(TAG, "CLICK OPTION 4");
                        ReplaceKeyIfRegular(mLastTouchedKey);
                    }
                    return true;
                } else                                                    // Another Key Was Pressed
                {
                    Log.v(TAG, "CLICK OPTION 3");
                    ReplaceKeyIfRegular(LocateTouchedKey(view, motionEvent));
                }

                return true;


            case MotionEvent.ACTION_HOVER_EXIT:
            case MotionEvent.ACTION_UP:
                Log.v(TAG, "ACTION_UP!");
                SetAllKeysState(1);
                if (mLastTouchedKey != null)
                    if (!mKeysOrganizer.IsRegularKey(mLastTouchedKey))
                        mLastKeyMeaning = onKeyClick(mLastTouchedKey);
                return true;
        }
        return false;
    }

    //endregion


    public void ResetStatePickingTimer()
    {
        mLastTouchDownTime = System.currentTimeMillis();
    }

    public boolean isStatePickerTimeIsUp()
    {
        return System.currentTimeMillis() - mLastTouchDownTime > KEY_SELECTION_TIME_WINDOW;
    }

    public void SetAllKeysState(int newState)
    {
        for (Key key : mKeysOrganizer.getKeys())
        {
            key.setState(newState);
        }
    }

    public void ResetFingerUpDelayTimer()
    {
        mDelayTimerTimeStamp = System.currentTimeMillis();
    }

    public boolean isFingerUpDelayTimerIsUp()
    {
        return System.currentTimeMillis() > mDelayTimerTimeStamp + 10;
    }

    public boolean ReplaceKeyIfRegular(Key key)
    {
        if (mKeysOrganizer.IsRegularKey(key))
        {
            ReplaceLastCharacter(key);
            return true;
        }
        return false;
    }

    public void ReplaceLastCharacter(Key key)
    {
        if (mDeleteLastChar)
        {
            mKeysOrganizer.BackSpaceKeyClick(this);
            Log.v(TAG, "BackSpace click!");
        } else
        {
            Log.v(TAG, "AVOIDED BACKSPACE!!!");
            mDeleteLastChar = true;
        }
        mLastKeyMeaning = onKeyClick(key);
    }
}

// BACKUP!
/*
 @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_HOVER_ENTER:
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG, "ACTION_DOWN!");
                mFingerCounter = 0;
                if (System.currentTimeMillis() - mLastTouchDownTime < 2000)
                {
                    Log.v(TAG, "ACTION TERMINATED!");
                    return true;
                }

                onKeyClick(LocateTouchedKey(view, motionEvent));
                mLastTouchDownTime = System.currentTimeMillis();
                return true;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.v(TAG, "ACTION_MOVE!");
                int tmpFingercount = motionEvent.getPointerCount();
                if (tmpFingercount > 4)
                    return false;

                int LastClickedKeySerial;
                if (mLastTouchedKey == null)
                    LastClickedKeySerial = -1;
                else LastClickedKeySerial = mLastTouchedKey.getSerialNum();
                mLastTouchedKey = LocateTouchedKey(view, motionEvent);

                // if same key pressed
                if (LastClickedKeySerial == mLastTouchedKey.getSerialNum())
                {
                    // if same key but with different number of clicked fingers
                    if (tmpFingercount != mFingerCounter)
                    {
                        mFingerCounter = tmpFingercount;
                        mLastTouchedKey.setState(mFingerCounter);
                        mSpeechHelper.ReadDescription(mLastTouchedKey.getContentDescription().toString());
                        mKeysOrganizer.BackSpaceKeyClick(this);
                        onKeyClick(mLastTouchedKey);
                        return true;
                    } else return true;               // same key same fingers pressing
                }
                //        if (isSelectingTimeWindowIsUp())
                //- OKKKK.. WORKING ON IT..!
                Log.v(TAG, "LastClickedKeySerial = " + LastClickedKeySerial + ", mLastTouchedKey.getSerialNum() = " + mLastTouchedKey.getSerialNum());
                mKeysOrganizer.BackSpaceKeyClick(this);
                onKeyClick(mLastTouchedKey);
                return true;


            case MotionEvent.ACTION_HOVER_EXIT:
            case MotionEvent.ACTION_UP:
                return true;
        }
        return false;
    }
 */