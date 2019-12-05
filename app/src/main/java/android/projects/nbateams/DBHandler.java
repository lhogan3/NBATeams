package android.projects.nbateams;

//Import necessary libraries.
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

//Defining the DBHandler class, which contains all of the interactions with the
//SQLite database used in the app
public class DBHandler extends SQLiteOpenHelper {

    //Define the version, name, and table name for the database.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "teams.db";
    private static final String TABLE_TEAMS = "teams";

    //Define the column names to be used in the table.
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TEAM = "_team";
    private static final String COLUMN_ARENA = "_arena";
    private static final String COLUMN_CHAMP = "_champ";
    private static final String COLUMN_PLAYER1 = "_player1";
    private static final String COLUMN_PLAYER2 = "_player2";
    private static final String COLUMN_PLAYER3 = "_player3";

    //Override the DBHandler constructor, so can extend the SQLiteOpenHelper. This creates the DB
    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //This creates the table in the database that has been passed as a parameter.
    //Also a required override.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_TEAMS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEAM + " TEXT," +
                COLUMN_ARENA + " TEXT, " +
                COLUMN_CHAMP + " TEXT, " +
                COLUMN_PLAYER1 + " TEXT, " +
                COLUMN_PLAYER2 + " TEXT, " +
                COLUMN_PLAYER3 + " TEXT " +
                ");";
        db.execSQL(query);
    }

    //If the DB is going to be upgraded, this function is run where the table is deleted and then a new
    //db is made.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        onCreate(db);
    }

    //Add a new row (team) to the database.
    public void addTeam(Team team){
        //Create all of the values to be added to the row.
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEAM, team.get_team());
        values.put(COLUMN_ARENA, team.get_arena());
        values.put(COLUMN_CHAMP, team.get_champ());
        values.put(COLUMN_PLAYER1, team.get_player1());
        values.put(COLUMN_PLAYER2, team.get_player2());
        values.put(COLUMN_PLAYER3, team.get_player3());

        //Get a writable db and then insert the values there.
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TEAMS, null, values);

        //Close the db
        db.close();
    }

    //Delete a row (team) from the database by the team name.
    public void deleteTeam(String team){
        //Open a db for writing.
        SQLiteDatabase db = getWritableDatabase();

        //Execute a SQL query with the input team name and delete the row if it exists.
        db.execSQL("DELETE FROM " + TABLE_TEAMS + " WHERE " + COLUMN_TEAM + "=\"" + team + "\";" );

        //Close the db
        db.close();
    }

    //How to retrieve all of the teams in the db as an List of Team objects.
    public List<Team> getDatabaseList(){
        //Initialize the List of teams, and then open a db.
        ArrayList<Team> teams = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        //Create a query string.
        String query = "SELECT * FROM " + TABLE_TEAMS + " WHERE 1";
        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row of the results
        c.moveToFirst();

        //Loop through the entire db and create a team object based on the fields stored there.
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_TEAM)) != null){
                teams.add(new Team(Integer.valueOf(c.getString(c.getColumnIndex(COLUMN_ID))), c.getString(c.getColumnIndex(COLUMN_TEAM)), c.getString(c.getColumnIndex(COLUMN_ARENA)),
                        c.getString(c.getColumnIndex(COLUMN_CHAMP)), c.getString(c.getColumnIndex(COLUMN_PLAYER1)), c.getString(c.getColumnIndex(COLUMN_PLAYER2)),
                        c.getString(c.getColumnIndex(COLUMN_PLAYER3))));
            }
            c.moveToNext();
        }

        //Close the db
        db.close();

        //return the full list of teams in the db.
        return teams;
    }

    //Find an item by an id.
    public Team findItemByID(int id){
        //Change the id to a string, and then declare a team with the default constructor
        String idString = Integer.toString(id);
        Team teamToReturn = new Team();

        //Open a db for reading, and then create the query string.
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TEAMS + " WHERE " + COLUMN_ID + "=\"" + idString + "\";";

        //Create a cursor and then move it to the first thing in the db.
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        //Loop through db, and if am entry in the db has a matching id, then set the rest of the
        //parameters for the team from the other columns entries for that row in the db
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_ID)).equals(idString)){
                teamToReturn.set_id(Integer.valueOf(c.getString(c.getColumnIndex(COLUMN_ID))));
                teamToReturn.set_team(c.getString(c.getColumnIndex(COLUMN_TEAM)));
                teamToReturn.set_arena(c.getString(c.getColumnIndex(COLUMN_ARENA)));
                teamToReturn.set_champ(c.getString(c.getColumnIndex(COLUMN_CHAMP)));
                teamToReturn.set_player1(c.getString(c.getColumnIndex(COLUMN_PLAYER1)));
                teamToReturn.set_player2(c.getString(c.getColumnIndex(COLUMN_PLAYER2)));
                teamToReturn.set_player3(c.getString(c.getColumnIndex(COLUMN_PLAYER3)));
            }
            c.moveToNext();
        }

        //Close the db.
        db.close();

        //Return the team created.
        return teamToReturn;
    }

    //Find an item by Team Name
    public Team findItemByName(String name){
        //Declare a team with the default constructor.
        Team teamToReturn = new Team();

        //Open a db for reading, and create the query string.
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TEAMS + " WHERE " + COLUMN_TEAM + "=\"" + name + "\";";

        //Create a cursor and then move it to the beginning of the db
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        //Loop through the db, and if a row's team name matches the one being searched for, set the Team objects
        //other fields to be the other entries in that row in the db
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_TEAM)).equals(name)){
                teamToReturn.set_id(Integer.valueOf(c.getString(c.getColumnIndex(COLUMN_ID))));
                teamToReturn.set_team(c.getString(c.getColumnIndex(COLUMN_TEAM)));
                teamToReturn.set_arena(c.getString(c.getColumnIndex(COLUMN_ARENA)));
                teamToReturn.set_champ(c.getString(c.getColumnIndex(COLUMN_CHAMP)));
                teamToReturn.set_player1(c.getString(c.getColumnIndex(COLUMN_PLAYER1)));
                teamToReturn.set_player2(c.getString(c.getColumnIndex(COLUMN_PLAYER2)));
                teamToReturn.set_player3(c.getString(c.getColumnIndex(COLUMN_PLAYER3)));
            }
            c.moveToNext();
        }

        //Close the db
        db.close();

        //Return the team created.
        return teamToReturn;
    }
}