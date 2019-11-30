package android.projects.nbateams;

public class Team {
    private int _id;
    private String _team;
    private String _arena;
    private String _champ;
    private String _player1;
    private String _player2;
    private String _player3;

    public Team() {
    }

    //For adding teams to the DB.
    public Team(String _team, String _arena, String _champ, String _player1, String _player2, String _player3) {
        this._team = _team;
        this._arena = _arena;
        this._champ = _champ;
        this._player1 = _player1;
        this._player2 = _player2;
        this._player3 = _player3;
    }

    //For creating the list of team objects to be displayed.

    public Team(int _id, String _team, String _arena, String _champ, String _player1, String _player2, String _player3) {
        this._id = _id;
        this._team = _team;
        this._arena = _arena;
        this._champ = _champ;
        this._player1 = _player1;
        this._player2 = _player2;
        this._player3 = _player3;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_team(String _team) {
        this._team = _team;
    }

    public void set_arena(String _arena) {
        this._arena = _arena;
    }

    public void set_champ(String _champ) {
        this._champ = _champ;
    }

    public void set_player1(String _player1) {
        this._player1 = _player1;
    }

    public void set_player2(String _player2) {
        this._player2 = _player2;
    }

    public void set_player3(String _player3) {
        this._player3 = _player3;
    }

    public int get_id() {
        return _id;
    }

    public String get_team() {
        return _team;
    }

    public String get_arena() {
        return _arena;
    }

    public String get_champ() {
        return _champ;
    }

    public String get_player1() {
        return _player1;
    }

    public String get_player2() {
        return _player2;
    }

    public String get_player3() {
        return _player3;
    }
}
