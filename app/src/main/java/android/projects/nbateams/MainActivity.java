package android.projects.nbateams;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.google.android.material.floatingactionbutton.FloatingActionButton;
        import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(getTitle());

        Button list = (Button) findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TeamListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        dbHandler = new DBHandler(this, null, null, 1);
        //printDatabase();
    }

//    //Add a team to the database
//    public void addButtonClicked(){
//        Team team = new Team(teamInput.getText().toString(), arenaInput.getText().toString(), champInput.getText().toString(),
//                player1Input.getText().toString(), player2Input.getText().toString(), player3Input.getText().toString());
//        dbHandler.addTeam(team);
//        //printDatabase();
//    }
//
//    public void deleteButtonClicked(){
//        String teamName = teamInput.getText().toString();
//        dbHandler.deleteTeam(teamName);
//        //printDatabase();
//    }


//    public void printDatabase(){
//        String dbString = dbHandler.databaseToString();
//        displayText.setText(dbString);
////        if(teamInput != null || arenaInput != null || champInput != null || player1Input != null || player2Input != null
////        || player3Input != null){
////            teamInput.setText("");
////            arenaInput.setText("");
////            champInput.setText("");
////            player1Input.setText("");
////            player2Input.setText("");
////            player3Input.setText("");
////        }
//    }
}
