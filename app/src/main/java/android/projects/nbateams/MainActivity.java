package android.projects.nbateams;

//Import the necessary libraries
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Defining the MainActivity Class
public class MainActivity extends AppCompatActivity {

    //Creating the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the view
        setContentView(R.layout.activity_main);

        //If the list button is selected, then move to the list view.
        Button list = findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TeamListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        Button teams = findViewById(R.id.teams);
        teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TeamsActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
}
