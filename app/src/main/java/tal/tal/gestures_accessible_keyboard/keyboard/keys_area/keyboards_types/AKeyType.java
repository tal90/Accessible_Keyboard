package tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types;

import android.view.View;
import android.widget.LinearLayout;
import java.util.HashMap;


/**
 * This interface helps to determine the type of a specific key..
 * Created by talra on 10-Mar-16.
 */
public abstract class AKeyType
{
    public abstract String getKeyboardName();
    public abstract boolean IsRegularKey(int KeySerialNum);
    public abstract String getMeaningStringFromKey(int KeySerialNum, int KeyState);      // TODO - THINK OF A BETTER NAME..!!
    public abstract LinearLayout[] getRowsArray(View v);
    public abstract HashMap<String, String> getKeyMapper();

    /*

    public abstract boolean IsSwitchToEnglishKeyboard(int KeySerialNum, int KeyState);     // TODO - CHECK IF NEEDED..!! I THINK NOT..!
    public abstract View getKeysViewLayoutRoot(View v);
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
