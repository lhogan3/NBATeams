package android.projects.nbateams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.projects.nbateams.TeamListActivity.dbHandler;

public class EditTeamActivity extends AppCompatActivity {

    EditText teamInput;
    EditText arenaInput;
    EditText champInput;
    EditText player1Input;
    EditText player2Input;
    EditText player3Input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        teamInput = (EditText) findViewById(R.id.teamInput);
        arenaInput = (EditText) findViewById(R.id.arenaInput);
        champInput = (EditText) findViewById(R.id.champInput);
        player1Input = (EditText) findViewById(R.id.player1Input);
        player2Input = (EditText) findViewById(R.id.player2Input);
        player3Input = (EditText) findViewById(R.id.player3Input);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String teamName = bundle.getString("teamName");
        Team inputTeam = dbHandler.findItemByName(teamName);
        dbHandler.deleteTeam(inputTeam.get_team());

        teamInput.setText(inputTeam.get_team());
        arenaInput.setText(inputTeam.get_arena());
        champInput.setText(inputTeam.get_champ());
        player1Input.setText(inputTeam.get_player1());
        player2Input.setText(inputTeam.get_player2());
        player3Input.setText(inputTeam.get_player3());

        Button submit = findViewById(R.id.submit);
        Button clear = findViewById(R.id.clear);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(new EditText[]{teamInput, arenaInput, champInput, player1Input, player2Input, player3Input})){
                    Team tempTeam = new Team(teamInput.getText().toString(), arenaInput.getText().toString(),
                            champInput.getText().toString(), player1Input.getText().toString(), player2Input.getText().toString(),
                            player3Input.getText().toString());
                    dbHandler.addTeam(tempTeam);
                    //Team tempTeam = dbHandler.findItemByName(teamInput.getText().toString());
                    //Context context = view.getContext();
                    Intent intent = new Intent(view.getContext(), TeamListActivity.class);
                    //intent.putExtra("teamID", Integer.toString(tempTeam.get_id()));
                    view.getContext().startActivity(intent);
                }

                else{
                    Toast.makeText(getApplicationContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

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
