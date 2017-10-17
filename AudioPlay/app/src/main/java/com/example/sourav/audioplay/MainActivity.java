package com.example.sourav.audioplay;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    Button start,pause,stop,download,show;
    TextView t1;
    MediaPlayer player;
    SeekBar seek_bar;
    Handler seekHandler = new Handler();
    private ProgressDialog mDialog;
    private StorageReference reference;
String[] string=new String[10];
int counter=0;
    ListView view;
    List<String> songs1 = new ArrayList<String>();
    StorageReference filepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seek_bar = (SeekBar) findViewById(R.id.seek_bar);
        mDialog = new ProgressDialog(this);
        start=(Button)findViewById(R.id.button1);
        pause=(Button)findViewById(R.id.button2);

        t1=(TextView)findViewById(R.id.tv);
        stop=(Button)findViewById(R.id.button3);
        download=(Button)findViewById(R.id.button4);
        show=(Button)findViewById(R.id.showlist);

        view=(ListView)findViewById(R.id.listView1);

        try {
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource("https://firebasestorage.googleapis.com/v0/b/audioplay-f02df.appspot.com/o/Audio%2FAisi%20Mulaqaat%20-%20Rahat%20Fateh%20Ali%20Khan%20(Mr-Punjab.Com).mp3?alt=media&token=7f500645-ce05-4443-8fda-39af64cf8b05");
            //player.setDataSource("https://firebasestorage.googleapis.com/v0/b/audioplay-f02df.appspot.com/o/Audio%2FBibi%20Bamb%20Aa%20Bai%20-%20Anmol%20Preet%20Feat%20Jsl%20Singh%20(DjPunjab.Com).mp3?alt=media&token=bf7062c3-f876-4f36-b9e8-f737f39fe019");

            player.prepare();
      } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                player.start();
            }
        });
        show.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showlist();
            }
        });
        pause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
            }
        });
        stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
            }
        });
        download.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAudio();
            }
        });

        seek_bar.setMax(player.getDuration());
        seekUpdation();
    }
    Runnable run = new Runnable()
    {
        @Override public void run()
        {
            seekUpdation();
        }
    };
    public void seekUpdation() {
        seek_bar.setProgress(player.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    private  void downloadAudio()
    {
        try {
            Log.e("Sourav", "I should be here");
            mDialog.setMessage("Download Audio");
            mDialog.show();
            Log.e("Sourav", "I should be here there");
            //StorageReference filepath = reference.child("Audio").child("Allah_Maaf_Kare_(Remix)-Sonu_Nigam(www.Mzc.in).mp3");
            FirebaseStorage storage = FirebaseStorage.getInstance();
            filepath = storage.getReference().child("Audio").child("Aisi Mulaqaat - Rahat Fateh Ali Khan (Mr-Punjab.Com).mp3");
            Log.e("Sourav", "I should be here there fare");

            Toast.makeText(getApplicationContext(), filepath.getName(), Toast.LENGTH_LONG).show();

                       File localFile  = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "song.mp3");
            localFile .createNewFile();
            filepath.getFile(localFile);


            filepath.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                   // songslist.add(taskSnapshot.toString());
                    Toast.makeText(getApplicationContext(),"Downloded",Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showlist(){
        Log.e("sourav","its entered in showlist");
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        Log.e("sourav","its crossed in showlist");
        String input = "a";
        String input2="b";

        DatabaseReference songs = databaseRef.child("songs");
        Query citiesQuery = songs.orderByKey();
        citiesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    songs1.add(postSnapshot.getKey().toString());

                }
                System.out.println(songs1);
                showdata();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
}
    private void showdata(){
        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.list_songs,R.id.tv,songs1);
        view.setAdapter(adapter);


    }
}