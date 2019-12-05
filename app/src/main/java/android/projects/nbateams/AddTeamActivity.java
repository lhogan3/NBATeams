package android.projects.nbateams;

//Importing necessary libraries.
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static android.projects.nbateams.TeamListActivity.dbHandler;


//Class for adding a team to the DB, and then adding it to the list to be displayed.
public class AddTeamActivity extends AppCompatActivity {

    //Defining all of the fields to be used for entering text input.
    EditText teamInput;
    EditText arenaInput;
    EditText champInput;
    EditText player1Input;
    EditText player2Input;
    EditText player3Input;

    //Creating the view.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Seeting the view and the title for the activity.
        setContentView(R.layout.activity_add_team);
        setTitle("Add New Team");

        //Defining the text inputs for each field.
        teamInput = findViewById(R.id.teamInput);
        arenaInput = findViewById(R.id.arenaInput);
        champInput = findViewById(R.id.champInput);
        player1Input = findViewById(R.id.player1Input);
        player2Input = findViewById(R.id.player2Input);
        player3Input = findViewById(R.id.player3Input);

        //Submit Button
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If none of the fields are empty, add the new team with these inputs to the db and then move back to the list.
                if(validate(new EditText[]{teamInput, arenaInput, champInput, player1Input, player2Input, player3Input})){
                    dbHandler.addTeam(new Team(teamInput.getText().toString(), arenaInput.getText().toString(),
                            champInput.getText().toString(), player1Input.getText().toString(), player2Input.getText().toString(),
                            player3Input.getText().toString()));
                    Intent intent = new Intent(view.getContext(), TeamListActivity.class);
                    view.getContext().startActivity(intent);
                }
                //Else, display a message that says that you need to fill out a new name of a team.
                else{
                    Toast.makeText(getApplicationContext(), "Please fill out all fields, or enter a new team.", Toast.LENGTH_LONG).show();
                }


            }
        });

        //Clear Inputs Button. If clicked, then set all of the EditTexts to be empty.
        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields(new EditText[]{teamInput, arenaInput, champInput, player1Input, player2Input, player3Input});
            }
        });

    }

    //Takes in an array of EditTexts, and then checks if any of them are empty. If there are, then return false. Else, return true.
    private boolean validate(EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().toString().length() <= 0){
                return false;
            }
        }
        return true;
    }

    //Takes in an array of EditTexts and sets all of them to be "" which is the empty string.
    private void clearFields(EditText[] fields){
        for (EditText e: fields) {
            e.setText("");
        }
    }
}
