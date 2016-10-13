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
public class MethodThreeHandler implements IMethodHandlers, View.OnTouchListener, View.OnHoverListener
{
    private static final String TAG = "MethodThreeHandler";
    private KeysOrganizer mKeysOrganizer = null;
    private String mTypedText = "";
    //region LocateTouchedKey Global Vars
    private int mLastTouchXCoords = 0;
    private int mLastTouchYCoords = 0;
    private Key mLastTouchedKey = null;
    //endregion
    private String mLastKeyMeaning = "";        // TODO - Check if needed..
    private boolean mDeleteLastChar = true;     // TODO - Check if needed..
    private long mLastTouchDownTime = 0;
    private long mKeyPickerTimeStamp = 0;
    private final int KEY_SELECTION_TIME_WINDOW = 300;


    public MethodThreeHandler(KeysOrganizer mKeysOrganizer)
    {
        Log.v(TAG, "MethodThreeHandler - Constructor");
        this.mKeysOrganizer = mKeysOrganizer;
    }


    //region Override Methods - OnTouch, OnHover
    @Override
    public boolean onHover(View view, MotionEvent motionEvent)
    {
        return onTouch(view, motionEvent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        if (motionEvent.getPointerCount() > 1)
        {
            if (mLastTouchedKey == null)
                return false;

            if (!isStatePickerTimeIsUp())
                return false;

            mLastTouchedKey.IncrementStateBy1();
            Log.v(TAG, "CLICK OPTION 5");
            mKeysOrganizer.ReadDescription(mLastTouchedKey.getContentDescription().toString());   //getKeyMeaning());
            ReplaceKeyIfRegular(mLastTouchedKey);
            ResetStatePickingTimer();

            return true;
        }

        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_HOVER_ENTER:
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG, "ACTION_DOWN!");
                mDeleteLastChar = true;
                if (isKeyPickerTimeIsUp())
                {
                    LocateTouchedKey(view, motionEvent);
                    if (mKeysOrganizer.IsRegularKey(mLastTouchedKey.getSerialNum()))
                    {
                        Log.v(TAG, "CLICK OPTION 1");
                        mLastKeyMeaning = onKeyClick(mLastTouchedKey);
                    } else mDeleteLastChar = false;
                }
                ResetKeyPickingTimer();
                return true;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.v(TAG, "ACTION_MOVE!");

                ResetKeyPickingTimer();

                int LastKeySerNum = mLastTouchedKey.getSerialNum();
                LocateTouchedKey(view, motionEvent);
                if (mLastTouchedKey.getSerialNum() == LastKeySerNum)        // Same Key Pressed
                    return true;
                else                                                    // Another Key Was Pressed
                {
                    Log.v(TAG, "CLICK OPTION 2");
                    ReplaceKeyIfRegular(mLastTouchedKey);
                }

                return true;
            //stopped here!

            case MotionEvent.ACTION_HOVER_EXIT:
            case MotionEvent.ACTION_UP:
                Log.v(TAG, "ACTION_UP!");
                SetAllKeysState(1);
                if (mLastTouchedKey != null)
                    if (!mKeysOrganizer.IsRegularKey(mLastTouchedKey.getSerialNum()))
                    {
                        Log.v(TAG, "CLICK OPTION 3");
                        mLastKeyMeaning = onKeyClick(mLastTouchedKey);
                    }
                return true;
        }

        return true;
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

        if (mKeysOrganizer.IsRegularKey(key.getSerialNum()))
        {
            ConcatenateToTypedTextAndChief(key.getKeyMeaning());
        } else
        {
            mKeysOrganizer.CommittingAnIrregularKey(key.getKeyMeaning());
        }
        mKeysOrganizer.VibrateAfterKeyPressed();

        //      key.setState(1);

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

    //endregion

    public void SetAllKeysState(int newState)
    {
        for (Key key : mKeysOrganizer.getKeys())
        {
            key.setState(newState);
        }
    }


    public boolean ReplaceKeyIfRegular(Key key)
    {
        if (mKeysOrganizer.IsRegularKey(key.getSerialNum()))
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
            Log.v(TAG, "BackSpace Click!");
        } else
        {
            Log.v(TAG, "AVOIDED BACKSPACE!!!");
            mDeleteLastChar = true;
        }
        mLastKeyMeaning = onKeyClick(key);
    }

    public void ResetKeyPickingTimer()
    {
        mKeyPickerTimeStamp = System.currentTimeMillis();
    }

    public boolean isKeyPickerTimeIsUp()
    {
        return System.currentTimeMillis() - mKeyPickerTimeStamp > KEY_SELECTION_TIME_WINDOW;
    }


    public void ResetStatePickingTimer()
    {
        mLastTouchDownTime = System.currentTimeMillis();
    }

    public boolean isStatePickerTimeIsUp()
    {
        return System.currentTimeMillis() - mLastTouchDownTime > KEY_SELECTION_TIME_WINDOW;
    }


}




    /*
            LINES FROM LAST IMPLEMENTATION - WHEN EVERY KEY WAS AN INDEPENDENT ONE..! - The METHOD 3 THE MEKORY!

     public MethodTwoHandler(KeysOrganizer mKeysOrganizer)
    {
        Log.v(TAG, "MethodTwoHandler - Constructor");
        this.mKeysOrganizer = mKeysOrganizer;
        SetAllKeys();
    }

    public void SetAllKeys()
    {
        Log.v(TAG, "SetAllKeys");
        if (mKeysOrganizer.getMethod2KeysAllSetFlag())
            return;

        Key[] AllKeys = mKeysOrganizer.getKeys();

        for (Key key : AllKeys)
        {
            key.setOnTouchListener(this);
            key.setOnHoverListener(this);
        }

        mKeysOrganizer.setMethod2KeysAllSetFlag(true);
    }



     */

