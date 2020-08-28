package com.gtappdevelopers.ibmbot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v1.Assistant;
import com.ibm.watson.assistant.v1.model.MessageInput;
import com.ibm.watson.assistant.v1.model.MessageOptions;
import com.ibm.watson.assistant.v1.model.MessageResponse;
import com.ibm.watson.assistant.v1.model.OutputData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String API_KEY="FuI2ZmHKgsqaRLKqR23z2oOm4E-Imce7THVBwoBzxBIi";
    private String URL="https://api.au-syd.assistant.watson.cloud.ibm.com/instances/53e1ef8b-692d-4229-ad68-1055b7ade08c";
    private String WORKSPACE_ID="cb714eef-5f1a-472a-9a66-719887c520e6";
    EditText et_user_statement;
    ImageButton mic_btn;
    ImageButton send_btn;
    private RecyclerView msg_recycler;
    private ArrayList<MessageModel>msgmodel=new ArrayList<>();
    private MultiViewAdapter msgAdapter;
    private final int REQ_CODE_SPEECH_INPUT = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mic_btn=findViewById(R.id.micbtn);
        msg_recycler=findViewById(R.id.msg_recycler);
        et_user_statement=findViewById(R.id.et_user_statement);
        send_btn=findViewById(R.id.send_btn);
        msgAdapter=new MultiViewAdapter(msgmodel,MainActivity.this);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        msg_recycler.setLayoutManager(manager);
        msg_recycler.setAdapter(msgAdapter);


        mic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            promptSpeechInput();
            }
        });


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_user_statement.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter your message", Toast.LENGTH_SHORT).show();
                }
                else {
                    msgmodel.add(new MessageModel(et_user_statement.getText().toString(),2));
                    msgAdapter.notifyDataSetChanged();

//                    new MyTask().execute();
                    new StringTask().execute(et_user_statement.getText().toString());

                    et_user_statement.setText("");


                }
            }
        });



    }

    private  class StringTask extends AsyncTask<String,String,String>{
        String result;
        @Override
        protected String doInBackground(String... strings) {
            Log.e("TAG","USER IBPUT ="+strings[0]);

            IamAuthenticator authenticator = new IamAuthenticator(API_KEY);
            Assistant assistant = new Assistant("2020-04-01", authenticator);
            assistant.setServiceUrl(URL);

            String workspaceId = WORKSPACE_ID;

            MessageInput input = new MessageInput();
            //input.setOriginalText(userinput);
            input.setText(strings[0]);
            Log.e("INPUT","input txt = "+strings[0]);


            MessageOptions options = new MessageOptions.Builder(workspaceId)
                    .input(input)
                    .build();

            MessageResponse response = assistant.message(options).execute().getResult();

            OutputData tree=response.getOutput();
            Log.e("MESSAGETAG","data = "+response.getOutput().getGeneric());


            result= String.valueOf(tree.getText());


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            msgmodel.add(new MessageModel(result,1));
            msgAdapter.notifyDataSetChanged();

            super.onPostExecute(s);
        }
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_user_statement.setText(result.get(0));
                }
                break;
            }

        }

    }
}