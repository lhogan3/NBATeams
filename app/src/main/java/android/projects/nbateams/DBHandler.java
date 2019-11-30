package android.projects.nbateams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.drm.DrmStore;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "teams.db";
    private static final String TABLE_TEAMS = "teams";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TEAM = "_team";
    private static final String COLUMN_ARENA = "_arena";
    private static final String COLUMN_CHAMP = "_champ";
    private static final String COLUMN_PLAYER1 = "_player1";
    private static final String COLUMN_PLAYER2 = "_player2";
    private static final String COLUMN_PLAYER3 = "_player3";

    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        onCreate(db);
    }

    //Add a new row (team) to the database.
    public void addTeam(Team team){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEAM, team.get_team());
        values.put(COLUMN_ARENA, team.get_arena());
        values.put(COLUMN_CHAMP, team.get_champ());
        values.put(COLUMN_PLAYER1, team.get_player1());
        values.put(COLUMN_PLAYER2, team.get_player2());
        values.put(COLUMN_PLAYER3, team.get_player3());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TEAMS, null, values);
        db.close();
    }

    //Delete a row (team) from the database.
    public void deleteTeam(String team){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TEAMS + " WHERE " + COLUMN_TEAM + "=\"" + team + "\";" );
        db.close();
    }

    //Print out the DB as a String
    public List<Team> getDatabaseList(){
        ArrayList<Team> teams = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
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
        db.close();
        return teams;
    }

    public Team findItem(int id){
        String idString = Integer.toString(id);
        Team teamToReturn = new Team();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TEAMS + " WHERE " + COLUMN_ID + "=\"" + idString + "\";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

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
        db.close();
        return teamToReturn;
    }
}
