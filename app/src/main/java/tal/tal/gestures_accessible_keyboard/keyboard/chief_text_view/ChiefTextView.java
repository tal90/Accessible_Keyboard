package tal.tal.gestures_accessible_keyboard.keyboard.chief_text_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.widget.TextView;

import tal.tal.gestures_accessible_keyboard.keyboard.Consts;

/**
 * Created by talra on 29-Aug-16.
 */
public class ChiefTextView extends TextView
{
    private int mFontSize;
    private Context mContext;
    private boolean mIsTypedTextPassword = false;

    public void setUpChief(Context context)
    {
        mContext = context;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mFontSize = sharedPreferences.getInt(Consts.SharedPref_FontSize_TAG, 85);
        setTextSize(mFontSize);
    }

    public void setIsTypedTextPassword(boolean mIsTypedTextPassword)
    {
        this.mIsTypedTextPassword = mIsTypedTextPassword;
    }

    public void SetTextInsideTheChief(String Str)       // TODO - recheck if ReImplementation needed..!!
    {
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
}
