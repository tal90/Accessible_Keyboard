package tal.tal.gestures_accessible_keyboard.keyboard.methods;

import android.content.Context;
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
    private final int SWIPE_TIME_SLICE = 310;
    private long mLastTouchDownTime = 0;
    private int mMin_Swipe_Distance = 100;
    //endregion
    private View mKeysLayoutRoot = null;
    //region OnTouch Global Vars
    private float mDownX, mDownY, mUpX, mUpY;
    //endregion

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
        return mLastTouchedKey; //null;
    }


    public boolean DetectAndHandleValidSwipe(float DeltaX, float DeltaY)
    {
        if (System.currentTimeMillis() - mLastTouchDownTime > SWIPE_TIME_SLICE)
            return false;

        if (mLastTouchedKey == null)        // TODO - TAL.. SIM LEV LEZE!!! - ASK THIS Question AGAIN IN THE ONTOUCH FUNC!!
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
                NewState = 2;       // TODO - Make sure..!
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
                NewState = 3;       // TODO - Make sure..!
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
                NewState = 1;       // TODO - Make sure..!
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
        String ReturnedString = key.getKeyMeaning();
        Log.v(TAG, ReturnedString + " Key Clicked!");

        if (mKeysOrganizer.IsRegularKey(key.getSerialNum()))
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
    //endregion

}

    /*              // TODO - This code was at the 8th line in the function DetectAndHandleValidSwipe.. this code checks if the current key is del or space.. and if yes, return false.. i dont know y i need this - meanwhile this is here.. for later use..
        int LastTouchedKeySerialNumber = mLastTouchedKey.getKeySerialNum();
        int LastTouchedKeyState = mLastTouchedKey.getKeyState();
        if (mkeysOrganizer.IsKeyBackSpace(LastTouchedKeySerialNumber, LastTouchedKeyState) || mkeysOrganizer.IsKeyDelete(LastTouchedKeySerialNumber, LastTouchedKeyState))
            return false;
         */


                /* WAS UNDER 'ACTION_UP' - on onTouch Function..!
                if (System.currentTimeMillis() - mLastTouchDownTime < 250)      // Checking for short click.. problem is.. talkback reads the letter even if its a short click.. user might think that it has been clicked...
                {
                    mSpeechHelper.ReadDescription("SS");
                    Log.v(TAG, "TOO SHORT CLICK..!");
                    return false;
                }
                */