package tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.english_keyboard;

import android.view.View;
import android.widget.LinearLayout;
import java.util.HashMap;
import tal.tal.gestures_accessible_keyboard.R;
import tal.tal.gestures_accessible_keyboard.keyboard.Consts;
import tal.tal.gestures_accessible_keyboard.keyboard.KeysOrganizer;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.Key;
import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.AKeyType;



/**
 * Created by talra on 11-Mar-16.
 */
public class EnglishKeysType extends AKeyType
{
    private HashMap<String, String> mKeyMapper = null;

    public EnglishKeysType()
    {
        InitKeyMapper();
    }

    @Override
    public String getKeyboardName()
    {
        return Consts.ENGLISH_KEYBOARD_NAME;
    }

    @Override
    public boolean IsRegularKey(int KeySerialNum)       // TODO - CHECK IF REALLY NEEDED..!!
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
    public View KeyboardInitializer(KeysOrganizer keysOrganizer)
    {
        View KeyboardView = keysOrganizer.getLayoutInflater().inflate(R.layout.attached_english_layout, null);
        if (KeyboardView == null)
            return null;

        View ViewRender = KeyboardView.findViewById(R.id.Attached_Eng_ViewRenderer);
        mKeys = new Key[getNumberOfKeys()];

        mKeys[0] = (Key) ViewRender.findViewById(R.id.Eng_Custom_Key1);
        mKeys[1] = (Key) ViewRender.findViewById(R.id.Eng_Custom_Key2);
        mKeys[2] = (Key) ViewRender.findViewById(R.id.Eng_Custom_Key3);

        mKeys[3] = (Key) ViewRender.findViewById(R.id.Eng_Custom_Key4);
        mKeys[4] = (Key) ViewRender.findViewById(R.id.Eng_Custom_Key5);
        mKeys[5] = (Key) ViewRender.findViewById(R.id.Eng_Custom_Key6);

        mKeys[6] = (Key) ViewRender.findViewById(R.id.Eng_Custom_Key7);
        mKeys[7] = (Key) ViewRender.findViewById(R.id.Eng_Custom_Key8);
        mKeys[8] = (Key) ViewRender.findViewById(R.id.Eng_Custom_Key9);

        mKeys[9] = (Key) ViewRender.findViewById(R.id.Eng_Custom_Key10);
        mKeys[10] = (Key) ViewRender.findViewById(R.id.Eng_Custom_Key11);
        mKeys[11] = (Key) ViewRender.findViewById(R.id.Eng_Custom_Key12);

        for (int i = 0; i < mKeys.length; i++)
        {
            mKeys[i].setKeysOrganizer(keysOrganizer);
            mKeys[i].setSerialNum(i);
            mKeys[i].setMaxStates(Integer.valueOf(this.getMeaningStringFromKey(i, 0)));
            mKeys[i].setContentDescription(this.getMeaningStringFromKey(i, 1));
        }

        return ViewRender;
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

        Rows[0] = (LinearLayout) v.findViewById(R.id.Eng_Custom_Layout_Row1);
        Rows[1] = (LinearLayout) v.findViewById(R.id.Eng_Custom_Layout_Row2);
        Rows[2] = (LinearLayout) v.findViewById(R.id.Eng_Custom_Layout_Row3);
        Rows[3] = (LinearLayout) v.findViewById(R.id.Eng_Custom_Layout_Row4);

        return Rows;
    }

    @Override
    public Key getEnterKey(View v)
    {
        return (Key) v.findViewById(R.id.Eng_Custom_Key10);
    }

    /*
    @Override
    public boolean IsSwitchToEnglishKeyboard(int KeySerialNum, int KeyState)
    {
        return mKeyMapper.get(KeySerialNum + 100 * KeyState).equals(Consts.ENGLISH_SWITCH_KEY_NAME);
    }

*/
    @Override
    public View getKeysViewLayoutRoot(View v)
    {
        return v.findViewById(R.id.Eng_Custom_Layout_Root);
    }

    public void InitKeyMapper()
    {
        mKeyMapper = new HashMap<String,String>();

        //region KEYS MAPPING!
        mKeyMapper.put(String.valueOf(0 + 100), ".");
        mKeyMapper.put(String.valueOf(0 + 200), ",");
        mKeyMapper.put(String.valueOf(0 + 300), "!");

        mKeyMapper.put(String.valueOf(1 + 100), "a");
        mKeyMapper.put(String.valueOf(1 + 200), "b");
        mKeyMapper.put(String.valueOf(1 + 300), "c");

        mKeyMapper.put(String.valueOf(2 + 100), "d");
        mKeyMapper.put(String.valueOf(2 + 200), "e");
        mKeyMapper.put(String.valueOf(2 + 300), "f");

        mKeyMapper.put(String.valueOf(3 + 100), "g");
        mKeyMapper.put(String.valueOf(3 + 200), "h");
        mKeyMapper.put(String.valueOf(3 + 300), "i");

        mKeyMapper.put(String.valueOf(4 + 100), "j");
        mKeyMapper.put(String.valueOf(4 + 200), "k");
        mKeyMapper.put(String.valueOf(4 + 300), "l");

        mKeyMapper.put(String.valueOf(5 + 100), "m");
        mKeyMapper.put(String.valueOf(5 + 200), "n");
        mKeyMapper.put(String.valueOf(5 + 300), "o");

        mKeyMapper.put(String.valueOf(6 + 100), "p");
        mKeyMapper.put(String.valueOf(6 + 200), "q");
        mKeyMapper.put(String.valueOf(6 + 300), "r");
        mKeyMapper.put(String.valueOf(6 + 400), "s");

        mKeyMapper.put(String.valueOf(7 + 100), "t");
        mKeyMapper.put(String.valueOf(7 + 200), "u");
        mKeyMapper.put(String.valueOf(7 + 300), "v");

        mKeyMapper.put(String.valueOf(8 + 100), "w");
        mKeyMapper.put(String.valueOf(8 + 200), "x");
        mKeyMapper.put(String.valueOf(8 + 300), "y");
        mKeyMapper.put(String.valueOf(8 + 400), "z");

        // THESE KEYS HAVE ONLY 2 OR LESS OPTIONS..!! PAY ATTENTION..!
        mKeyMapper.put(String.valueOf(9 + 100), Consts.ENTER_KEY_NAME);
        mKeyMapper.put(String.valueOf(9 + 200), Consts.HEBREW_SWITCH_KEY_NAME);

        mKeyMapper.put(String.valueOf(10+ 100), Consts.SPACE_KEY_NAME);

        mKeyMapper.put(String.valueOf(11+ 100), Consts.BACKSPACE_KEY_NAME);
        mKeyMapper.put(String.valueOf(11+ 200), Consts.CAPITAL_ENGLISH_SWITCH_KEY_NAME);
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
