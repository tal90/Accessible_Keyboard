package tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.capital_english_keyboard;

import android.view.View;
import android.widget.LinearLayout;
import java.util.HashMap;
import tal.tal.gestures_accessible_keyboard.R;
import tal.tal.gestures_accessible_keyboard.keyboard.Consts;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.AKeyType;


/**
 * Created by talra on 01-May-16.
 */
public class CapitalEnglishKeysType extends AKeyType
{
    private HashMap<String, String> mKeyMapper = null;


    public CapitalEnglishKeysType()
    {
        InitKeyMapper();
    }

    @Override
    public String getKeyboardName()
    {
        return Consts.CAPITAL_ENGLISH_KEYBOARD_NAME;
    }

    @Override
    public boolean IsRegularKey(int KeySerialNum)
    {
        if (KeySerialNum < 9)
            return true;

        return false;
    }


    public HashMap<String, String> getKeyMapper()
    {
        if (mKeyMapper == null)
            InitKeyMapper();

        return mKeyMapper;
    }

    @Override
    public String getMeaningStringFromKey(int KeySerialNum, int KeyState)
    {
        if (mKeyMapper == null)
            InitKeyMapper();

        String Map_Me = String.valueOf(KeySerialNum + 100 * KeyState);
        return mKeyMapper.get(Map_Me);
    }


    @Override
    public LinearLayout[] getRowsArray(View v)
    {
        LinearLayout[] Rows = new  LinearLayout[4];

        Rows[0] = (LinearLayout) v.findViewById(R.id.Cap_Eng_Custom_Layout_Row1);
        Rows[1] = (LinearLayout) v.findViewById(R.id.Cap_Eng_Custom_Layout_Row2);
        Rows[2] = (LinearLayout) v.findViewById(R.id.Cap_Eng_Custom_Layout_Row3);
        Rows[3] = (LinearLayout) v.findViewById(R.id.Cap_Eng_Custom_Layout_Row4);

        return Rows;
    }
/*
    @Override
    public View getEnterKeyView(View v)
    {
        return v.findViewById(R.id.Cap_Eng_Custom_Key10);
    }


    @Override
    public boolean IsSwitchToEnglishKeyboard(int KeySerialNum, int KeyState)
    {
        //TODO - UNIMPLEMENTED! - IMPLEMENT!!!
        return false;
    }

      @Override
    public View getKeysViewLayoutRoot(View v)
    {
        return v.findViewById(R.id.Cap_Eng_Custom_Layout_Root);
    }
*/
    public void InitKeyMapper()
    {
        mKeyMapper = new HashMap<String,String>();

        //region KEYS MAPPING!
        mKeyMapper.put(String.valueOf(0 + 100), ".");
        mKeyMapper.put(String.valueOf(0 + 200), ",");
        mKeyMapper.put(String.valueOf(0 + 300), "!");

        mKeyMapper.put(String.valueOf(1 + 100), "A");
        mKeyMapper.put(String.valueOf(1 + 200), "B");
        mKeyMapper.put(String.valueOf(1 + 300), "C");

        mKeyMapper.put(String.valueOf(2 + 100), "D");
        mKeyMapper.put(String.valueOf(2 + 200), "E");
        mKeyMapper.put(String.valueOf(2 + 300), "F");

        mKeyMapper.put(String.valueOf(3 + 100), "G");
        mKeyMapper.put(String.valueOf(3 + 200), "H");
        mKeyMapper.put(String.valueOf(3 + 300), "I");

        mKeyMapper.put(String.valueOf(4 + 100), "J");
        mKeyMapper.put(String.valueOf(4 + 200), "K");
        mKeyMapper.put(String.valueOf(4 + 300), "L");

        mKeyMapper.put(String.valueOf(5 + 100), "M");
        mKeyMapper.put(String.valueOf(5 + 200), "N");
        mKeyMapper.put(String.valueOf(5 + 300), "O");

        mKeyMapper.put(String.valueOf(6 + 100), "P");
        mKeyMapper.put(String.valueOf(6 + 200), "Q");
        mKeyMapper.put(String.valueOf(6 + 300), "R");
        mKeyMapper.put(String.valueOf(6 + 400), "S");

        mKeyMapper.put(String.valueOf(7 + 100), "T");
        mKeyMapper.put(String.valueOf(7 + 200), "U");
        mKeyMapper.put(String.valueOf(7 + 300), "V");

        mKeyMapper.put(String.valueOf(8 + 100), "W");
        mKeyMapper.put(String.valueOf(8 + 200), "X");
        mKeyMapper.put(String.valueOf(8 + 300), "Y");
        mKeyMapper.put(String.valueOf(8 + 400), "Z");

        // TODO - THESE KEYS HAVE ONLY 2 OPTIONS..!! PAY ATTENTION..!
        mKeyMapper.put(String.valueOf(9 + 100), Consts.ENTER_KEY_NAME);
        mKeyMapper.put(String.valueOf(9 + 200), Consts.SYMBOLS_SWITCH_KEY_NAME);

        mKeyMapper.put(String.valueOf(10+ 100), Consts.SPACE_KEY_NAME);

        mKeyMapper.put(String.valueOf(11+ 100), Consts.BACKSPACE_KEY_NAME);
        mKeyMapper.put(String.valueOf(11+ 200), Consts.ENGLISH_SWITCH_KEY_NAME);
        //endregion

        //region KEYS MAX STATES
        mKeyMapper.put(String.valueOf(0), String.valueOf(3));
        mKeyMapper.put(String.valueOf(1), String.valueOf(3));
        mKeyMapper.put(String.valueOf(2), String.valueOf(3));
        mKeyMapper.put(String.valueOf(3), String.valueOf(3));
        mKeyMapper.put(String.valueOf(4), String.valueOf(3));
        mKeyMapper.put(String.valueOf(5), String.valueOf(3));
        mKeyMapper.put(String.valueOf(6), String.valueOf(4));
        mKeyMapper.put(String.valueOf(7), String.valueOf(3));
        mKeyMapper.put(String.valueOf(8), String.valueOf(4));
        mKeyMapper.put(String.valueOf(9), String.valueOf(2));
        mKeyMapper.put(String.valueOf(10), String.valueOf(1));
        mKeyMapper.put(String.valueOf(11), String.valueOf(2));
        //endregion
    }
}
