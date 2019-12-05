package android.projects.nbateams;

//Import necessary libraries
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import android.view.MenuItem;
import static android.projects.nbateams.TeamListActivity.dbHandler;

//Defining the TeamDetailActivity class.
public class TeamDetailActivity extends AppCompatActivity {

    //Creating an instance of this activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the view, and define the toolbar.
        setContentView(R.layout.activity_team_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        //Define the edit Floating Action Button
        FloatingActionButton edit = findViewById(R.id.edit);

        //If edit is clicked.
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create a bundle
                Bundle editBundle = new Bundle();

                //Put the name of the team after finding it by its id in the db in the bundle. Use the key "teamName" to access
                //in the next activity.
                editBundle.putString("teamName", dbHandler.findItemByID(Integer.valueOf(getIntent().getStringExtra(TeamListActivity.idString))).get_team());

                //Then, create a new intent to switch to the new activity, add the bundle, and then start the new activity.
                Intent intent = new Intent(view.getContext(), EditTeamActivity.class);
                intent.putExtras(editBundle);
                startActivity(intent);
            }
        });

        //Defining the delete button.
        FloatingActionButton delete = findViewById(R.id.delete);

        //If delete is clicked
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Delete the team after finding it in the db by it's id.
                dbHandler.deleteTeam(dbHandler.findItemByID(Integer.valueOf(getIntent().getStringExtra(TeamListActivity.idString))).get_team());

                //Create a new intent and switch back to the TeamListActivity.
                Intent intent = new Intent(view.getContext(), TeamListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(TeamListActivity.idString,
                    getIntent().getStringExtra(TeamListActivity.idString));
            TeamDetailFragment fragment = new TeamDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.team_detail_container, fragment)
                    .commit();
        }
    }

    //If the item is selected, then return it's id.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, TeamListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
