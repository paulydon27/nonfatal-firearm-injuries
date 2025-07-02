package com.example.nonfatalfirearminjuries2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FirearmDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "firearm_injuries2.sqbpro";
    private static final int DB_VERSION = 1;

    public FirearmDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS Victims;");
        db.execSQL("DROP TABLE IF EXISTS Injuries;");
        db.execSQL("DROP TABLE IF EXISTS Locations;");

        db.execSQL("CREATE TABLE Locations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "city TEXT NOT NULL," +
                "state TEXT NOT NULL," +
                "latitude REAL," +
                "longitude REAL);");

        db.execSQL("CREATE TABLE Injuries (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date TEXT NOT NULL," +
                "location_id INTEGER NOT NULL," +
                "type TEXT NOT NULL," +
                "severity TEXT NOT NULL," +
                "description TEXT," +
                "FOREIGN KEY (location_id) REFERENCES Locations(id));");

        db.execSQL("CREATE TABLE Victims (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "age INTEGER," +
                "gender TEXT," +
                "injury_id INTEGER NOT NULL," +
                "FOREIGN KEY (injury_id) REFERENCES Injuries(id));");

        // Insert data into Locations
        db.execSQL("INSERT INTO Locations (city, state, latitude, longitude) VALUES " +
                "('New York', 'NY', 40.7128, -74.0060)," +
                "('Los Angeles', 'CA', 34.0522, -118.2437)," +
                "('Chicago', 'IL', 41.8781, -87.6298)," +
                "('Houston', 'TX', 29.7604, -95.3698)," +
                "('Miami', 'FL', 25.7617, -80.1918)," +
                "('Philadelphia', 'PA', 39.9526, -75.1652)," +
                "('Phoenix', 'AZ', 33.4484, -112.0740)," +
                "('San Antonio', 'TX', 29.4241, -98.4936)," +
                "('San Diego', 'CA', 32.7157, -117.1611)," +
                "('Dallas', 'TX', 32.7767, -96.7970)," +
                "('Atlanta', 'GA', 33.7490, -84.3880)," +
                "('Denver', 'CO', 39.7392, -104.9903)," +
                "('Seattle', 'WA', 47.6062, -122.3321)," +
                "('Las Vegas', 'NV', 36.1699, -115.1398)," +
                "('Detroit', 'MI', 42.3314, -83.0458)," +
                "('Baltimore', 'MD', 39.2904, -76.6122)," +
                "('St. Louis', 'MO', 38.6270, -90.1994)," +
                "('Portland', 'OR', 45.5051, -122.6750)," +
                "('Nashville', 'TN', 36.1627, -86.7816)," +
                "('New Orleans', 'LA', 29.9511, -90.0715);");

        // Insert data into Injuries
        db.execSQL("INSERT INTO Injuries (date, location_id, type, severity, description) VALUES " +
                "('2024-06-15', 1, 'Gunshot', 'Minor', 'Gunshot wound in New York.')," +
                "('2024-07-02', 2, 'Gunshot', 'Severe', 'Gunshot wound in Los Angeles.')," +
                "('2024-05-20', 3, 'Gunshot', 'Moderate', 'Gunshot wound in Chicago.')," +
                "('2024-06-22', 4, 'Gunshot', 'Fatal', 'Gunshot wound in Houston.')," +
                "('2024-04-18', 5, 'Gunshot', 'Minor', 'Gunshot wound in Miami.')," +
                "('2024-07-10', 6, 'Gunshot', 'Severe', 'Gunshot wound in Philadelphia.')," +
                "('2024-03-11', 7, 'Gunshot', 'Moderate', 'Gunshot wound in Phoenix.')," +
                "('2024-06-01', 8, 'Gunshot', 'Severe', 'Gunshot wound in San Antonio.')," +
                "('2024-05-27', 9, 'Gunshot', 'Minor', 'Gunshot wound in San Diego.')," +
                "('2024-04-05', 10, 'Gunshot', 'Fatal', 'Gunshot wound in Dallas.')," +
                "('2024-07-14', 11, 'Gunshot', 'Moderate', 'Gunshot wound in Atlanta.')," +
                "('2024-06-28', 12, 'Gunshot', 'Severe', 'Gunshot wound in Denver.')," +
                "('2024-05-15', 13, 'Gunshot', 'Fatal', 'Gunshot wound in Seattle.')," +
                "('2024-04-23', 14, 'Gunshot', 'Minor', 'Gunshot wound in Las Vegas.')," +
                "('2024-06-30', 15, 'Gunshot', 'Severe', 'Gunshot wound in Detroit.')," +
                "('2024-07-05', 16, 'Gunshot', 'Fatal', 'Gunshot wound in Baltimore.')," +
                "('2024-05-09', 17, 'Gunshot', 'Moderate', 'Gunshot wound in St. Louis.')," +
                "('2024-06-18', 18, 'Gunshot', 'Severe', 'Gunshot wound in Portland.')," +
                "('2024-03-30', 19, 'Gunshot', 'Fatal', 'Gunshot wound in Nashville.')," +
                "('2024-07-08', 20, 'Gunshot', 'Moderate', 'Gunshot wound in New Orleans.');");

        // Insert data into Victims
        db.execSQL("INSERT INTO Victims (age, gender, injury_id) VALUES " +
                "(25, 'Male', 1)," +
                "(30, 'Female', 2)," +
                "(19, 'Male', 3)," +
                "(40, 'Female', 4)," +
                "(22, 'Male', 5)," +
                "(35, 'Female', 6)," +
                "(28, 'Male', 7)," +
                "(31, 'Female', 8)," +
                "(23, 'Male', 9)," +
                "(37, 'Female', 10)," +
                "(20, 'Male', 11)," +
                "(33, 'Female', 12)," +
                "(29, 'Male', 13)," +
                "(26, 'Female', 14)," +
                "(21, 'Male', 15)," +
                "(38, 'Female', 16)," +
                "(24, 'Male', 17)," +
                "(27, 'Female', 18)," +
                "(32, 'Male', 19)," +
                "(36, 'Female', 20);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);  // Simplified for development: drop and recreate all tables
    }
}





