package tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.hebrew_keyboard;

import android.view.View;
import android.widget.LinearLayout;

import tal.tal.gestures_accessible_keyboard.R;
import tal.tal.gestures_accessible_keyboard.keyboard.Consts;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.AKeyType;

import java.util.HashMap;


/**
 * Created by talra on 11-Mar-16.
 */
public class HebrewKeysType extends AKeyType
{
    HashMap<String, String> mKeyMapper = null;

    public HebrewKeysType()
    {
        InitKeyMapper();
    }

    @Override
    public String getKeyboardName()
    {
        return Consts.HEBREW_KEYBOARD_NAME;
    }

    @Override
    public boolean IsRegularKey(int KeySerialNum)       // TODO - CHECK IF REALLY NEEDED..!!
    {
        if (KeySerialNum < 9)
            return true;
        return false;
    }


    @Override
    public LinearLayout[] getRowsArray(View v)
    {
        LinearLayout[] Rows = new  LinearLayout[4];

        Rows[0] = (LinearLayout) v.findViewById(R.id.Heb_Custom_Layout_Row1);
        Rows[1] = (LinearLayout) v.findViewById(R.id.Heb_Custom_Layout_Row2);
        Rows[2] = (LinearLayout) v.findViewById(R.id.Heb_Custom_Layout_Row3);
        Rows[3] = (LinearLayout) v.findViewById(R.id.Heb_Custom_Layout_Row4);

        return Rows;
    }

    @Override
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
/*
    @Override
    public View getKeysViewLayoutRoot(View v)
    {
        return v.findViewById(R.id.Heb_Custom_Layout_Root);
    }

    @Override
    public View getEnterKeyView(View v)
    {
        return v.findViewById(R.id.Heb_Custom_Key10);
    }

    @Override
    public boolean IsSwitchToEnglishKeyboard(int KeySerialNum, int KeyState)
    {
        return mKeyMapper.get(KeySerialNum + 100 * KeyState).equals(Consts.ENGLISH_SWITCH_KEY_NAME);
    }
  */
    public void InitKeyMapper()
    {
        mKeyMapper = new HashMap<String,String>();

        //region KEYS MAPPING!
        mKeyMapper.put(String.valueOf(0 + 100), ".");
        mKeyMapper.put(String.valueOf(0 + 200), ",");
        mKeyMapper.put(String.valueOf(0 + 300), "!");

        mKeyMapper.put(String.valueOf(2 + 100), "א");
        mKeyMapper.put(String.valueOf(2 + 200), "ב");
        mKeyMapper.put(String.valueOf(2 + 300), "ג");

        mKeyMapper.put(String.valueOf(1 + 100), "ד");
        mKeyMapper.put(String.valueOf(1 + 200), "ה");
        mKeyMapper.put(String.valueOf(1 + 300), "ו");

        mKeyMapper.put(String.valueOf(5 + 100), "ז");
        mKeyMapper.put(String.valueOf(5 + 200), "ח");
        mKeyMapper.put(String.valueOf(5 + 300), "ט");

        mKeyMapper.put(String.valueOf(4 + 100), "י");
        mKeyMapper.put(String.valueOf(4 + 200), "כ");
        mKeyMapper.put(String.valueOf(4 + 300), "ך");
        mKeyMapper.put(String.valueOf(4 + 400), "ל");

        mKeyMapper.put(String.valueOf(3 + 100), "מ");
        mKeyMapper.put(String.valueOf(3 + 200), "ם");
        mKeyMapper.put(String.valueOf(3 + 300), "נ");
        mKeyMapper.put(String.valueOf(3 + 400), "ן");

        mKeyMapper.put(String.valueOf(8 + 100), "ס");
        mKeyMapper.put(String.valueOf(8 + 200), "ע");
        mKeyMapper.put(String.valueOf(8 + 300), "פ");
        mKeyMapper.put(String.valueOf(8 + 400), "ף");

        mKeyMapper.put(String.valueOf(7 + 100), "צ");
        mKeyMapper.put(String.valueOf(7 + 200), "ץ");
        mKeyMapper.put(String.valueOf(7 + 300), "ק");

        mKeyMapper.put(String.valueOf(6 + 100), "ר");
        mKeyMapper.put(String.valueOf(6 + 200), "ש");
        mKeyMapper.put(String.valueOf(6 + 300), "ת");

        // TODO - THESE KEYS HAVE ONLY 2 OR LESS OPTIONS..!! PAY ATTENTION..!
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
        mKeyMapper.put(String.valueOf(3), String.valueOf(4));
        mKeyMapper.put(String.valueOf(4), String.valueOf(4));
        mKeyMapper.put(String.valueOf(5), String.valueOf(3));
        mKeyMapper.put(String.valueOf(6), String.valueOf(3));
        mKeyMapper.put(String.valueOf(7), String.valueOf(3));
        mKeyMapper.put(String.valueOf(8), String.valueOf(4));
        mKeyMapper.put(String.valueOf(9), String.valueOf(2));
        mKeyMapper.put(String.valueOf(10), String.valueOf(1));
        mKeyMapper.put(String.valueOf(11), String.valueOf(2));
        //endregion

    }
}
