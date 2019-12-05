package android.projects.nbateams;

//Import the necessary libraries.
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

//Defining the TeamsActivity Class
public class TeamsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the view, and create the tool bar
        setContentView(R.layout.activity_teams);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Define the back button
        Button back = findViewById(R.id.back);

        //If the back button is clicked.
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Switch back to the MainActivity
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

}
