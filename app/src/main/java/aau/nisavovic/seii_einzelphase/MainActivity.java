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
    // this method is executed when the "Absenden"-button is clicked
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

    // this method is executed when the "Berechnung"-button is clicked
    public void calculateButtonClicked(View v ) {
        String matrikelnummer = this.userInput.getText().toString();
        String result = "";

        // assumption: if a digit is zero (0), it gets replaced with a 'z' because there is no
        // specification in the task description
        char[] characters = {'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};

        // iterate over all characters and substitute every 2nd digit with a character
        for(int i = 0; i < matrikelnummer.length(); i++) {
            // digit gets replaced with character
            if ((i+1)%2 == 0) {
                char currentChar = matrikelnummer.charAt(i); // get character at index i from string
                String currentCharAsString = String.valueOf(currentChar); // convert the character to string
                int digitAsNumber = Integer.parseInt(currentCharAsString); // convert string to int
                char newChar = characters[digitAsNumber]; // get the right character for this number

                result = result + String.valueOf(newChar); // convert char to string back again to add together
            }
            // digit remains the same
            else {
                String currentChar = String.valueOf(matrikelnummer.charAt(i));
                result = result + currentChar;
            }
            }

        this.serverResponse.setText(result);
    }
}