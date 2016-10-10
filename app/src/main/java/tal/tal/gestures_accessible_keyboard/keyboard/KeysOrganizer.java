package tal.tal.gestures_accessible_keyboard.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;

import tal.tal.gestures_accessible_keyboard.MainIME;
import tal.tal.gestures_accessible_keyboard.R;
import tal.tal.gestures_accessible_keyboard.keyboard.Speech.SpeechHelper;
import tal.tal.gestures_accessible_keyboard.keyboard.chief_text_view.ChiefTextView;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.Key;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.AKeyType;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.capital_english_keyboard.CapitalEnglishKeysType;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.english_keyboard.EnglishKeysType;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.hebrew_keyboard.HebrewKeysType;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.symbols_keyboard.SymbolsKeysType;
import tal.tal.gestures_accessible_keyboard.keyboard.methods.IMethodHandlers;
import tal.tal.gestures_accessible_keyboard.keyboard.methods.MethodOneHandler;
import tal.tal.gestures_accessible_keyboard.keyboard.methods.MethodThreeHandler;
import tal.tal.gestures_accessible_keyboard.keyboard.methods.MethodTwoHandler;

/**
 * Created by talra on 24-Aug-16.
 */
public class KeysOrganizer implements IKeysOperations
{
    private static final String TAG = "KeysOrganizer";
    private Context mContext = null;
    private MainIME mMainIME = null;
    private View mKeyboardView = null;
    private ChiefTextView mChiefTextView = null;
    private AKeyType mAKeyType = null;
    private IMethodHandlers mIMethodHandlers = null;
    private SpeechHelper mSpeechHelper = null;
    private Vibrator mVibrator;
    public boolean mVibrationIsON = true;

    public enum KeyboardsTypes
    {
        Hebrew, English, CapitalEnglish, Symbols
    }


    public KeysOrganizer(Context context, MainIME mainIME)
    {
        Log.v(TAG, "KeysOrganizer Constructor");
        mContext = context;
        mMainIME = mainIME;
        mSpeechHelper = new SpeechHelper(context);
    }

    public View switchKeyboardType(String PassedStr, final KeyboardsTypes keyboardType, boolean isOnCreateCall)
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
        setUpChiefTextView(PassedStr); // TODO !!!! - lahzor Le ZE!! REIMPLEMENT!!!
        setMethodHandler(mKeyboardView);
        //    if (!isOnCreateCall)
        mMainIME.setInputView(mKeyboardView);       // REREAD DOCUMNETATION - NEEDED ONLY WHILE SWITCHING KEYBOARD WITH A BUTTON..

        return mKeyboardView;
    }

    public void setActionKey(EditorInfo editorInfo)
    {
        Log.v(TAG, "setActionKey");
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
        Log.v(TAG, "setUpChiefTextView");
        // TODO - Implement! from mechinsm with changes..!!!!
        // TODO - most implementation would appear in the 'chiefs' class..!!!!

        // MAY include - settext(PassedStr)..

        // debug!! - for now // TODO - MAKE IT GOOD!!!
        mChiefTextView = (ChiefTextView) mKeyboardView.findViewById(R.id.Attached_Chief_TextView);
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
                MethodOneHandler MOH = new MethodOneHandler(this, 100); // getCompatibleSwipeMinDistance(Layout_Root));
                Layout_Root.setOnTouchListener(MOH);
                Layout_Root.setOnHoverListener(MOH);
                mIMethodHandlers = MOH;
                return;
                */
            case 2:
                /*
                MethodTwoHandler MTH = new MethodTwoHandler(this);
                Layout_Root.setOnTouchListener(MTH);
                Layout_Root.setOnHoverListener(MTH);
                mIMethodHandlers = MTH;
                */
            case 3:
                /*

                */
        }

        // Debuggggg!! erase later..!!

        MethodThreeHandler MTH = new MethodThreeHandler(this);
        Layout_Root.setOnTouchListener(MTH);
        Layout_Root.setOnHoverListener(MTH);
        mIMethodHandlers = MTH;


    }

    @Override
    public void BackSpaceKeyClick(IMethodHandlers Method)
    {
        InputConnection ic = mMainIME.getCurrentInputConnection();

        if (Method.DeleteLastTypedTextCharacter())
            SetTextInsideTheChief(Method.getTypedText());
        else ic.deleteSurroundingText(1, 0);
    }

    @Override
    public void SetTextInsideTheChief(String str)
    {
        mChiefTextView.SetTextInsideTheChief(str);
    }

    @Override
    public void DropToTextEditor(String string)
    {
        InputConnection ic = mMainIME.getCurrentInputConnection();
        ic.commitText(string, 1);
    }

    @Override
    public void FlushFromChiefAndCurrWord()
    {
        String TypedTxt = mIMethodHandlers.getTypedText();
        if (TypedTxt.length() > 0 || mChiefTextView.getText().length() > 0)
        {
            InputConnection ic = mMainIME.getCurrentInputConnection();
            ic.commitText(TypedTxt, 1);
            mIMethodHandlers.SetTypedTextAnEmptyString();
            SetTextInsideTheChief("");
        }
    }


    public void CommittingAnIrregularKey(String ClickedKeyName)
    {
        Log.v(TAG, "CommittingAnIrregularKey " + ClickedKeyName);
        switch (ClickedKeyName)
        {
            case Consts.SPACE_KEY_NAME:
                FlushFromChiefAndCurrWord();
                DropToTextEditor(" ");
                break;
            // TODO - STOPPED HERE!!!
            case Consts.BACKSPACE_KEY_NAME:
                Key BS = mAKeyType.getKeyBySerialNumber(11);
                BS.setContentDescription("");
                MediaPlayer md = MediaPlayer.create(mContext, R.raw.woosh_del_sound);
                md.setLooping(false);
                md.start();
                BackSpaceKeyClick(mIMethodHandlers);
                BS.setContentDescription(Consts.BACKSPACE_KEY_NAME);
                break;
            case Consts.ENTER_KEY_NAME:
                EnterKeyClick();
                break;
            case Consts.SYMBOLS_SWITCH_KEY_NAME:
                switchKeyboardType(mIMethodHandlers.getTypedText(), KeyboardsTypes.Symbols, false);
                break;
            case Consts.ENGLISH_SWITCH_KEY_NAME:
                switchKeyboardType(mIMethodHandlers.getTypedText(), KeyboardsTypes.English, false);
                break;
            case Consts.HEBREW_SWITCH_KEY_NAME:
                switchKeyboardType(mIMethodHandlers.getTypedText(), KeyboardsTypes.Hebrew, false);
                break;
            case Consts.CAPITAL_ENGLISH_SWITCH_KEY_NAME:
                switchKeyboardType(mIMethodHandlers.getTypedText(), KeyboardsTypes.CapitalEnglish, false);
                break;
        }
    }


    public void EnterKeyClick()
    {
        InputConnection ic = mMainIME.getCurrentInputConnection();
        FlushFromChiefAndCurrWord();

        AKeyType.EnterKeyTypes CurrEnterType = mAKeyType.getEnterKeyType();

        if (CurrEnterType == AKeyType.EnterKeyTypes.Search)
            ic.performEditorAction(EditorInfo.IME_ACTION_SEARCH);
        else if (CurrEnterType == AKeyType.EnterKeyTypes.Send)
            ic.performEditorAction(EditorInfo.IME_ACTION_SEND);
        else if (CurrEnterType == AKeyType.EnterKeyTypes.Go)
            ic.performEditorAction(EditorInfo.IME_ACTION_GO);
        else if (CurrEnterType == AKeyType.EnterKeyTypes.Next)
            ic.performEditorAction(EditorInfo.IME_ACTION_NEXT);
        else if (CurrEnterType == AKeyType.EnterKeyTypes.Previous)
            ic.performEditorAction(EditorInfo.IME_ACTION_PREVIOUS);
        else                                            // a regular 'ENTER'..
            ic.commitText("\n", 1);
    }


    public void VibrateAfterKeyPressed()
    {
        if (!mVibrationIsON)
            return;
        if (mVibrator == null)
            mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

        mVibrator.vibrate(55);
    }


    // region Setters & Getters
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
        mIMethodHandlers.setTypedText(typedText);
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

    public String getKeyboardName()
    {
        return mAKeyType.getKeyboardName();
    }

    public boolean IsRegularKey(int KeySerialNumber)
    {
        return mAKeyType.IsRegularKey(KeySerialNumber);
    }

    public View getKeysLayoutRoot()
    {
        return mAKeyType.getKeysViewLayoutRoot(mKeyboardView);
    }

    public Context getContext()
    {
        return mContext;
    }

    public boolean getMethod2KeysAllSetFlag()
    {
        return mAKeyType.getMethod2KeysAllSetFlag();
    }

    public void setMethod2KeysAllSetFlag(boolean Value)         // TODO - wait to Method 3..!
    {
        mAKeyType.setMethod2KeysAllSetFlag(Value);
    }

    public Key[] getKeys()
    {
        return mAKeyType.getKeys();
    }
    // endregion

    public void ReadDescription(String DescStr)
    {
        mSpeechHelper.ReadDescription(DescStr);
    }

    public void Spell(String StrToSpell)
    {
        mSpeechHelper.Spell(StrToSpell);
    }

}
