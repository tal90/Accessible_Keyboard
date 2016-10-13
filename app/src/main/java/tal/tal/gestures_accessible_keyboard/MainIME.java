package tal.tal.gestures_accessible_keyboard;

import android.inputmethodservice.InputMethodService;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import java.util.Locale;

import tal.tal.gestures_accessible_keyboard.keyboard.KeysOrganizer;
import tal.tal.gestures_accessible_keyboard.keyboard.Speech.SpeechHelper;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.AKeyType;

/**
 * Created by talra on 23-Aug-16.
 */
public class MainIME extends InputMethodService
{
    private static final String TAG = "MainIME";
    private View mKeyboardView = null;
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

        // DEBUGGGG - i need to hear the bitch!
        keyboardType = KeysOrganizer.KeyboardsTypes.English;


        // DEBUGGG!!!!! DEBUGG!!!
        keyboardType = KeysOrganizer.KeyboardsTypes.Symbols;

        mKeysOrganizer = new KeysOrganizer(getApplicationContext(), this);
        mKeyboardView = mKeysOrganizer.switchKeyboardType("", keyboardType);

        return mKeyboardView;
        // TODO - recheck with TweetMechnism's func that i'vn't forgot anything!
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting)
    {
        super.onStartInputView(info, restarting);
        Log.v(TAG, "onStartInputView");

        mKeysOrganizer.setActionKey(info);
        mKeysOrganizer.setTypedText("");


        int u1 = InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_PASSWORD;
        int u2 = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        int u3 = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;       // should be visible..??? according to the documentation.. it should...
        int u4 = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;

        if (info.inputType == u1 || info.inputType == u2 || info.inputType == u3 || info.inputType == u4)
        {
            mKeysOrganizer.setIsTypedTextPassword(true);
        } else
        {
            mKeysOrganizer.setIsTypedTextPassword(false);
        }

        mKeysOrganizer.ReadOutLoudKeyboardName();


        /*
        if (TweetView != null && keysOrganizer != null)
        {
            keysOrganizer.setKeysAreaOnTouchListener(TweetView);
            keysOrganizer.mVibrationIsON = sharedPreferences.getBoolean(Consts.SharedPref_Vibrate_TAG, true);
        }
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

    //endregion
}
