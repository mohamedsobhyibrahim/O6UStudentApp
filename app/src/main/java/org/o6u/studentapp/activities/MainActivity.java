package org.o6u.studentapp.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.o6u.studentapp.R;
import org.o6u.studentapp.customViews.CustomTextView;
import org.o6u.studentapp.customViews.CustomTypefaceSpan;
import org.o6u.studentapp.fragments.CoursesFragment;
import org.o6u.studentapp.fragments.GradesFragment;
import org.o6u.studentapp.fragments.ProfileFragment;
import org.o6u.studentapp.registrationActivities.LoginActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Toolbar
    private Toolbar mToolbar;
    private CustomTextView titleToolbarTextView;

    //Nav Header
    private CustomTextView nameNavTextView;
    private CustomTextView idNavTextView;
    private CustomTextView emailNavTextView;

    //Database
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        titleToolbarTextView = (CustomTextView) findViewById(R.id.toolbar_title_text_view);
        titleToolbarTextView.setText("O6U - Student App");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Change Font of Navigation Drawer
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            //for applying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
        }

        //Define Nav Header
        View headerView = navigationView.getHeaderView(0);
        nameNavTextView = (CustomTextView) headerView.findViewById(R.id.nav_header_name_textView);
        idNavTextView = (CustomTextView) headerView.findViewById(R.id.nav_header_id_textView);
        emailNavTextView = (CustomTextView) headerView.findViewById(R.id.nav_header_email_textView);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {

            //Database
            mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
            String current_uid = mCurrentUser.getUid();
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Students").child(current_uid);
            mUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String id = dataSnapshot.child("id").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    nameNavTextView.setText(name);
                    idNavTextView.setText(id);
                    emailNavTextView.setText(email);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            });

            //Start First Fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_frame_layout, new ProfileFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else { super.onBackPressed(); }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendToStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void sendToStart() {

        Intent startIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sign_out_btn) {
            FirebaseAuth.getInstance().signOut();
            sendToStart();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
            titleToolbarTextView.setText("Profile");
        } else if (id == R.id.nav_grades) {
            fragment = new GradesFragment();
            Bundle bundle = new Bundle();
            bundle.putString("STUDENT_ID", idNavTextView.getText().toString());
            fragment.setArguments(bundle);
            titleToolbarTextView.setText("Grades");
        } else if (id == R.id.nav_courses) {
            fragment = new CoursesFragment();
            titleToolbarTextView.setText("Courses");
        }
        if (fragment != null) {
            FragmentTransaction fc = getSupportFragmentManager().beginTransaction();
            fc.replace(R.id.main_frame_layout, fragment);
            fc.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Change Fonts of Navigation Drawer
    private void applyFontToMenuItem(MenuItem mi) {
        final String FONT_NAME = "jf_flat_regular.ttf";
        Typeface font = Typeface.createFromAsset(getAssets(), FONT_NAME);
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

}
