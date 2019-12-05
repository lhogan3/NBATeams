package android.projects.nbateams;

//Import necessary libraries
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static android.projects.nbateams.TeamListActivity.dbHandler;

//Defining the EditTeamActivity class, which is where a user can edit an entry
//in the db.
public class EditTeamActivity extends AppCompatActivity {

    //Declaring all of the input TextEdits for the fields for a team.
    EditText teamInput;
    EditText arenaInput;
    EditText champInput;
    EditText player1Input;
    EditText player2Input;
    EditText player3Input;

    //When the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the view and the title for the activity.
        setContentView(R.layout.activity_edit_team);
        setTitle("Edit Team");

        //Set all of the views for the inputs.
        teamInput = findViewById(R.id.teamInput);
        arenaInput = findViewById(R.id.arenaInput);
        champInput = findViewById(R.id.champInput);
        player1Input = findViewById(R.id.player1Input);
        player2Input = findViewById(R.id.player2Input);
        player3Input = findViewById(R.id.player3Input);

        //Get the bundle from the TeamDetailActivity
        Bundle bundle = getIntent().getExtras();

        //Extract the data from the bundle by the key "teamName"
        String teamName = bundle.getString("teamName");

        //Find the team in the db with that namem and then delete that team.
        Team inputTeam = dbHandler.findItemByName(teamName);
        dbHandler.deleteTeam(inputTeam.get_team());

        //Set the edit texts to be the item that was found in the list.
        teamInput.setText(inputTeam.get_team());
        arenaInput.setText(inputTeam.get_arena());
        champInput.setText(inputTeam.get_champ());
        player1Input.setText(inputTeam.get_player1());
        player2Input.setText(inputTeam.get_player2());
        player3Input.setText(inputTeam.get_player3());

        //Defining the submit and clear buttons.
        Button submit = findViewById(R.id.submit);
        Button clear = findViewById(R.id.clear);

        //If the submit button is clicked.
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //If all of the fields are not empty, then create a new team with these new inputs, add it to the db, and
                //then switch back to the TeamListActivity.
                if(validate(new EditText[]{teamInput, arenaInput, champInput, player1Input, player2Input, player3Input})){
                    Team tempTeam = new Team(teamInput.getText().toString(), arenaInput.getText().toString(),
                            champInput.getText().toString(), player1Input.getText().toString(), player2Input.getText().toString(),
                            player3Input.getText().toString());
                    dbHandler.addTeam(tempTeam);

                    Intent intent = new Intent(view.getContext(), TeamListActivity.class);
                    view.getContext().startActivity(intent);
                }

                //Else, display that all of the fields need to be filled out before the submit can complete.
                else{
                    Toast.makeText(getApplicationContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //If the clear button is clicked
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Clear all of the fields so that they have no information in them.
                clearFields(new EditText[]{teamInput, arenaInput, champInput, player1Input, player2Input, player3Input});
            }
        });

    }

    //Defining the validate function.
    private boolean validate(EditText[] fields){

        //Loop through the input of the EditText array
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];

            //If the current field has nothing entered, then
            //return false.
            if(currentField.getText().toString().length() <= 0){
                return false;
            }
        }
        //Otherwise, return true.
        return true;
    }

    //Defining the clearFields function.
    private void clearFields(EditText[] fields){

        //Set all of the fields in the EditText array to have "" (i.e. nothing)
        for (EditText e: fields) {
            e.setText("");
        }
    }
}
