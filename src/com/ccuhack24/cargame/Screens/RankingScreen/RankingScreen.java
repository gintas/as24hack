package com.ccuhack24.cargame.Screens.RankingScreen;

import java.util.ArrayList;

import com.ccuhack24.cargame.R;
import com.ccuhack24.cargame.Screens.MapScreen.MapScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RankingScreen extends Activity {

    private ListView goodListLV;
    private ListView badListLV;

    private ArrayList<String> goodUsers;
    private ArrayList<String> badUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_ranking_screen);

	goodListLV = (ListView) findViewById(R.id.rankingScreen_good_list);
	badListLV = (ListView) findViewById(R.id.rankingScreen_bad_list);

	initUserLists();

	// now fill the rankings with sample data
	ArrayAdapter<String> goodLVadapter = new ArrayAdapter<String>(
		getApplicationContext(), R.layout.list_item_ranked_user,
		goodUsers);
	goodListLV.setAdapter(goodLVadapter);

	ArrayAdapter<String> badLVadapter = new ArrayAdapter<String>(
		getApplicationContext(), R.layout.list_item_ranked_user,
		badUsers);
	badListLV.setAdapter(badLVadapter);
    }

    // fake User ranking data
    private void initUserLists() {
	goodUsers = new ArrayList<String>();
	goodUsers.add("26.832 Gintautas Miliauskas");
	goodUsers.add("21.982 Dirk Gomez");
	goodUsers.add("14.239 Adeel Naveed");
	goodUsers.add(" 9.319 Sergiu Soima");
	goodUsers.add(" 5.219 Dominik Eggert");

	badUsers = new ArrayList<String>();
	badUsers.add("-98.234 Chaoran Chen");
	badUsers.add("-73.282 Arian Avini");
	badUsers.add("-53.981 Jan Wahler");
	badUsers.add("-23.193 Muhammad Talha");
	badUsers.add("-12.981 Boris Danne");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.map_screen, menu);
	final Intent intent = new Intent(this, MapScreen.class);
	menu.findItem(R.id.action_map).setOnMenuItemClickListener(
		new OnMenuItemClickListener() {

		    @Override
		    public boolean onMenuItemClick(MenuItem item) {
			startActivity(intent);
			return false;
		    }
		});
	return true;
    }
}
