package com.cdph.virtualrvm;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;

import com.cdph.virtualrvm.adapter.ItemListAdapter;
import com.cdph.virtualrvm.adapter.UserListAdapter;
import com.cdph.virtualrvm.db.VirtualRVMDatabase;
import com.cdph.virtualrvm.model.ItemModel;
import com.cdph.virtualrvm.model.UserModel;
import com.cdph.virtualrvm.util.Constants;

public class AdminActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
	private VirtualRVMDatabase db;
    private BottomNavigationView bottomNav;
	private FloatingActionButton fabAdd;
	private RecyclerView contentList;
	private Typeface flatFont;
    private EditText searchView;
	private TextView header;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
		
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        
        initViews();
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        boolean ret = false;
        
        switch(item.getItemId())
        {
            case R.id.action_items:
				loadAllItemData();
            	header.setText(R.string.admin_items_header);
				ret = true;
            break;
			
			case R.id.action_users:
				loadAllUserData();
				header.setText(R.string.admin_users_header);
				ret = true;
			break;
        }
        
        return ret;
    }
	
	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
		finish();
	}
    
    private void initViews()
    {
		db = new VirtualRVMDatabase(this);
		flatFont = Typeface.createFromAsset(getAssets(), "fonts/quicksand_light.ttf");
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(this);
		
		contentList = findViewById(R.id.content_list);
		contentList.setLayoutManager(new LinearLayoutManager(this));
		
		header = findViewById(R.id.admin_list_header);
		header.setTypeface(flatFont, Typeface.BOLD);
		
		bottomNav.setSelectedItemId(R.id.action_items);
    }
	
	private void loadAllItemData()
	{
		List<ArrayList<String>> items = db.getAllItemData();
		List<ItemModel> itemModels = new ArrayList<>();

		for(int i = 0; i < items.size(); i++)
		{
			ArrayList<String> itemData = items.get(i);
			String id = itemData.get(0);
			String name = itemData.get(1);
			String weight = itemData.get(2);
			String type = itemData.get(3);
			String worth = itemData.get(4);

			itemModels.add(ItemModel.newItem(id, name, weight, type, worth));
		}
		
		ItemListAdapter itemAdapter = new ItemListAdapter(itemModels);
		contentList.setAdapter(itemAdapter);
	}
	
	private void loadAllUserData()
	{
		List<ArrayList<String>> users = db.getAllUserData();
		List<UserModel> userModels = new ArrayList<>();

		for(ArrayList<String> userData : users)
		{
			String name = userData.get(0);
			String pass = userData.get(1);
			String cent = userData.get(2);
			String rank = userData.get(3);

			userModels.add(UserModel.newUser(name, pass, cent, rank));
		}

		UserListAdapter userAdapter = new UserListAdapter(userModels);
		contentList.setAdapter(userAdapter);
	}
}
