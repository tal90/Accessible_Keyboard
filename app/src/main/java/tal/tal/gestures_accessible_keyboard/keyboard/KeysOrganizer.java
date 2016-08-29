package tal.tal.gestures_accessible_keyboard.keyboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import tal.tal.gestures_accessible_keyboard.MainIME;
import tal.tal.gestures_accessible_keyboard.keyboard.chief_text_view.ChiefTextView;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.AKeyType;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.capital_english_keyboard.CapitalEnglishKeysType;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.english_keyboard.EnglishKeysType;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.hebrew_keyboard.HebrewKeysType;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.symbols_keyboard.SymbolsKeysType;

/**
 * Created by talra on 24-Aug-16.
 */
public class KeysOrganizer
{
    private static final String TAG = "KeysOrganizer";
    private Context mContext = null;
    private MainIME mMainIME = null;
    private View mKeyboardView = null;
    private ChiefTextView mChiefTextView = null;

    public enum KeyboardsTypes {Hebrew, English, CapitalEnglish, Symbols}


    public KeysOrganizer(Context context, MainIME mainIME)
    {
        mContext = context;
        mMainIME = mainIME;
    }

    public View switchKeyboardType(String PassedStr, final KeyboardsTypes keyboardType)
    {
        Log.v(TAG, "switchKeyboadType");

        mKeyboardView = null;
        AKeyType aKeyType;

        switch (keyboardType)
        {
            case English:
                aKeyType = new EnglishKeysType();
                break;
            case CapitalEnglish:
                aKeyType = new CapitalEnglishKeysType();
                break;
            case Symbols:
                aKeyType = new SymbolsKeysType();
                break;
            default:
            case Hebrew:
                aKeyType = new HebrewKeysType();
                break;
        }

        mKeyboardView = aKeyType.KeyboardInitializer(this);
        setUpChiefTextView(PassedStr); // HOPPA!!! - moved from mechnizsm!
        setMethodHandler();
        mMainIME.setInputView(mKeyboardView);       // REREAD DOCUMNETATION - NEEDED ONLY WHILE SWITCHING KEYBOARD WITH A BUTTON..
        // TODO - COMPARE AND CHECK IF FINISHED.. !! SSIM LEV!!
        return mKeyboardView;
    }

    public LayoutInflater getLayoutInflater()
    {
        if (mMainIME == null)
            return null;

        return mMainIME.getLayoutInflater();
    }

    public void setUpChiefTextView(String PassedStr)
    {
        // TODO - Implement! from mechinsm with changes..!
        // TODO - most implementation would appear in the 'chiefs' class..!!!
    }

    public void setMethodHandler()
    {
        // TODO - Implement! - taken from 'setKeysAreaOnTouchListener' on keysorganizer!
    }
}
