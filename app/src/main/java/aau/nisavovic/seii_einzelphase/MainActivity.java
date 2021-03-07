package aau.nisavovic.seii_einzelphase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // create the UI elements
    Button send;
    TextView serverResponse;
    EditText userInput; //textfeld

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect UI elements to XML-elements by referencing ID
        send = findViewById(R.id.send);
        serverResponse = findViewById(R.id.serverResponse);
        userInput = findViewById(R.id.userInput);
    }

    // view is not used here
    public void sendButtonClicked(View v) throws InterruptedException {
        String matrikelnummer = this.userInput.getText().toString();

        Thread networkThread = new Server(matrikelnummer);
        // thread needed so the app does not crash when sending message to the server
        networkThread.start();

        // wait for the thread to complete so we can read the result
        networkThread.join();

        // set the response from the server in the user interface; need to typecast because we initialized
        // it as a thread but need to access a "Server"-method
        this.serverResponse.setText(((Server)networkThread).getResult());
    }
}