package android.projects.nbateams;

//Import necessary libraries.
import android.app.Activity;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import static android.projects.nbateams.TeamListActivity.dbHandler;

//Defining the TeamDetailFragment
public class TeamDetailFragment extends Fragment {

    //The team being displayed in the fragment
    private Team mItem;

    //Required default fragment
    public TeamDetailFragment() {
    }

    //When the fragment is created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //If the arguments passed in the bundle from the TeamDetailActivity match the
        //idString from the TeamListActivity
        if(getArguments().containsKey(TeamListActivity.idString)){

            //Get the id from the bundle
            int id = Integer.valueOf(getArguments().getString(TeamListActivity.idString));

            //Set the mitem to be the team stored in the db by the id.
            mItem = dbHandler.findItemByID(id);

            //Set the activity to be this fragement.
            Activity activity = this.getActivity();

            //Create the Collapsing Toolbar Layout
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.get_team());
            }
        }
    }

    //When creating the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Set the view
        View rootView = inflater.inflate(R.layout.team_detail, container, false);

        //Set the text of the fragment to be from the mItem just set from the db.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.team_detail)).setText(createText(mItem));
        }
        return rootView;
    }

    //Get string to be displayed on the page.
    public String createText(Team team){
        String text = "\nArena: " + team.get_arena() + "\n\nNumber of Championships: " + team.get_champ() +
                "\n\nTop Players:\n\n\t\t\t1.) " + team.get_player1() + "\n\t\t\t2.) " + team.get_player2() + "\n\t\t\t3.) " +
                team.get_player3();
        return text;
    }
}
