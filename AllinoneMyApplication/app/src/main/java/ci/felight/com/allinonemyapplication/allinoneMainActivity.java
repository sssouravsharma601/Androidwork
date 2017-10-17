package ci.felight.com.allinonemyapplication;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;

public class allinoneMainActivity extends AppCompatActivity {
    private Button button;

        public void access (View view) {
            try

            {

                switch (view.getId()) {
                    case R.id.bt1:
                        Intent int1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivity(int1);
                        break;
                    case R.id.bt2:

                        break;
                    case R.id.bt3:
                        break;
                    case R.id.bt4:
                        break;
                    case R.id.bt5:
                        break;
                    case R.id.bt6:
                        break;
                    case R.id.bt7:
                        break;
                    case R.id.bt8:
                        break;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allinone_main);
        button = (Button) findViewById(R.id.bt1);
        button = (Button) findViewById(R.id.bt2);
        button = (Button) findViewById(R.id.bt3);
        button = (Button) findViewById(R.id.bt4);
        button = (Button) findViewById(R.id.bt5);
        button = (Button) findViewById(R.id.bt6);
        button = (Button) findViewById(R.id.bt7);
        button = (Button) findViewById(R.id.bt8);
    }

}


