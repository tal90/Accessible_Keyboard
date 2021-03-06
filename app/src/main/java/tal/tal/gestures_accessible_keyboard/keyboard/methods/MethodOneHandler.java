package tal.tal.gestures_accessible_keyboard.keyboard.methods;

import android.content.Context;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import tal.tal.gestures_accessible_keyboard.keyboard.Consts;
import tal.tal.gestures_accessible_keyboard.keyboard.KeysOrganizer;
import tal.tal.gestures_accessible_keyboard.keyboard.Speech.SpeechHelper;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.Key;

/**
 * Created by talra on 24-Aug-16.
 */
public class MethodOneHandler implements IMethodHandlers, View.OnTouchListener, View.OnHoverListener
{
    private static final String TAG = "MethodOneHandler";
    private KeysOrganizer mKeysOrganizer = null;
    private String mTypedText = "";
    //region LocateTouchedKey Global Vars
    private int mLastTouchXCoords = 0;
    private int mLastTouchYCoords = 0;
    private Key mLastTouchedKey = null;
    //endregion
    //region DetectAndHandleValidSwipe Global Vars
    private final int SWIPE_TIME_SLICE = 370; // 310;
    private long mLastTouchDownTime = 0;
    private int mMin_Swipe_Distance = 100;
    //endregion
    private View mKeysLayoutRoot = null;
    //region OnTouch Global Vars
    private float mDownX, mDownY, mUpX, mUpY;
    private boolean mDeleteLastChar = true;
    //endregion
    private long ChillTimerTimeStamp = 0;

    public MethodOneHandler(KeysOrganizer _KeysOrganizer, int _Min_Swipe_Distance)
    {
        this.mKeysOrganizer = _KeysOrganizer;
        this.mMin_Swipe_Distance = _Min_Swipe_Distance;
    }
    //endregion

    @Override
    public boolean onHover(View view, MotionEvent motionEvent)
    {
        if (mKeysLayoutRoot == null)
            mKeysLayoutRoot = mKeysOrganizer.getKeysLayoutRoot();

        return onTouch(mKeysLayoutRoot, motionEvent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_HOVER_ENTER:
            case MotionEvent.ACTION_DOWN:
                mDownX = motionEvent.getX();
                mDownY = motionEvent.getY();
                mLastTouchDownTime = System.currentTimeMillis();
                //        onKeyClick(LocateTouchedKey(view, motionEvent));
                return true;

            case MotionEvent.ACTION_HOVER_EXIT:
            case MotionEvent.ACTION_UP:
                mUpX = motionEvent.getX();
                mUpY = motionEvent.getY();

                if (DetectAndHandleValidSwipe(mDownX - mUpX, mDownY - mUpY))
                    return true;

                onKeyClick(LocateTouchedKey(view, motionEvent));
                return true;
        }
        return false;
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


    public boolean DetectAndHandleValidSwipe(float DeltaX, float DeltaY)
    {
        if (System.currentTimeMillis() - mLastTouchDownTime > SWIPE_TIME_SLICE)
            return false;

        if (mLastTouchedKey == null)
            return false;

        // swipe horizontal?
        if (Math.abs(DeltaX) > mMin_Swipe_Distance)
        {
            // left or right
            if (DeltaX < 0)
            {
                onLeftToRightSwipe();
                mLastTouchedKey.setState(1);              // resetting to default key state..
                return true;
            }
            if (DeltaX > 0)
            {
                onRightToLeftSwipe();
                mLastTouchedKey.setState(1);              // resetting to default key state..
                return true;
            }
        } else
        {
            Log.i(TAG, "Swipe was only " + Math.abs(DeltaX) + " long horizontally, need at least " + mMin_Swipe_Distance);
            // return false; // We don't consume the event
        }

        // swipe vertical?
        if (Math.abs(DeltaY) > mMin_Swipe_Distance)
        {
            // top or down
            if (DeltaY < 0)
            {
                onTopToBottomSwipe();
                mLastTouchedKey.setState(1);              // resetting to default key state..
                return true;
            }
            if (DeltaY > 0)
            {
                onBottomToTopSwipe();
                mLastTouchedKey.setState(1);              // resetting to default key state..
                return true;
            }
        } else
        {
            Log.i(TAG, "Swipe was only " + Math.abs(DeltaX) + " long vertically, need at least " + mMin_Swipe_Distance);
            // return false; // We don't consume the event
        }

        return false; // no swipe horizontally and no swipe vertically
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
        onKeyClick(key);
    }


    public void ResetChillTimer()
    {
        ChillTimerTimeStamp = System.currentTimeMillis();
    }

    public boolean isChillTimeIsUp()
    {
        return ChillTimerTimeStamp + 900 < System.currentTimeMillis();
    }


    //region Swipes Of All Kinds Handling
    public void onRightToLeftSwipe()
    {
        Log.i(TAG, "RightToLeftSwipe!");

        if (mLastTouchedKey == null)
            return;

        int NewState = 0;
        switch (mKeysOrganizer.getKeyboardName())
        {
            case Consts.HEBREW_KEYBOARD_NAME:
                NewState = 2;
                break;
            case Consts.SYMBOLS_KEYBOARD_NAME:              // Same as regular eng keyboard
            case Consts.CAPITAL_ENGLISH_KEYBOARD_NAME:      // Same as regular eng keyboard
            case Consts.ENGLISH_KEYBOARD_NAME:
                NewState = 4;
                break;
        }

        if (mLastTouchedKey.getMaxStates() < NewState)
            return;

        mLastTouchedKey.setState(NewState);
        mKeysOrganizer.BackSpaceKeyClick(this);          // Every swipe deletes last character since the first pressing does invokes 'mLastTouchedKey.OnCurrKeyClick()'
        mKeysOrganizer.ReadDescription(mLastTouchedKey.getContentDescription().toString());
        onKeyClick(mLastTouchedKey);
    }

    public void onLeftToRightSwipe()
    {
        Log.i(TAG, "LeftToRightSwipe!");

        if (mLastTouchedKey == null)
            return;

        int NewState = 0;
        switch (mKeysOrganizer.getKeyboardName())
        {
            case Consts.HEBREW_KEYBOARD_NAME:
                NewState = 4;
                break;
            case Consts.SYMBOLS_KEYBOARD_NAME:              // Same as regular eng keyboard
            case Consts.CAPITAL_ENGLISH_KEYBOARD_NAME:      // Same as regular eng keyboard
            case Consts.ENGLISH_KEYBOARD_NAME:
                NewState = 2;
                break;
        }

        if (mLastTouchedKey.getMaxStates() < NewState)
            return;

        mLastTouchedKey.setState(NewState);
        mKeysOrganizer.BackSpaceKeyClick(this);          // Every swipe deletes last character since the first pressing does invokes 'mLastTouchedKey.OnCurrKeyClick()'
        mKeysOrganizer.ReadDescription(mLastTouchedKey.getContentDescription().toString());
        onKeyClick(mLastTouchedKey);
    }

    public void onTopToBottomSwipe()
    {
        Log.i(TAG, "onTopToBottomSwipe!");

        if (mLastTouchedKey == null)
            return;

        int NewState = 0;
        switch (mKeysOrganizer.getKeyboardName())
        {
            case Consts.HEBREW_KEYBOARD_NAME:
                NewState = 3;
                break;
            case Consts.SYMBOLS_KEYBOARD_NAME:              // Same as regular eng keyboard
            case Consts.CAPITAL_ENGLISH_KEYBOARD_NAME:      // Same as regular eng keyboard
            case Consts.ENGLISH_KEYBOARD_NAME:
                NewState = 3;
                break;
        }

        if (mLastTouchedKey.getMaxStates() < NewState)
            return;

        mLastTouchedKey.setState(NewState);
        mKeysOrganizer.BackSpaceKeyClick(this);          // Every swipe deletes last character since the first pressing does invokes 'mLastTouchedKey.OnCurrKeyClick()'
        mKeysOrganizer.ReadDescription(mLastTouchedKey.getContentDescription().toString());
        onKeyClick(mLastTouchedKey);
    }

    public void onBottomToTopSwipe()
    {
        Log.i(TAG, "onBottomToTopSwipe!");

        if (mLastTouchedKey == null)
            return;

        int NewState = 0;
        switch (mKeysOrganizer.getKeyboardName())
        {
            case Consts.HEBREW_KEYBOARD_NAME:
                NewState = 1;
                break;
            case Consts.SYMBOLS_KEYBOARD_NAME:              // Same as regular eng keyboard
            case Consts.CAPITAL_ENGLISH_KEYBOARD_NAME:      // Same as regular eng keyboard
            case Consts.ENGLISH_KEYBOARD_NAME:
                NewState = 1;
                break;
        }

        mLastTouchedKey.setState(NewState);
        mKeysOrganizer.BackSpaceKeyClick(this);          // Every swipe deletes last character since the first pressing does invokes 'mLastTouchedKey.OnCurrKeyClick()'
        mKeysOrganizer.ReadDescription(mLastTouchedKey.getContentDescription().toString());
        onKeyClick(mLastTouchedKey);
    }


    //endregion


    //region Override methods
    @Override
    public String onKeyClick(Key key)
    {
        if (key == null)
            return "";

        String ReturnedString = key.getKeyMeaning();
        Log.v(TAG, ReturnedString + " Key Clicked!");

        if (mKeysOrganizer.IsRegularKey(key))
        {
            ConcatenateToTypedTextAndChief(key.getKeyMeaning());
        } else
        {
            mKeysOrganizer.CommittingAnIrregularKey(key.getKeyMeaning());
        }
        mKeysOrganizer.VibrateAfterKeyPressed();

        return ReturnedString;
    }

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

}


// DUMPSTER FOR NOW..
/*
@Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_HOVER_ENTER:
            case MotionEvent.ACTION_DOWN:
                mDownX = motionEvent.getX();
                mDownY = motionEvent.getY();
                mLastTouchDownTime = System.currentTimeMillis();

                ResetChillTimer();
//                return true;


                mDeleteLastChar = true;
                LocateTouchedKey(view, motionEvent);

                if (mKeysOrganizer.IsRegularKey(mLastTouchedKey))
                {
                    Log.v(TAG, "CLICK OPTION 1");
                    onKeyClick(mLastTouchedKey);
                } else mDeleteLastChar = false;
                return true;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.v(TAG, "ACTION_MOVE!");

                if (!isChillTimeIsUp())
                {
                    Log.v(TAG, "CHILLL MANNN!!");
                    return true;
                }

                ResetChillTimer();

                int LastKeySerNum = mLastTouchedKey.getSerialNum();
                LocateTouchedKey(view, motionEvent);
                if (mLastTouchedKey.getSerialNum() != LastKeySerNum)        // Same Key Pressed
                {
                    Log.v(TAG, "CLICK OPTION 1");
                    ReplaceKeyIfRegular(mLastTouchedKey);

                } else
                    return true;                                                // Another Key Was Pressed

                return true;

            case MotionEvent.ACTION_HOVER_EXIT:
            case MotionEvent.ACTION_UP:
                mUpX = motionEvent.getX();
                mUpY = motionEvent.getY();

                if (DetectAndHandleValidSwipe(mDownX - mUpX, mDownY - mUpY))
                    return true;

 //               onKeyClick(LocateTouchedKey(view, motionEvent));
                ReplaceLastCharacter(LocateTouchedKey(view, motionEvent));
                return true;
        }
        return false;
    }


 */