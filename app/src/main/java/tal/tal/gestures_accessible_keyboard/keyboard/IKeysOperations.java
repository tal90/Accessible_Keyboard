package tal.tal.gestures_accessible_keyboard.keyboard;

import tal.tal.gestures_accessible_keyboard.keyboard.methods.IMethodHandlers;

/**
 * Created by talra on 21-Sep-16.
 */
public interface IKeysOperations
{
    void BackSpaceKeyClick(IMethodHandlers Method);
    void SetTextInsideTheChief(String str);
    void DropToTextEditor(String string);
    void FlushFromChiefAndCurrWord();

    /*
    void ConcatenateToTypedTextAndChief(String Str);
     */


}
