package tal.tal.gestures_accessible_keyboard.keyboard.keys_area;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import tal.tal.gestures_accessible_keyboard.keyboard.KeysOrganizer;

/**
 * Created by talra on 24-Aug-16.
 */
public class Key extends View
{
    private int mWidth;
    private int mHeight;
    private int mSerialNum;
    private int mState = 1;
    private int mMaxStates = 3;
    private KeysOrganizer mKeysOrganizer = null;


    //region Constructors
    public Key(Context context)
    {
        super(context);
    }

    public Key(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public Key(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Key(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
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
        String ContDesc = mKeysOrganizer.getKeyMeaning(mSerialNum, mState);
        if (ContDesc != null)
            setContentDescription(ContDesc);
    }

    public void setKeysOrganizer(KeysOrganizer mKeysOrganizer)
    {
        this.mKeysOrganizer = mKeysOrganizer;
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
        return mKeysOrganizer.getKeyMeaning(mSerialNum, mState);
    }

    /**
     * drawing the key..
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        Object tag = getTag();

        if (tag == null)
            return;

        canvas.drawColor(Color.GRAY);//BLACK);          // Drawing the whole key as a black square..

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        float size = Math.min(mWidth * 0.9f, mHeight * 0.9f); // TODO - Play with this - change to '0.8f'.. until it seats well..
        paint.setTextSize(size);

        String textToDraw = tag.toString();
        Rect bounds = new Rect();
        paint.getTextBounds(textToDraw, 0, textToDraw.length(), bounds);

        int bottomMargin = (mHeight - bounds.height()) / 2;
        int leftMargin = (mWidth - bounds.width()) / 2;

        canvas.drawText(textToDraw, leftMargin, (mHeight - bottomMargin), paint);        // writing the tag on the Key..
        Rect r = new Rect(5, 0, mWidth - 5, mHeight);
        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setColor(Color.WHITE);
        canvas.drawRect(r, paint1);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

}
