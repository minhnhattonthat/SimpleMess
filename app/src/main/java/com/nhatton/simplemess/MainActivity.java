package com.nhatton.simplemess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.nhatton.simplemess.LoginActivity.SIGN_OUT_COMPLETE;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseUser mUser;
    private FirebaseListAdapter<ChatMessage> mChatAdapter;
    private ListView chatTable;
    private String userID;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = getIntent().getExtras().getString("user_email");
        userID = getIntent().getExtras().getString("user_id");

        chatTable = (ListView) findViewById(R.id.chat_table);

        final EditText messageInput = (EditText) findViewById(R.id.message_input);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        TextView mEmailTextView = (TextView) findViewById(R.id.chat_user_email);
        Button mSignOutButton = (Button) findViewById(R.id.sign_out_button);

        myRef = FirebaseDatabase.getInstance().getReference();

        mChatAdapter = new FirebaseListAdapter<ChatMessage>
                (this,ChatMessage.class,android.R.layout.two_line_list_item, myRef) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                Log.d("ChatMessage",model.getEmail());
                ((TextView) v.findViewById(android.R.id.text1)).setText(model.getEmail());
                ((TextView) v.findViewById(android.R.id.text2)).setText(model.getMessage());
            }
        };
        chatTable.setAdapter(mChatAdapter);

        mEmailTextView.setText(userEmail);

        //Listener for Signout button
        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Listener for Send button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatMessage message = new ChatMessage
                        (userEmail, messageInput.getText().toString(),userID);
                myRef.push().setValue(message);
                messageInput.setText("");
            }
        });
    }

    public void refreshChatScreen(){
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Do you want to sign out?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        setResult(SIGN_OUT_COMPLETE);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChatAdapter.cleanup();
    }
}
