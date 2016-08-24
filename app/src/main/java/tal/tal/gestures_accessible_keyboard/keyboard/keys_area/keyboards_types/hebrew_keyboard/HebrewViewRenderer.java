package tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.hebrew_keyboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import tal.tal.gestures_accessible_keyboard.R;


/**
 * Created by talra on 11-Mar-16.
 */
public class HebrewViewRenderer extends LinearLayout
{
    private static final String TAG = "HebrewViewRender";

    //region Constructors with renderInit..
    public HebrewViewRenderer(Context context)
    {
        super(context);
        renderInit();
    }

    public HebrewViewRenderer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        renderInit();
    }

    public HebrewViewRenderer(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        renderInit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HebrewViewRenderer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        renderInit();
    }
    //endregion

    private void renderInit()
    {
        try
        {
            inflate(getContext(), R.layout.custom_hebrew_layout, this);
        }
        catch (Exception ex)
        {
            Log.v(TAG, ex.toString());
        }
    }
}
