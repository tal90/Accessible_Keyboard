package tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.symbols_keyboard;

import android.util.Log;
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
    HashMap<String, String> mKeyMapper = null;

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
    public String getInvisibleRightKeyMeaning()
    {
        if (mKeyMapper == null)
            InitKeyMapper();

        return mKeyMapper.get(Consts.INVISIBLE_RIGHT_KEY_NAME);
    }

    @Override
    public String getInvisibleLeftKeyMeaning()
    {
        if (mKeyMapper == null)
            InitKeyMapper();

        return mKeyMapper.get(Consts.INVISIBLE_LEFT_KEY_NAME);
    }

    @Override
    public HashMap<String, String> getKeyMapper()
    {
        if (mKeyMapper == null)
            InitKeyMapper();

        return mKeyMapper;
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
            mKeys[i].setContentDescription(this.getDescriptionStringFromKey(i, 1));
        }

        return KeyboardView;
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

    @Override
    public String getDescriptionStringFromKey(int KeySerialNum, int KeyState)
    {
        if (mKeyMapper == null)
            InitKeyMapper();

        String Map_Me = String.valueOf(KeySerialNum + 100 * KeyState + 50);
        return mKeyMapper.get(Map_Me);
    }

    public void InitKeyMapper()
    {
        mKeyMapper = new HashMap<String,String>();

        //region KEYS MAPPING!
        mKeyMapper.put(String.valueOf(0 + 100), "1");
        mKeyMapper.put(String.valueOf(0 + 200), "!");
        mKeyMapper.put(String.valueOf(0 + 300), ".");
        mKeyMapper.put(String.valueOf(0 + 400), ",");

        mKeyMapper.put(String.valueOf(1 + 100), "2");
        mKeyMapper.put(String.valueOf(1 + 200), "@");
        mKeyMapper.put(String.valueOf(1 + 300), "+");
        mKeyMapper.put(String.valueOf(1 + 400), "-");

        mKeyMapper.put(String.valueOf(2 + 100), "3");
        mKeyMapper.put(String.valueOf(2 + 200), "#");
        mKeyMapper.put(String.valueOf(2 + 300), "<");
        mKeyMapper.put(String.valueOf(2 + 400), ">");

        mKeyMapper.put(String.valueOf(3 + 100), "4");
        mKeyMapper.put(String.valueOf(3 + 200), "$");
        mKeyMapper.put(String.valueOf(3 + 300), "{");
        mKeyMapper.put(String.valueOf(3 + 400), "}");

        mKeyMapper.put(String.valueOf(4 + 100), "5");
        mKeyMapper.put(String.valueOf(4 + 200), "%");
        mKeyMapper.put(String.valueOf(4 + 300), ":");
        mKeyMapper.put(String.valueOf(4 + 400), ";");

        mKeyMapper.put(String.valueOf(5 + 100), "6");
        mKeyMapper.put(String.valueOf(5 + 200), "[");
        mKeyMapper.put(String.valueOf(5 + 300), "]");
        mKeyMapper.put(String.valueOf(5 + 400), "'");

        mKeyMapper.put(String.valueOf(6 + 100), "7");
        mKeyMapper.put(String.valueOf(6 + 200), "&");
        mKeyMapper.put(String.valueOf(6 + 300), "\\");
        mKeyMapper.put(String.valueOf(6 + 400), "\"");

        mKeyMapper.put(String.valueOf(7 + 100), "8");
        mKeyMapper.put(String.valueOf(7 + 200), "*");
        mKeyMapper.put(String.valueOf(7 + 300), "/");

        mKeyMapper.put(String.valueOf(8 + 100), "9");
        mKeyMapper.put(String.valueOf(8 + 200), "?");        // TODO - natun leshinuy!
        mKeyMapper.put(String.valueOf(8 + 300), "(");
        mKeyMapper.put(String.valueOf(8 + 400), ")");

        //SOME OF THESE KEYS HAVE ONLY 2 OR LESS OPTIONS..!! PAY ATTENTION..!
        mKeyMapper.put(String.valueOf(9 + 100), Consts.ENTER_KEY_NAME);

        mKeyMapper.put(String.valueOf(10+ 100), "0");
        mKeyMapper.put(String.valueOf(10+ 200), Consts.SPACE_KEY_NAME);

        mKeyMapper.put(String.valueOf(11+ 100), Consts.BACKSPACE_KEY_NAME);

        mKeyMapper.put(Consts.INVISIBLE_LEFT_KEY_NAME, Consts.HEBREW_SWITCH_KEY_NAME);
        mKeyMapper.put(Consts.INVISIBLE_RIGHT_KEY_NAME, Consts.ENGLISH_SWITCH_KEY_NAME);
        //endregion

        //region KEYS MAX STATES
        mKeyMapper.put(String.valueOf(0), String.valueOf(4));
        mKeyMapper.put(String.valueOf(1), String.valueOf(4));
        mKeyMapper.put(String.valueOf(2), String.valueOf(4));
        mKeyMapper.put(String.valueOf(3), String.valueOf(4));
        mKeyMapper.put(String.valueOf(4), String.valueOf(4));
        mKeyMapper.put(String.valueOf(5), String.valueOf(4));
        mKeyMapper.put(String.valueOf(6), String.valueOf(4));
        mKeyMapper.put(String.valueOf(7), String.valueOf(3));
        mKeyMapper.put(String.valueOf(8), String.valueOf(3));
        mKeyMapper.put(String.valueOf(9), String.valueOf(2));
        mKeyMapper.put(String.valueOf(10), String.valueOf(2));
        mKeyMapper.put(String.valueOf(11), String.valueOf(2));
        //endregion

        //region Special Keys Content Description
        mKeyMapper.put(String.valueOf(0 + 150), "1");
        mKeyMapper.put(String.valueOf(0 + 250), "Exclamation mark");           // '!'
        mKeyMapper.put(String.valueOf(0 + 350), "Dot");                        // '.'
        mKeyMapper.put(String.valueOf(0 + 450), "Comma");                      // ','

        mKeyMapper.put(String.valueOf(1 + 150), "2");
        mKeyMapper.put(String.valueOf(1 + 250), "At sign");                    // '@'
        mKeyMapper.put(String.valueOf(1 + 350), "Plus sign");                  // '+'
        mKeyMapper.put(String.valueOf(1 + 450), "Minus sign");                 // '-'

        mKeyMapper.put(String.valueOf(2 + 150), "3");
        mKeyMapper.put(String.valueOf(2 + 250), "Hash");                       // '#'
        mKeyMapper.put(String.valueOf(2 + 350), "Less-than sign");             // '<'
        mKeyMapper.put(String.valueOf(2 + 450), "Greater-than sign");          // '>'

        mKeyMapper.put(String.valueOf(3 + 150), "4");
        mKeyMapper.put(String.valueOf(3 + 250), "Dollar sign");                // '$'
        mKeyMapper.put(String.valueOf(3 + 350), "Opening Curly Bracket");      // '{'
        mKeyMapper.put(String.valueOf(3 + 450), "Closing Curly Bracket");      // '}'

        mKeyMapper.put(String.valueOf(4 + 150), "5");
        mKeyMapper.put(String.valueOf(4 + 250), "Percentage sign");           // '%'
        mKeyMapper.put(String.valueOf(4 + 350), "Colon sign");                // ':'
        mKeyMapper.put(String.valueOf(4 + 450), "Semicolon sign");            // ';'

        mKeyMapper.put(String.valueOf(5 + 150), "6");
        mKeyMapper.put(String.valueOf(5 + 250), "Opening Square Bracket");    // '['
        mKeyMapper.put(String.valueOf(5 + 350), "Closing Square Bracket");    // ']'
        mKeyMapper.put(String.valueOf(5 + 450), "Single Quote");              // "'"

        mKeyMapper.put(String.valueOf(6 + 150), "7");
        mKeyMapper.put(String.valueOf(6 + 250), "Ampersand Sign");            // '&'
        mKeyMapper.put(String.valueOf(6 + 350), "Back Slash");                // '\'
        mKeyMapper.put(String.valueOf(6 + 450), "Quotation mark");            // '"'

        mKeyMapper.put(String.valueOf(7 + 150), "8");
        mKeyMapper.put(String.valueOf(7 + 250), "Asterisk");                  // '*'
        mKeyMapper.put(String.valueOf(7 + 350), "Slash Sign");                // '/'

        mKeyMapper.put(String.valueOf(8 + 150), "9");
        mKeyMapper.put(String.valueOf(8 + 250), "Question Mark");            // '?'
        mKeyMapper.put(String.valueOf(8 + 350), "Opening Parentheses");      // '('
        mKeyMapper.put(String.valueOf(8 + 450), "Closing Parentheses");      // ')'

        mKeyMapper.put(String.valueOf(9 + 150), Consts.ENTER_KEY_NAME);

        mKeyMapper.put(String.valueOf(10+ 150), "0");
        mKeyMapper.put(String.valueOf(10+ 250), Consts.SPACE_KEY_NAME);

        mKeyMapper.put(String.valueOf(11+ 150), Consts.BACKSPACE_KEY_NAME);

        //endregion
    }

}
