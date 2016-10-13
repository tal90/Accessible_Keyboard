package tal.tal.gestures_accessible_keyboard.keyboard.Speech;

import android.content.Context;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

/**
 * Created by talra on 25-Sep-16.
 */
public class SpeechHelper
{
    private static final String TAG = "SpeechHelper";
    TextToSpeech mTextToSpeech = null;
    Context mContext = null;

    public SpeechHelper(Context mContext)
    {
        this.mContext = mContext;
        InitTextToSpeech();
    }

    public void ReadDescription(String DescStr)
    {
        Log.v(TAG, "SpeechHelper - ReadDescription - " + DescStr);

        if (mTextToSpeech == null)
            InitTextToSpeech();

        mTextToSpeech.speak(DescStr, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void Spell(final String SpellStr)
    {
        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (SpellStr.equals(""))
                            return;

                        mTextToSpeech.speak(SpellStr.charAt(0) + "", TextToSpeech.QUEUE_FLUSH, null);

                        if (SpellStr.length() > 1)
                        {
                            String sub = SpellStr.substring(1, SpellStr.length());
                            Spell(sub);
                        }
                    }
                }, 1000);
    }
    private void InitTextToSpeech()
    {
        mTextToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int i)
            {
                if (i == TextToSpeech.SUCCESS)
                    ReadDescription("Accessoble Keyboard");
                /*   {  myTTS.setLanguage(Locale.US);}
                    */
            }
        });
    }
}
