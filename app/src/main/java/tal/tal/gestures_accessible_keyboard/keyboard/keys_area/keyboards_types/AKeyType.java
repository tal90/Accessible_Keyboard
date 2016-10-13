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
    protected Key[] mKeys;
    protected EnterKeyTypes mEnterKeyType;
    public enum EnterKeyTypes {   Enter,Search,Send,Go,Next,Previous,None,Unspecified     }

    public int mNumOfKeys = 12;

    public abstract String getKeyboardName();
    public abstract boolean IsRegularKey(int KeySerialNum);
    public abstract String getMeaningStringFromKey(int KeySerialNum, int KeyState);
    public abstract String getDescriptionStringFromKey(int KeySerialNum, int KeyState);
    public abstract LinearLayout[] getRowsArray(View v);
    public abstract HashMap<String, String> getKeyMapper();
    public abstract View KeyboardInitializer(KeysOrganizer keysOrganizer);
    public abstract Key getEnterKey(View v);
    public abstract View getKeysViewLayoutRoot(View v);
    public abstract String getInvisibleRightKeyMeaning();
    public abstract String getInvisibleLeftKeyMeaning();

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
        return mNumOfKeys;
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

}
