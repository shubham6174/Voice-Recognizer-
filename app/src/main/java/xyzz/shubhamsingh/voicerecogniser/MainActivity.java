package xyzz.shubhamsingh.voicerecogniser;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech myTTS;
    private SpeechRecognizer mySpeechRecognizer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                mySpeechRecognizer.startListening(intent);

            }
        });

        initializeTextToSpeech();
        initializeSpeechRecognizer();


    }

    private void initializeSpeechRecognizer() {
        if(SpeechRecognizer.isRecognitionAvailable(this)){

            mySpeechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mySpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                /**
                 * Called when the endpointer is ready for the user to start speaking.
                 *
                 * @param params parameters set by the recognition service. Reserved for future use.
                 */
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                /**
                 * The user has started to speak.
                 */
                @Override
                public void onBeginningOfSpeech() {

                }

                /**
                 * The sound level in the audio stream has changed. There is no guarantee that this method will
                 * be called.
                 *
                 * @param rmsdB the new RMS dB value
                 */
                @Override
                public void onRmsChanged(float rmsdB) {

                }

                /**
                 * More sound has been received. The purpose of this function is to allow giving feedback to the
                 * user regarding the captured audio. There is no guarantee that this method will be called.
                 *
                 * @param buffer a buffer containing a sequence of big-endian 16-bit integers representing a
                 *               single channel audio stream. The sample rate is implementation dependent.
                 */
                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                /**
                 * Called after the user stops speaking.
                 */
                @Override
                public void onEndOfSpeech() {

                }

                /**
                 * A network or recognition error occurred.
                 *
                 * @param error code is defined in {@link SpeechRecognizer}
                 */
                @Override
                public void onError(int error) {

                }

                /**
                 * Called when recognition results are ready.
                 *
                 * @param    {@code
                 *                ArrayList<String>} format use {@link Bundle#getStringArrayList(String)} with
                 *                {@link SpeechRecognizer#RESULTS_RECOGNITION} as a parameter. A float array of
                 *                confidence values might also be given in {@link SpeechRecognizer#CONFIDENCE_SCORES}.
                 */
                @Override
                public void onResults(Bundle bundle) {
                    List<String> results=bundle.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION


                    );
                    processResult(results.get(0));


                }

                /**
                 * Called when partial recognition results are available. The callback might be called at any
                 * time between {@link #onBeginningOfSpeech()} and {@link #onResults(Bundle)} when partial
                 * results are ready. This method may be called zero, one or multiple times for each call to
                 * {@link SpeechRecognizer#}, depending on the speech recognition
                 * service implementation.  To request partial results, use
                 * {@link
                 *
                 * @param partialResults the returned results. To retrieve the results in
                 *                       ArrayList&lt;String&gt; format use {@link Bundle#getStringArrayList(String)} with
                 *                       {@link SpeechRecognizer#RESULTS_RECOGNITION} as a parameter
                 */
                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                /**
                 * Reserved for adding future events.
                 *
                 * @param eventType the type of the occurred event
                 * @param params    a Bundle containing the passed parameters
                 */
                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void processResult(String command) {

        command=command.toLowerCase();


        if(command.indexOf("what") !=-1){


            if(command.indexOf("your name") !=-1){

                speak("My Name Is RAAJU DINKAR");

            }

            if(command.indexOf("time") !=-1){


                Date now=new Date();
                String time= DateUtils.formatDateTime(this,now.getTime(),DateUtils.FORMAT_SHOW_TIME);
                speak("THE TIME NOW IS "+time);
            }


        }else if(command.indexOf("open") !=-1){

            if(command.indexOf("browser")!=-1){

                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com"));
                startActivity(intent);


            }
        }



    }

    private void initializeTextToSpeech() {
        myTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            /**
             * Called to signal the completion of the TextToSpeech engine initialization.
             *
             * @param status {@link TextToSpeech#SUCCESS} or {@link TextToSpeech#ERROR}.
             */
            @Override
            public void onInit(int status) {
                if(myTTS.getEngines().size()==0){

                    Toast.makeText(MainActivity.this,"THERE IS NO TTS ENGINE ON UR DEVICE!!!",Toast.LENGTH_LONG).show();

                    finish();
                } else{


                    myTTS.setLanguage(Locale.US);
                    speak("HELLO!!! I'M Ready to Work!!!");
                }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT >=21) {
            myTTS.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);





        } else{
            myTTS.speak(message,TextToSpeech.QUEUE_FLUSH,null);
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();
        myTTS.shutdown();
    }
}
