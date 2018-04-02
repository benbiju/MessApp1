package android.messapp.com.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView text1;

    private static final String FORMAT = "%02d:%02d:%02d";

    int seconds , minutes;
    boolean booked=false;

    final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private String roll,messGot;
    private final String Name = "StdName";

    private final String Dues = "Dues";
    private final String Rollno = "RollNo";
    private final String Mess = "BookedMess";
    private final String Rate="Rate";
    private final String Seats="NoOfSeats";
    private final String messes="messes";
    private final String Breakfast = "Breakfast";
    private final String Lunch = "Lunch";
    private final String Dinner = "Dinner";
    private StringBuilder name = new StringBuilder("");
    int dues,seats,ratenow;
    private StringBuilder duesD=new StringBuilder("");
    private String givMess=new String("");
    private String givRoll=new String("");
    private String givRate=new String("");
    private String givSeats=new String("");

    private StringBuilder rollnum = new StringBuilder("");
    private StringBuilder mess = new StringBuilder("");
    private StringBuilder breakfast = new StringBuilder("");
    private StringBuilder lunch = new StringBuilder("");
    private StringBuilder dinner = new StringBuilder("");
    private StringBuilder rate = new StringBuilder("");
   private StringBuilder seat=new StringBuilder("");
    private final String students = "students";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


       SharedPreferences sharedPref1= getSharedPreferences("mypref", 0);
        booked=sharedPref1.getBoolean("booked",false);
        if (booked == true) {
            Button Button7 = (Button) findViewById(R.id.button6);
            Button7.setVisibility(View.GONE);
        }

        //Bundle bundle = getIntent().getExtras();
        //roll= bundle.getString("RollNo");
        roll="B140571CS";


        myRef.addValueEventListener(new ValueEventListener() {  //Function runs whenver there's a change in the database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Toast.makeText(getApplicationContext(), "Successfully Booked", Toast.LENGTH_LONG).show();
                DataSnapshot studentDS = dataSnapshot.child(students).child(roll);
                name = new StringBuilder(studentDS.child(Name).getValue().toString());
                mess = new StringBuilder(studentDS.child(Mess).getValue().toString());
                rollnum = new StringBuilder(studentDS.child(Rollno).getValue().toString());
                duesD =new StringBuilder(studentDS.child(Dues).getValue().toString());

                TextView text1 = (TextView) findViewById(R.id.NavName);
                text1.setText("");
                text1.setText(name.toString());
                TextView text2 = (TextView) findViewById(R.id.NavRollno);
                text2.setText("");
                text2.setText(rollnum.toString());
                Long L = (Long) studentDS.child(Dues).getValue();
                dues = L.intValue();



                Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
                givMess=String.valueOf(spinner1.getSelectedItem());
               DataSnapshot messDS = dataSnapshot.child(messes).child(givMess.toString());
                breakfast = new StringBuilder(messDS.child(Breakfast).getValue().toString());
                lunch = new StringBuilder(messDS.child(Lunch).getValue().toString());
                dinner = new StringBuilder(messDS.child(Dinner).getValue().toString());
                rate = new StringBuilder(messDS.child(Rate).getValue().toString());
               seat = new StringBuilder(messDS.child(Seats).getValue().toString());
                fillContent();
            }

            @Override
            public void onCancelled(DatabaseError error) { //auto-generated
                // Failed to read value
            }
        });



        Button Button4 = (Button) findViewById(R.id.button4);

        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(true)
                  //  Toast.makeText(getApplicationContext(), "Go to Menu", Toast.LENGTH_LONG).show();//display the text of button1
                myRef.addValueEventListener(new ValueEventListener() {  //Function runs whenver there's a change in the database
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
                        givMess=String.valueOf(spinner1.getSelectedItem());
                        if(givMess.equals("Select Mess"))
                        {
                            Toast.makeText(getApplicationContext(),"Please select a mess",Toast.LENGTH_SHORT).show();
                            TextView text1 = (TextView) findViewById(R.id.breakfast);
                            text1.setVisibility(View.GONE);
                            TextView text2 = (TextView) findViewById(R.id.lunch);
                            text2.setVisibility(View.GONE);
                            TextView text3 = (TextView) findViewById(R.id.dinner);
                            text3.setVisibility(View.GONE);
                            TextView text4 = (TextView) findViewById(R.id.rate);
                            text4.setVisibility(View.GONE);
                            TextView text5 = (TextView) findViewById(R.id.seats);
                            text5.setVisibility(View.GONE);
                        }
                        else {
                            // EditText etext = (EditText) findViewById(R.id.messName);
                            // givMess = etext.getText().toString();
                            DataSnapshot messDS = dataSnapshot.child(messes).child(givMess.toString());
                            breakfast = new StringBuilder(messDS.child(Breakfast).getValue().toString());
                            lunch = new StringBuilder(messDS.child(Lunch).getValue().toString());
                            dinner = new StringBuilder(messDS.child(Dinner).getValue().toString());
                            rate = new StringBuilder(messDS.child(Rate).getValue().toString());
                            seat = new StringBuilder(messDS.child(Seats).getValue().toString());




                        fillContent();}
                    }

                    @Override
                    public void onCancelled(DatabaseError error) { //auto-generated
                        // Failed to read value
                    }
                });



                TextView text1 = (TextView) findViewById(R.id.breakfast);
                text1.setVisibility(View.VISIBLE);
                text1.setText("");
                text1.setText("BreakFast: "+breakfast.toString());
                TextView text2 = (TextView) findViewById(R.id.lunch);
                text2.setVisibility(View.VISIBLE);
                text2.setText("");
                text2.setText("Lunch: "+lunch.toString());
                TextView text3 = (TextView) findViewById(R.id.dinner);
                text3.setVisibility(View.VISIBLE);
                text3.setText("");
                text3.setText("Dinner: "+dinner.toString());
                TextView text4 = (TextView) findViewById(R.id.rate);
                text4.setVisibility(View.VISIBLE);
                text4.setText("");
                text4.setText("Rate: "+rate.toString());
                TextView text5 = (TextView) findViewById(R.id.seats);
                text5.setVisibility(View.VISIBLE);
                text5.setText("");
                text5.setText("Seats Left: "+seat.toString());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TextView text1 = (TextView) findViewById(R.id.breakfast);
                        text1.setVisibility(View.GONE);
                        TextView text2 = (TextView) findViewById(R.id.lunch);
                        text2.setVisibility(View.GONE);
                        TextView text3 = (TextView) findViewById(R.id.dinner);
                        text3.setVisibility(View.GONE);
                        TextView text4 = (TextView) findViewById(R.id.rate);
                        text4.setVisibility(View.GONE);
                        TextView text5 = (TextView) findViewById(R.id.seats);
                        text5.setVisibility(View.GONE);

                    }
                }, 10000);
            }
        });

        Button Button5 = (Button) findViewById(R.id.button5);

        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) findViewById(R.id.dues);
                text.setVisibility(View.VISIBLE);
                text.setText("");
                text.setText("Dues: "+duesD.toString());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TextView text = (TextView) findViewById(R.id.dues);
                        text.setVisibility(View.GONE);
                    }
                }, 10000);

            }
        });
        Button Button6 = (Button) findViewById(R.id.button6);

        Button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
                givMess=String.valueOf(spinner1.getSelectedItem());
                if(givMess.equals("Select Mess"))
                {
                    Toast.makeText(getApplicationContext(),"Please select a mess",Toast.LENGTH_SHORT).show();
                }
                else {

                    TextView text = (TextView) findViewById(R.id.rollno);
                    givRoll = text.getText().toString();
                    TextView text1 = (TextView) findViewById(R.id.rate);
                    givRate = text1.getText().toString();
                    String s = givRate.substring(6);
                    ratenow = Integer.parseInt(s);
                    TextView text2 = (TextView) findViewById(R.id.seats);
                    givSeats = text2.getText().toString();
                    String s1 = givSeats.substring(12);
                    seats = Integer.parseInt(s1);


                    //Toast.makeText(getApplicationContext(),seats,Toast.LENGTH_SHORT).show();
                    if (seats > 0) {
                        seats = seats - 1;

                        dues = dues + ratenow;

                        myRef.child(messes).child(givMess).child(Seats).setValue(seats);
                        myRef.child(students).child(givRoll).child(Dues).setValue(dues);
                        myRef.child(students).child(givRoll).child(Mess).setValue(givMess);
                        myRef.child(messes).child(givMess).child("listOfStudents").push().setValue(givRoll);
                        Toast.makeText(getApplicationContext(), "Successfully Booked", Toast.LENGTH_LONG).show();

                        SharedPreferences sharedPref = getSharedPreferences("mypref", 0);
                        //now get Editor
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("booked", true);
                        //commits your edits
                        editor.commit();
                        Button Button6 = (Button) findViewById(R.id.button6);
                        Button6.setVisibility(View.GONE);
                        text1 = (TextView) findViewById(R.id.text1);
                        text1.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getApplicationContext(), "NO SEATS AVAILABLE IN THIS MESS. TRY ANOTHER MESS:)", Toast.LENGTH_SHORT).show();
                    }


                }
            }

        });
        //save instance





    //timer


        long timeCheck= System.currentTimeMillis();
        Calendar calendarCheck=Calendar.getInstance();
        calendarCheck.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DATE),
                16,0,0);
        long startTimeCheck = calendarCheck.getTimeInMillis();
        Calendar calendarCheck2 = Calendar.getInstance();
        calendarCheck2.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE),
                22, 20, 0);

        long endTime1 = calendarCheck2.getTimeInMillis();
        if(timeCheck>startTimeCheck&&timeCheck<endTime1) {


            text1 = (TextView) findViewById(R.id.text1);
            long time = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE),
                    22, 20, 0);

            long endTime = calendar.getTimeInMillis();
            new CountDownTimer(endTime - time, 1000) { // adjust the milli seconds here

                public void onTick(long millisUntilFinished) {

                    text1.setText("Mess Booking Ends in: " + String.format(FORMAT,
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))+" hours");
                }

                public void onFinish() {
                    text1.setText("Todays Mess Booking is done");
                }
            }.start();
        }
        else
        {         Button6.setVisibility(View.GONE);
            text1 = (TextView) findViewById(R.id.text1);
            text1.setText("Booking has not yet started for today");
            booked=false;

            SharedPreferences sharedPref= getSharedPreferences("mypref", 0);
            //now get Editor
            SharedPreferences.Editor editor= sharedPref.edit();
            editor.putBoolean("booked",false);
            //commits your edits
            editor.commit();

        }




    }


    public void fillContent(){

        TextView text = (TextView) findViewById(R.id.name);
        text.setText("");
        text.setText("Name: "+name.toString()); //set value to current value of breakfast
          //Set edit-text field to not editable until user clicks on updateMenu option again

        text = (TextView) findViewById(R.id.mess);
        text.setText("");
        text.setText("Mess: "+mess.toString());

        text = (TextView) findViewById(R.id.rollno);
        text.setText("");
        text.setText("Roll Number: "+rollnum.toString());

          //user is not editing now
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            TextView text1 = (TextView) findViewById(R.id.breakfast);
            text1.setVisibility(View.VISIBLE);
            text1.setText("");
            text1.setText("BreakFast: "+breakfast.toString());
            TextView text2 = (TextView) findViewById(R.id.lunch);
            text2.setVisibility(View.VISIBLE);
            text2.setText("");
            text2.setText("Lunch: "+lunch.toString());
            TextView text3 = (TextView) findViewById(R.id.dinner);
            text3.setVisibility(View.VISIBLE);
            text3.setText("");
            text3.setText("Dinner: "+dinner.toString());
            TextView text4 = (TextView) findViewById(R.id.rate);
            text4.setVisibility(View.VISIBLE);
            text4.setText("");
            text4.setText("Rate: "+rate.toString());
            TextView text5 = (TextView) findViewById(R.id.seats);
            text5.setVisibility(View.VISIBLE);
            text5.setText("");
            text5.setText("Seats Left: "+seat.toString());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    TextView text1 = (TextView) findViewById(R.id.breakfast);
                    text1.setVisibility(View.GONE);
                    TextView text2 = (TextView) findViewById(R.id.lunch);
                    text2.setVisibility(View.GONE);
                    TextView text3 = (TextView) findViewById(R.id.dinner);
                    text3.setVisibility(View.GONE);
                    TextView text4 = (TextView) findViewById(R.id.rate);
                    text4.setVisibility(View.GONE);
                    TextView text5 = (TextView) findViewById(R.id.seats);
                    text5.setVisibility(View.GONE);

                }
            }, 10000);
        } else if (id == R.id.nav_dues) {
            TextView text = (TextView) findViewById(R.id.dues);
            text.setVisibility(View.VISIBLE);
            text.setText("");
            text.setText("Dues: "+duesD.toString());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    TextView text = (TextView) findViewById(R.id.dues);
                    text.setVisibility(View.GONE);
                }
            }, 10000);

        } else if (id == R.id.nav_book) {




        } else if (id == R.id.nav_logout) {
           // FirebaseAuth.getInstance().signOut();   //signs out
           // signedOut = true;
           // Intent intent = new Intent(this,LoginActivity.class);
          //  startActivity(intent);  //starts LoginActivity
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
