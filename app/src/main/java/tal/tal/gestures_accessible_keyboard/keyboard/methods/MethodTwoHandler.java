package tal.tal.gestures_accessible_keyboard.keyboard.methods;

import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.Key;

/**
 * Created by talra on 24-Aug-16.
 */
public class MethodTwoHandler implements IMethodHandlers
{
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
