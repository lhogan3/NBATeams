package android.projects.nbateams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.projects.nbateams.TeamListActivity.dbHandler;

public class AddTeamActivity extends AppCompatActivity {

    EditText teamInput;
    EditText arenaInput;
    EditText champInput;
    EditText player1Input;
    EditText player2Input;
    EditText player3Input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);
        setTitle("Add New Team");

        //Defining the text inputs for each field.
        teamInput = (EditText) findViewById(R.id.teamInput);
        arenaInput = (EditText) findViewById(R.id.arenaInput);
        champInput = (EditText) findViewById(R.id.champInput);
        player1Input = (EditText) findViewById(R.id.player1Input);
        player2Input = (EditText) findViewById(R.id.player2Input);
        player3Input = (EditText) findViewById(R.id.player3Input);

        //Submit Button
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(new EditText[]{teamInput, arenaInput, champInput, player1Input, player2Input, player3Input})){
                    dbHandler.addTeam(new Team(teamInput.getText().toString(), arenaInput.getText().toString(),
                            champInput.getText().toString(), player1Input.getText().toString(), player2Input.getText().toString(),
                            player3Input.getText().toString()));
                    Intent intent = new Intent(view.getContext(), TeamListActivity.class);
                    view.getContext().startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "lease fill out all fields.", Toast.LENGTH_LONG).show();
                }


            }
        });

        //Clear Inputs Button
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields(new EditText[]{teamInput, arenaInput, champInput, player1Input, player2Input, player3Input});
            }
        });

    }

    private boolean validate(EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().toString().length() <= 0){
                return false;
            }
        }
        return true;
    }

    private void clearFields(EditText[] fields){
        for (EditText e: fields) {
            e.setText("");
        }
    }
}
