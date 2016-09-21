package tal.tal.gestures_accessible_keyboard.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import tal.tal.gestures_accessible_keyboard.MainIME;
import tal.tal.gestures_accessible_keyboard.R;
import tal.tal.gestures_accessible_keyboard.keyboard.chief_text_view.ChiefTextView;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.Key;
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
    private AKeyType mAKeyType = null;

    public enum KeyboardsTypes
    {
        Hebrew, English, CapitalEnglish, Symbols
    }


    public KeysOrganizer(Context context, MainIME mainIME)
    {
        mContext = context;
        mMainIME = mainIME;
    }

    public View switchKeyboardType(String PassedStr, final KeyboardsTypes keyboardType)
    {
        Log.v(TAG, "switchKeyboadType");
        mKeyboardView = null;

        switch (keyboardType)
        {
            case English:
                mAKeyType = new EnglishKeysType();
                break;
            case CapitalEnglish:
                mAKeyType = new CapitalEnglishKeysType();
                break;
            case Symbols:
                mAKeyType = new SymbolsKeysType();
                break;
            default:
            case Hebrew:
                mAKeyType = new HebrewKeysType();
                break;
        }

        mKeyboardView = mAKeyType.KeyboardInitializer(this);
        setUpChiefTextView(PassedStr); // HOPPA!!! - moved from mechnizsm!
        setMethodHandler(mKeyboardView);
        mMainIME.setInputView(mKeyboardView);       // REREAD DOCUMNETATION - NEEDED ONLY WHILE SWITCHING KEYBOARD WITH A BUTTON..

        return mKeyboardView;
    }

    public void setActionKey(EditorInfo editorInfo)
    {
        int mask = editorInfo.imeOptions & EditorInfo.IME_MASK_ACTION;        // mask would help to determine the 'Enter' Key type..
        Key enterKey = getEnterKey();
        String str;

        boolean isKeyAvailable = (enterKey != null);

        switch (mask)
        {
            case EditorInfo.IME_ACTION_SEARCH:
                mAKeyType.setEnterKeyType(AKeyType.EnterKeyTypes.Search);
                if (isKeyAvailable)
                {
                    str = mContext.getString(R.string.Search);

                    enterKey.setContentDescription(str);
                }
                break;
            case EditorInfo.IME_ACTION_SEND:
                mAKeyType.setEnterKeyType(AKeyType.EnterKeyTypes.Send);
                if (isKeyAvailable)
                {
                    str = mContext.getString(R.string.Send);

                    enterKey.setContentDescription(str);
                }
                break;
            case EditorInfo.IME_ACTION_GO:
                mAKeyType.setEnterKeyType(AKeyType.EnterKeyTypes.Go);
                if (isKeyAvailable)
                {
                    str = mContext.getString(R.string.Go);

                    enterKey.setContentDescription(str);
                }
                break;
            case EditorInfo.IME_ACTION_NEXT:
                mAKeyType.setEnterKeyType(AKeyType.EnterKeyTypes.Next);
                if (isKeyAvailable)
                {
                    str = mContext.getString(R.string.Next);

                    enterKey.setContentDescription(str);
                }
                break;
            case EditorInfo.IME_ACTION_PREVIOUS:
                mAKeyType.setEnterKeyType(AKeyType.EnterKeyTypes.Previous);
                if (isKeyAvailable)
                {
                    str = mContext.getString(R.string.Previous);

                    enterKey.setContentDescription(str);
                }
                break;
            default:                        // Regular 'Enter' button..
                mAKeyType.setEnterKeyType(AKeyType.EnterKeyTypes.Enter);
                if (isKeyAvailable)
                {
                    str = mContext.getString(R.string.Enter);

                    enterKey.setContentDescription(str);
                }
                break;
        }
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

        // MAY include - settext(PassedStr)..
    }

    public void setMethodHandler(View v)
    {
        // TODO - Implement! - taken from 'setKeysAreaOnTouchListener' on keysorganizer!

        View Layout_Root = mAKeyType.getKeysViewLayoutRoot(v);

        if (Layout_Root == null)
            return;

        switch (getUsedMethodNumber())
        {
            case 1:
                /*
                MethodOneTouchHandler MOTH = new MethodOneTouchHandler(this, getCompatibleSwipeMinDistance(Layout_Root));
                Layout_Root.setOnTouchListener(MOTH);
                if (mTweetMechanism.IsTalkBackActive())
                    Layout_Root.setOnHoverListener(MOTH);
                mMethodInUse = MOTH;
                return;
                */
            case 2:
                /*
                MethodTwoTouchHandler MTTH = new MethodTwoTouchHandler(this);
                Layout_Root.setOnTouchListener(MTTH);
                if (mTweetMechanism.IsTalkBackActive())
                {
                    Layout_Root.setOnHoverListener(MTTH);
                    MTTH.SetIsTalkBackActive(true);
                }
                mMethodInUse = MTTH;
                return;
                */
            case 3:
                /*
                MethodThreeTouchHandler M3TH = new MethodThreeTouchHandler(this);
                Layout_Root.setOnTouchListener(M3TH);
                if (mTweetMechanism.IsTalkBackActive())
                {
                    Layout_Root.setOnHoverListener(M3TH);
                    M3TH.SetIsTalkBackActive(true);
                }
                mMethodInUse = M3TH;
                return;
*/
        }

    }

    public String getKeyMeaning(int KeySerialNum, int KeyState)
    {
        if (mAKeyType == null)
            return null;

        return mAKeyType.getMeaningStringFromKey(KeySerialNum, KeyState);
    }

    public Key getEnterKey()
    {
        if (mKeyboardView == null)
            return null;
        return (Key) mAKeyType.getEnterKey(mKeyboardView);
    }

    public void setTypedText(String typedText)
    {
        mAKeyType.setTypedText(typedText);
        mChiefTextView.setText(typedText);
    }

    public void setIsTypedTextPassword(boolean isIt)
    {
        //TODO - add some stuff here?
        if (mChiefTextView == null)
            return;

        mChiefTextView.setIsTypedTextPassword(isIt);
    }

    public int getUsedMethodNumber()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences.getInt(Consts.SharedPref_Method_TAG, 1);
    }

}
