package tal.tal.gestures_accessible_keyboard.keyboard.methods;

import tal.tal.gestures_accessible_keyboard.keyboard.keys_area.Key;

/**
 * Created by talra on 27-May-16.
 */
public interface IMethodHandlers
{
    boolean DeleteLastTypedTextCharacter();
    String getTypedText();
    String onKeyClick(Key key);
    void ConcatenateToTypedTextAndChief(String string);
    void SetTypedTextAnEmptyString();
    void setTypedText(String str);
    void BlankLastTouchedKey();
}
