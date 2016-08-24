package tal.tal.gestures_accessible_keyboard.keyboard.keys_area.keyboards_types.symbols_keyboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import tal.tal.gestures_accessible_keyboard.R;


/**
 * Created by talra on 01-May-16.
 */
public class SymbolsViewRenderer extends LinearLayout
{
    private static final String TAG = "SymViewRenderer";

    //region Constructors with renderInit..
    public SymbolsViewRenderer(Context context)
    {
        super(context);
        renderInit();
    }

    public SymbolsViewRenderer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        renderInit();
    }

    public SymbolsViewRenderer(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        renderInit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SymbolsViewRenderer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        renderInit();
    }
    //endregion

    private void renderInit()
    {
        try
        {
            inflate(getContext(), R.layout.custom_symbols_layout, this);
        }
        catch (Exception ex)
        {
            Log.v(TAG, ex.toString());
        }
    }
}