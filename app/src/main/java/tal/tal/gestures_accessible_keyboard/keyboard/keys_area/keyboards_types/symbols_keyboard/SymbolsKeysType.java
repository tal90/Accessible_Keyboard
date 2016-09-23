package tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.symbols_keyboard;

import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;

import tal.tal.gestures_accessible_keyboard.R;
import tal.tal.gestures_accessible_keyboard.keyboard.Consts;
import tal.tal.gestures_accessible_keyboard.keyboard.KeysOrganizer;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.Key;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.AKeyType;


/**
 * Created by talra on 01-May-16.
 */
public class SymbolsKeysType extends AKeyType
{
    HashMap<String, String> KeyMapper = null;

    public SymbolsKeysType()
    {
        InitKeyMapper();
    }

    @Override
    public String getKeyboardName()
    {
        return Consts.SYMBOLS_KEYBOARD_NAME;
    }

    @Override
    public boolean IsRegularKey(int KeySerialNum)
    {
        if (KeySerialNum < 9)
            return true;
        return false;
    }

    @Override
    public HashMap<String, String> getKeyMapper()
    {
        if (KeyMapper == null)
            InitKeyMapper();

        return KeyMapper;
    }

    @Override
    public View KeyboardInitializer(KeysOrganizer keysOrganizer)
    {
        View KeyboardView = keysOrganizer.getLayoutInflater().inflate(R.layout.attached_symbols_layout, null);
        if (KeyboardView == null)
            return null;

        View ViewRender = KeyboardView.findViewById(R.id.Attached_Sym_ViewRenderer);
        mKeys = new Key[getNumberOfKeys()];

        mKeys[0] = (Key) ViewRender.findViewById(R.id.Sym_Custom_Key1);
        mKeys[1] = (Key) ViewRender.findViewById(R.id.Sym_Custom_Key2);
        mKeys[2] = (Key) ViewRender.findViewById(R.id.Sym_Custom_Key3);

        mKeys[3] = (Key) ViewRender.findViewById(R.id.Sym_Custom_Key4);
        mKeys[4] = (Key) ViewRender.findViewById(R.id.Sym_Custom_Key5);
        mKeys[5] = (Key) ViewRender.findViewById(R.id.Sym_Custom_Key6);

        mKeys[6] = (Key) ViewRender.findViewById(R.id.Sym_Custom_Key7);
        mKeys[7] = (Key) ViewRender.findViewById(R.id.Sym_Custom_Key8);
        mKeys[8] = (Key) ViewRender.findViewById(R.id.Sym_Custom_Key9);

        mKeys[9] = (Key) ViewRender.findViewById(R.id.Sym_Custom_Key10);
        mKeys[10] = (Key) ViewRender.findViewById(R.id.Sym_Custom_Key11);
        mKeys[11] = (Key) ViewRender.findViewById(R.id.Sym_Custom_Key12);

        for (int i = 0; i < mKeys.length; i++)
        {
            mKeys[i].setKeysOrganizer(keysOrganizer);
            mKeys[i].setSerialNum(i);
            mKeys[i].setMaxStates(Integer.valueOf(this.getMeaningStringFromKey(i, 0)));
            mKeys[i].setContentDescription(this.getMeaningStringFromKey(i, 1));
        }

        return KeyboardView;
    }

    @Override
    public String getMeaningStringFromKey(int KeySerialNum, int KeyState)
    {
        if (KeyMapper == null)
            InitKeyMapper();

        String Map_Me = String.valueOf(KeySerialNum + 100 * KeyState);
        return KeyMapper.get(Map_Me);
    }



    @Override
    public LinearLayout[] getRowsArray(View v)
    {
        LinearLayout[] Rows = new  LinearLayout[4];

        Rows[0] = (LinearLayout) v.findViewById(R.id.Sym_Custom_Layout_Row1);
        Rows[1] = (LinearLayout) v.findViewById(R.id.Sym_Custom_Layout_Row2);
        Rows[2] = (LinearLayout) v.findViewById(R.id.Sym_Custom_Layout_Row3);
        Rows[3] = (LinearLayout) v.findViewById(R.id.Sym_Custom_Layout_Row4);

        return Rows;
    }

    @Override
    public Key getEnterKey(View v)
    {
        return (Key) v.findViewById(R.id.Sym_Custom_Key10);
    }

/*
    @Override
    public boolean IsSwitchToEnglishKeyboard(int KeySerialNum, int KeyState)
    {
        //TODO - UNIMPLEMENTED! - IMPLEMENT!!!
        return false;
    }
    */

    @Override
    public View getKeysViewLayoutRoot(View v)
    {
        return v.findViewById(R.id.Sym_Custom_Layout_Root);
    }

    public void InitKeyMapper()
    {
        KeyMapper = new HashMap<String,String>();

        //region KEYS MAPPING!
        KeyMapper.put(String.valueOf(0 + 100), "1");
        KeyMapper.put(String.valueOf(0 + 200), "!");
        KeyMapper.put(String.valueOf(0 + 300), ".");
        KeyMapper.put(String.valueOf(0 + 400), ",");

        KeyMapper.put(String.valueOf(1 + 100), "2");
        KeyMapper.put(String.valueOf(1 + 200), "@");
        KeyMapper.put(String.valueOf(1 + 300), "+");
        KeyMapper.put(String.valueOf(1 + 400), "-");

        KeyMapper.put(String.valueOf(2 + 100), "3");
        KeyMapper.put(String.valueOf(2 + 200), "#");
        KeyMapper.put(String.valueOf(2 + 300), "<");
        KeyMapper.put(String.valueOf(2 + 400), ">");

        KeyMapper.put(String.valueOf(3 + 100), "4");
        KeyMapper.put(String.valueOf(3 + 200), "$");
        KeyMapper.put(String.valueOf(3 + 300), "{");
        KeyMapper.put(String.valueOf(3 + 400), "}");

        KeyMapper.put(String.valueOf(4 + 100), "5");
        KeyMapper.put(String.valueOf(4 + 200), "%");
        KeyMapper.put(String.valueOf(4 + 300), ":");
        KeyMapper.put(String.valueOf(4 + 400), ";");

        KeyMapper.put(String.valueOf(5 + 100), "6");
        KeyMapper.put(String.valueOf(5 + 200), "[");
        KeyMapper.put(String.valueOf(5 + 300), "]");
        KeyMapper.put(String.valueOf(5 + 400), "'");

        KeyMapper.put(String.valueOf(6 + 100), "7");
        KeyMapper.put(String.valueOf(6 + 200), "&");
        KeyMapper.put(String.valueOf(6 + 300), "\\");
        KeyMapper.put(String.valueOf(6 + 400), "\"");

        KeyMapper.put(String.valueOf(7 + 100), "8");
        KeyMapper.put(String.valueOf(7 + 200), "*");
        KeyMapper.put(String.valueOf(7 + 300), "/");

        KeyMapper.put(String.valueOf(8 + 100), "9");
        KeyMapper.put(String.valueOf(8 + 200), "?");        // TODO - natun leshinuy!
        KeyMapper.put(String.valueOf(8 + 300), "(");
        KeyMapper.put(String.valueOf(8 + 400), ")");

        //SOME OF THESE KEYS HAVE ONLY 2 OR LESS OPTIONS..!! PAY ATTENTION..!
        KeyMapper.put(String.valueOf(9 + 100), Consts.ENTER_KEY_NAME);
        KeyMapper.put(String.valueOf(9 + 200), Consts.HEBREW_SWITCH_KEY_NAME);

        KeyMapper.put(String.valueOf(10+ 100), "0");
        KeyMapper.put(String.valueOf(10+ 200), Consts.SPACE_KEY_NAME);

        KeyMapper.put(String.valueOf(11+ 100), Consts.BACKSPACE_KEY_NAME);
        KeyMapper.put(String.valueOf(11+ 200), Consts.ENGLISH_SWITCH_KEY_NAME);
        //endregion

        //region KEYS MAX STATES
        KeyMapper.put(String.valueOf(0), String.valueOf(4));
        KeyMapper.put(String.valueOf(1), String.valueOf(4));
        KeyMapper.put(String.valueOf(2), String.valueOf(4));
        KeyMapper.put(String.valueOf(3), String.valueOf(4));
        KeyMapper.put(String.valueOf(4), String.valueOf(4));
        KeyMapper.put(String.valueOf(5), String.valueOf(4));
        KeyMapper.put(String.valueOf(6), String.valueOf(4));
        KeyMapper.put(String.valueOf(7), String.valueOf(3));
        KeyMapper.put(String.valueOf(8), String.valueOf(3));
        KeyMapper.put(String.valueOf(9), String.valueOf(2));
        KeyMapper.put(String.valueOf(10), String.valueOf(2));
        KeyMapper.put(String.valueOf(11), String.valueOf(2));
        //endregion
    }

}
