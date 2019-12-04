package android.projects.nbateams;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.Toolbar;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;
import android.widget.EditText;

import static android.projects.nbateams.TeamListActivity.dbHandler;

/**
 * An activity representing a single Team detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TeamListActivity}.
 */
public class TeamDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle editBundle = new Bundle();
                editBundle.putString("teamName", dbHandler.findItemByID(Integer.valueOf(getIntent().getStringExtra(TeamListActivity.idString))).get_team());
                Intent intent = new Intent(view.getContext(), EditTeamActivity.class);
                intent.putExtras(editBundle);
                startActivity(intent);
            }
        });
        FloatingActionButton delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.deleteTeam(dbHandler.findItemByID(Integer.valueOf(getIntent().getStringExtra(TeamListActivity.idString))).get_team());
                Intent intent = new Intent(view.getContext(), TeamListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, TeamListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
