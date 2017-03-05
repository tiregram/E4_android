package com.tiregram.glove.bluetoothglove;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;




/**
 * Created by Thibault.VIGIER on 07/02/2017.
 */
public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    protected NavigationView drawer;
    protected ViewGroup content;


    public static void start(Context context) {
        Intent starter = new Intent(context, DrawerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer = (NavigationView)findViewById(R.id.navigation);
        content = (ViewGroup)findViewById(R.id.content);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpDrawer();
    }



    protected void setDrawerContentView(int layoutResID) {
        //Récupération du layout
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(layoutResID, null, false);
        content.addView(contentView, 0);

        // Ajout de la Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Initialise le Menu tiroir
    protected void setUpDrawer() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        drawer.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Bouton menu
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_text:
                startActivity(GloveConnectTo.class);
                break;
            // ProfilActivity
            case R.id.nav_accelerometer:
                startActivity(AccelerometerActivity.class);
                break;
            // ProfilActivity
            case R.id.nav_gyroscope:
                startActivity(GyroscopeActivity.class);
                break;
            // ProfilActivity
            case R.id.nav_flex_sensors:
                startActivity(FlexSensorActivity.class);
                break;
            // ProfilActivity
            case R.id.nav_three_dimensions_view:
                startActivity(ViewActivity.class);
                break;
            // ProfilActivity
            case R.id.nav_about_us:
                startActivity(AboutUsActivity.class);
                break;
            // ProfilActivity
            case R.id.nav_credentials:
                startActivity(CredentialActivity.class);
                break;

            default:
                throw new IllegalStateException("Élément de navigation non géré.");
        }


        return true;
    }


    private void startActivity(Class<?> classNewActivity) {
        drawerLayout.closeDrawers();

        if(!drawerLayout.isDrawerOpen(GravityCompat.END)) {

            Class<?> classThisActivity = getClass();

            // Si l'activité à démarrer est la même que celle en cours, on en fait rien
            if (classThisActivity == classNewActivity)
                return;

            // On démarre l'activité
            startActivity(new Intent(DrawerActivity.this, classNewActivity));

            // On termine l'activité en cours, sauf si c'est MainActivity (pour pouvoir revenir dessus avec bouton retour)
            if (classThisActivity != AccelerometerActivity.class) {
                finish();
            }
        }
    }

}
