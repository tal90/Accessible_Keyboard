package tal.tal.gestures_accessible_keyboard.keyboard.keys_area;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by talra on 24-Aug-16.
 */
public class key extends View
{
    private int mWidth;
    private int mHeight;
    private int mSerialNum;
    private int mState = 1;
    private int mMaxStates = 3;


    //region Constructors
    public key(Context context)
    {
        super(context);
    }

    public key(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public key(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public key(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    //endregion

    //region GETTERS & SETTERS
    public int getKeyWidth()
    {
        return mWidth;
    }

    public void setWidth(int mWidth)
    {
        this.mWidth = mWidth;
    }

    public int getKeyHeight()
    {
        return mHeight;
    }

    public void setHeight(int mHeight)
    {
        this.mHeight = mHeight;
    }

    public int getSerialNum()
    {
        return mSerialNum;
    }

    public void setSerialNum(int mSerialNum)
    {
        this.mSerialNum = mSerialNum;
    }

    public int getState()
    {
        return mState;
    }

    public void setState(int NewState)
    {
        if (NewState > mMaxStates || NewState < 1)
            return;

        mState = NewState;
        /*
        TODO - ADD this part - refreshing the 'contentDesc' of our key..
        String ContDesc = keysOrganizer.getMeaningStringFromKey(KeySerialNum, KeyState);
        if (ContDesc != null)
            setContentDescription(ContDesc);
            */
    }

    public int getMaxStates()
    {
        return mMaxStates;
    }

    public void setMaxStates(int mMaxStates)
    {
        this.mMaxStates = mMaxStates;
    }
    //endregion


    public void onKeyClick()
    {
        // TODO - 24.8 - 13:44 - Imlement by adding reference to current applied method..

    }

    public void onKeyTouch()
    {
        // TODO - 24.8 - 13:44 - Imlement by adding reference to current applied method..

    }

    public void IncrementStateBy1()
    {
        int tmpKeyState = mState + 1;
        if (tmpKeyState > mMaxStates)
            tmpKeyState = 1;
        setState(tmpKeyState);
    }

    public int getNextAvailableState()
    {
        int tmpKeyState = mState + 1;
        if (tmpKeyState > mMaxStates)
            tmpKeyState = 1;
        return tmpKeyState;
    }

    public String getKeyMeaning()
    {
        /* TODO - Add Functionality here...!
        return keysOrganizer.getMeaningStringFromKey(KeySerialNum, KeyState);
         */
        return "";
    }


}
