package tal.tal.gestures_accessible_keyboard;

import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import java.util.Locale;

import tal.tal.gestures_accessible_keyboard.keyboard.KeysOrganizer;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.AKeyType;

/**
 * Created by talra on 23-Aug-16.
 */
public class MainIME extends InputMethodService
{
    private static final String TAG = "MainIME";
    private View mKeyboardView = null;
    private AKeyType mAKeyType = null;
    private KeysOrganizer mKeysOrganizer = null;


    @Override
    public View onCreateInputView()
    {
        Log.v(TAG, "onCreateInputView");

        KeysOrganizer.KeyboardsTypes keyboardType;
        String Lang = Locale.getDefault().getCountry();
        if (Lang.equalsIgnoreCase("IL"))
        {
            keyboardType = KeysOrganizer.KeyboardsTypes.Hebrew;
        } else if (Lang.equalsIgnoreCase("EN"))
        {
            keyboardType = KeysOrganizer.KeyboardsTypes.English;
        } else
        {
            keyboardType = KeysOrganizer.KeyboardsTypes.English;        // Switch to the Default for other languages - English keyboard..
        }


        mKeysOrganizer = new KeysOrganizer(getApplicationContext(), this);
        mKeyboardView = mKeysOrganizer.switchKeyboardType("", keyboardType);


        return mKeyboardView;
        // TODO - recheck with TweetMechnism's func that i'vn't forgot anything!

    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting)
    {
        super.onStartInputView(info, restarting);

        /* TODO : ADD this function!
        mKeysOrgainzer.RefreshEnterKeyIcon();
        */
    }

    //region Setters & Getters
    public View getKeyboardView()
    {
        return mKeyboardView;
    }

    public void setKeyboardView(View mKeyboardView)
    {
        this.mKeyboardView = mKeyboardView;
    }

    public AKeyType getAKeyType()
    {
        return mAKeyType;
    }

    public void setAKeyType(AKeyType mAKeyType)
    {
        this.mAKeyType = mAKeyType;
    }
    //endregion
}
