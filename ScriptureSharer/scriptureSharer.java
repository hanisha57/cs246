import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class BossScriptureSharer extends ActionBarActivity {
    public static final String EXTRA = "extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boss_scripture_sharer);
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
     * shareMessage method
     *   This method links two activities together using an Intent
     *   object. It craets the string to be printed and passe it to
     *   the scripturePrinter class.
     */
    public void shareMessage(View view){
        // Makes bridge between the two activities
        Intent intent = new Intent(this, scripturePrinter.class);

        // Gets the text from the activity
        EditText book = (EditText) findViewById(R.id.editText);
        EditText chapter = (EditText) findViewById(R.id.editText2);
        EditText verse = (EditText) findViewById(R.id.editText3);

        // Makes string to display to screen
        String myScripture = "Your favorite scripture is: " + book.getText().toString()
                + " " + chapter.getText().toString() + ":" + verse.getText().toString();

        // Passes myScripture to scripture printer through intent
        intent.putExtra(EXTRA, myScripture);
        startActivity(intent);
    }
}

  

