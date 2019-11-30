package android.projects.nbateams;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Team detail screen.
 * This fragment is either contained in a {@link TeamListActivity}
 * in two-pane mode (on tablets) or a {@link TeamDetailActivity}
 * on handsets.
 */
public class TeamDetailFragment extends Fragment {
    /**
     * The dummy content this fragment is presenting.
     */
    private Team mItem;
    private DBHandler dbHandler;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TeamDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = TeamListActivity.dbHandler;
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            if(getArguments().containsKey(TeamListActivity.idString)){
                int id = Integer.valueOf(getArguments().getString(TeamListActivity.idString));
                mItem = dbHandler.findItem(id);
                Activity activity = this.getActivity();
                CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                if (appBarLayout != null) {
                    appBarLayout.setTitle(mItem.get_team());
                }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.team_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.team_detail)).setText(mItem.get_arena());
        }

        return rootView;
    }
}
