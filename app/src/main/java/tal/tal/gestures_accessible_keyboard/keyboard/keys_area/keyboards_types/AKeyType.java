package tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import java.util.HashMap;

import tal.tal.gestures_accessible_keyboard.keyboard.KeysOrganizer;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.Key;


/**
 * This abstract class represent a typical keyboard..
 * TODO - Consider change this class name to 'ATypicalKeyboard'
 * Created by talra on 10-Mar-16.
 */
public abstract class AKeyType
{
    protected Key[] mKeys;               // shall i add 'Row[] Rows' ..??
    protected EnterKeyTypes mEnterKeyType;
    public enum EnterKeyTypes {   Enter,Search,Send,Go,Next,Previous,None,Unspecified     }

    private boolean mMethod2KeysAllSetFlag = false;
    public int mNumOfKeys = 12;
    public abstract String getKeyboardName();
    public abstract boolean IsRegularKey(int KeySerialNum);
    public abstract String getMeaningStringFromKey(int KeySerialNum, int KeyState);
    public abstract LinearLayout[] getRowsArray(View v);
    public abstract HashMap<String, String> getKeyMapper();
    public abstract View KeyboardInitializer(KeysOrganizer keysOrganizer);
    public abstract Key getEnterKey(View v);
    public abstract View getKeysViewLayoutRoot(View v);

    public void setEnterKeyType(EnterKeyTypes enterKeyTypes)
    {
        this.mEnterKeyType = enterKeyTypes;
    }

    public EnterKeyTypes getEnterKeyType()
    {
        return mEnterKeyType;
    }

    public int getNumberOfKeys()
    {
        return 12;
    }
    public Key[] getKeys()
    {
        return mKeys;
    }

    public Key getKeyBySerialNumber(int SerialNumber)
    {
        if (SerialNumber < mNumOfKeys && SerialNumber >= 0)
            return mKeys[SerialNumber];
        return null;
    }

    public void setMethod2KeysAllSetFlag(boolean val)
    {
        mMethod2KeysAllSetFlag = val;
    }

    public boolean getMethod2KeysAllSetFlag()
    {
        return mMethod2KeysAllSetFlag;
    }

    /*   // TODO - Well i thought that the typedtext should be here.. but.. i don't think that way anymore..

    protected String mTypedText;
    public String getTypedText()
    {
        return mTypedText;
    }

    public void setTypedText(String typedText)
    {
        mTypedText = typedText;
    }
     */

    /*

    public abstract boolean IsSwitchToEnglishKeyboard(int KeySerialNum, int KeyState);     // TODO - CHECK IF NEEDED..!! I THINK NOT..!
    public abstract View getEnterKeyView(View v);

    //region TODO - MUST CHECK IF NEEDED..!!!!!
    public boolean IsBackSpace(int KeySerialNum, int KeyState)        // TODO - is that delete??.. wat is the difference..!
    {
        return getMeaningStringFromKey(KeySerialNum, KeyState)
                .equals(Consts.BACKSPACE_KEY_NAME);
    }

    public boolean IsSpace(int KeySerialNum, int KeyState)
    {
        return getMeaningStringFromKey(KeySerialNum, KeyState)
                .equals(Consts.SPACE_KEY_NAME);
    }
    public boolean IsEnter(int KeySerialNum, int KeyState)           // enter is usually as i see it in all kinds of keyboards.. is at the second degree of '10' key.. so..
    {
        return getMeaningStringFromKey(KeySerialNum, KeyState)
                .equals(Consts.ENTER_KEY_NAME);
    }

    public boolean IsSwitchToSymbolsKeyboard(int KeySerialNum, int KeyState)     // SYMBOLS KEYBOARD WOULD OVERRIDE THIS FUNC.. AND REWRITE THE NEEDED IMPLEMENTATION...  : 'RETURN FALSE..'
    {
        return getMeaningStringFromKey(KeySerialNum, KeyState)
                .equals(Consts.SYMBOLS_SWITCH_KEY_NAME);
    }
    public boolean IsDelete(int KeySerialNum, int KeyState)
    {
        return getMeaningStringFromKey(KeySerialNum, KeyState)
                .equals(Consts.DELETE_KEY_NAME);
    }
    //endregion
     */
}
