package tal.tal.gestures_accessible_keyboard.keyboard.methods;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import tal.tal.gestures_accessible_keyboard.keyboard.KeysOrganizer;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.Key;

/**
 * Created by talra on 24-Aug-16.
 */
public class MethodTwoHandler implements IMethodHandlers, View.OnTouchListener, View.OnHoverListener
{
    private static final String TAG = "MethodTwoHandler";
    private KeysOrganizer mKeysOrganizer = null;
    private String mTypedText = "";


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
        Key k = (Key) view;
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_HOVER_ENTER:
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG, k.getKeyMeaning() + " - ACTION DOWN!!");
                Log.v(TAG, "NUM OF Fingers = " + motionEvent.getPointerCount());
                return true;

            case MotionEvent.ACTION_HOVER_EXIT:
            case MotionEvent.ACTION_UP:
                Log.v(TAG, k.getKeyMeaning() + " - ACTION UP!!");
                Log.v(TAG, "NUM OF Fingers = " + motionEvent.getPointerCount());
                
                return true;
        }
        return false;
    }

    //endregion

}
