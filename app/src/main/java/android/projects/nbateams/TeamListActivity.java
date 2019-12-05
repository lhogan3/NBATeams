package android.projects.nbateams;

//Import necessary libraries.
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

//Defining the TeamListActivity class
public class TeamListActivity extends AppCompatActivity {

    //Defining the fields
    private boolean mTwoPane;
    public static DBHandler dbHandler;
    public static List<Team> teams;
    public static final String idString = "itemID";
    public static boolean makeNewDb = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the view
        setContentView(R.layout.activity_team_list);

        //Creating the database instantiation if one has not already been created
        if(makeNewDb){
            dbHandler = new DBHandler(this, null, null, 1);
            makeNewDb = false;
        }

        //Create the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        //Defining the addTeam Floating Action Button
        FloatingActionButton add = findViewById(R.id.delete);

        //If add is clicked
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Move to the AddTeamActivity
                Intent intent = new Intent(view.getContext(), AddTeamActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        //If the app is being run on a tablet, then being put in two pane mode.
        if (findViewById(R.id.team_detail_container) != null) {
            mTwoPane = true;
        }

        //Creating the recycler view, and then setting it up.
        View recyclerView = findViewById(R.id.team_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    //Setting up the recycler view
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //Setting the adapted to be a new SimpleItemRecyclerAdapter with the contents of the db.
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, dbHandler.getDatabaseList(), mTwoPane));
    }

    //Defining the SimpleItemRecyclerViewAdapter class
    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        //Fields of the class
        private final TeamListActivity mParentActivity;
        private final List<Team> mValues;
        private final boolean mTwoPane;

        //If an item in the list is being clicked.
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create a team by the view being clicked.
                Team item = (Team) view.getTag();

                //If on a tablet
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(idString, Integer.toString(item.get_id()));
                    TeamDetailFragment fragment = new TeamDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.team_detail_container, fragment)
                            .commit();
                }
                //if not on a tablet
                else {
                    //Create a new intent to switch to the TeamDetailActivity class
                    //and then pass in with this new intent switch the id of the team
                    //that was selected in the list.
                    Context context = view.getContext();
                    Intent intent = new Intent(context, TeamDetailActivity.class);
                    intent.putExtra(idString, Integer.toString(item.get_id()));
                    context.startActivity(intent);
                }
            }
        };

        //Constructor
        SimpleItemRecyclerViewAdapter(TeamListActivity parent,
                                      List<Team> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        //Creating a ViewHolder with the View being passed in.
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.team_list_content, parent, false);
            return new ViewHolder(view);
        }

        //Setting the view for the holder.
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(Integer.toString(mValues.get(position).get_id()));
            holder.mContentView.setText(mValues.get(position).get_team());
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        //Getting the number of items in the list.
        @Override
        public int getItemCount() {
            return mValues.size();
        }

        //Defining the View Holder class
        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            //Constructor with the view being passed in.
            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                mContentView = view.findViewById(R.id.content);
            }
        }
    }
}
