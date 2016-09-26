package tal.tal.gestures_accessible_keyboard.keyboard.methods;

import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.Key;

/**
 * Created by talra on 24-Aug-16.
 */
public class MethodThreeHandler implements IMethodHandlers
{

    /*

     public MethodTwoHandler(KeysOrganizer mKeysOrganizer)
    {
        Log.v(TAG, "MethodTwoHandler - Constructor");
        this.mKeysOrganizer = mKeysOrganizer;
        SetAllKeys();
    }

    public void SetAllKeys()
    {
        Log.v(TAG, "SetAllKeys");
        if (mKeysOrganizer.getMethod2KeysAllSetFlag())
            return;

        Key[] AllKeys = mKeysOrganizer.getKeys();

        for (Key key : AllKeys)
        {
            key.setOnTouchListener(this);
            key.setOnHoverListener(this);
        }

        mKeysOrganizer.setMethod2KeysAllSetFlag(true);
    }



     */






    @Override
    public boolean DeleteLastTypedTextCharacter()
    {
        return false;
    }

    @Override
    public String getTypedText()
    {
        return null;
    }

    @Override
    public void onKeyClick(Key key)
    {

    }

    @Override
    public void ConcatenateToTypedTextAndChief(String string)
    {

    }

    @Override
    public void SetTypedTextAnEmptyString()
    {

    }

    @Override
    public void setTypedText(String str)
    {

    }
}
