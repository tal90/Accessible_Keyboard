package tal.tal.gestures_accessible_keyboard.keyboard.Speech;

import android.content.Context;
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

    public void Spell(String Str)
    {
        // tODO - IMPLEMENT!
    }

    private void InitTextToSpeech()
    {
        mTextToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int i)
            {
                //TODO - RETHINK ABOUT THIS!
                    /*
                    if (initStatus == TextToSpeech.SUCCESS)
                    {  myTTS.setLanguage(Locale.US);}
                    */
            }
        });
    }
}
