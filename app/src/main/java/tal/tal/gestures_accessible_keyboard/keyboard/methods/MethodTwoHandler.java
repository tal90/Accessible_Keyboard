package tal.tal.gestures_accessible_keyboard.keyboard.methods;

import android.provider.Settings;
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
    private SpeechHelper mSpeechHelper = null;
    //region LocateTouchedKey Global Vars
    private int mLastTouchXCoords = 0;
    private int mLastTouchYCoords = 0;
    private Key mLastTouchedKey = null;
    //endregion
    private int mFingerCounter = 0;
    private long mLastTouchDownTime = 0;
    private final int KEY_SELECTION_TIME_WINDOW = 1000;

    public MethodTwoHandler(KeysOrganizer mKeysOrganizer)
    {
        Log.v(TAG, "MethodTwoHandler - Constructor");
        this.mKeysOrganizer = mKeysOrganizer;
        mSpeechHelper = new SpeechHelper(mKeysOrganizer.getContext());
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
        return mLastTouchedKey;
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
    public void onKeyClick(Key key)
    {
        Log.v(TAG, key.getKeyMeaning() + " Key Clicked!");

        if (mKeysOrganizer.IsRegularKey(key.getSerialNum()))
        {
            ConcatenateToTypedTextAndChief(key.getKeyMeaning());
        } else
        {
            mKeysOrganizer.CommittingAnIrregularKey(key.getKeyMeaning());
        }
        mKeysOrganizer.VibrateAfterKeyPressed();

        key.setState(1);
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

    //region Override Methods - OnTouch, OnHover
    @Override
    public boolean onHover(View view, MotionEvent motionEvent)
    {
        return onTouch(view, motionEvent);
    }

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

    //endregion


    public boolean isSelectingTimeWindowIsUp()
    {//true if new selection should occur..!
        return System.currentTimeMillis() - mLastTouchDownTime > KEY_SELECTION_TIME_WINDOW;
    }
}
