package tal.tal.gestures_accessible_keyboard.keyboard.chief_text_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by talra on 29-Aug-16.
 */
public class ChiefTextView extends TextView
{
    public ChiefTextView(Context context)
    {
        super(context);
    }

    public ChiefTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ChiefTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChiefTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
