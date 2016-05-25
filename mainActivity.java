import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends ActionBarActivity {

    //Private member variables
    private String filename = "numbers.txt";
    private int numbers[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private FileOutputStream out;
    private FileInputStream in;
    private ProgressBar progress;
    private ArrayAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize member variables
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);
        progress.setProgress(0);
        itemsAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.list1);
        listView.setAdapter(itemsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * createFile method
     *
     *  This method is connected to the create button. It makes a
     *  new thread and calls create() in it's run method.
     */
    public void createFile(View view) {
       Thread t1 = new Thread(new Runnable() {
           @Override
            public void run() {
                create();
                }
        });
        t1.start();
    }

    /**
     *  create method
     *
     *    This method created a file containing the numbers 1 - 10.
     *    It stores this file in internal memory. It uses the Thread.sleep
     *    command to simulate a more difficult task and updates the progress
     *    bar as it runs.
     */
    public void create() {
        try {
            out = openFileOutput(filename, Context.MODE_PRIVATE);
            for (int i : numbers) {
                out.write(i);
                Thread.sleep(250);
                progBar(i + 1);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * loadFile method
     *
     *  This method is connected to the load button. It makes a
     *  new thread and calls load() in it's run method.
     */
    public void loadFile(View view) {
      Thread t2 = new Thread(new Runnable() {
          @Override
            public void run() {
                load();
           }
        });
        t2.start();
    }

    /**
     *  load method
     *
     *    This method loads the file containing the numbers 1 - 10.
     *    It puts this file in a list and displays it on the screen.
     *    It uses the Thread.sleep command to simulate a more difficult
     *    task and updates the progress bar as it runs.
     */
    public void load() {

        //runs on main thread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.setProgress(0);
            }
        });

        //reads in file
        try {
            in = openFileInput(filename);
            int temp;

            while ((temp = in.read()) != -1) {
                //Need a final variable to use in inner run method
                final int temp2 = temp;
                //Allows the itemsAdapter and progBar to work on main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        itemsAdapter.add(temp2);
                        progBar(temp2);
                    }
                });
                Thread.sleep(250);
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Error loading ...\n");
            e.printStackTrace();
        }
    }

    /**
     * clearList method
     *
     *  This method is connected to the clear button. It simply
     *  clears the list that is displayed on the screen.
     * @param view
     */
    public void clearList(View view) {
        itemsAdapter.clear();
        progress.setProgress(0);
    }

    /**
     * progBar method
     *
     *  This method updates the value used to set
     *  the progress bar and updates the progress bar.
     * @param i
     */
    public void progBar(int i) {
        progress.setProgress((i) * 10);
    }
}