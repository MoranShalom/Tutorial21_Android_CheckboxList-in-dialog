package com.example.tutorial21_checkbox_dialog;

import android.content.DialogInterface;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private Button mOrder;
    private TextView mItemSelected;
    private String[] listItems;// the original list
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems= new ArrayList<>();// the user selected items list



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mOrder = (Button) findViewById(R.id.btnOrder);
        mItemSelected= (TextView) findViewById(R.id.textView);

        listItems=getResources().getStringArray((R.array.Shopping_Item));
        checkedItems= new boolean[listItems.length];

        for(int i=0; i<listItems.length;i++){
                mUserItems.add(null);
            Log.i(TAG, "position size: " +mUserItems.size());
        }

        mOrder.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                // create an AlertDialog
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                //set a title for the AlertDialog
                mBuilder.setTitle(R.string.dialog_list_title);

                /////// setMultiChoiceItems - for the checked box list
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {

                        //if the Item is checked
                        if (isChecked) {
                            //if the item is not part of the list
                            Log.i(TAG, "position: " +position);
                            Log.i(TAG, "position mUserItems : " +!mUserItems.contains(position));
                            if (!mUserItems.contains(position)) {
                                //add the item

                                    mUserItems.set(position,position);
                                }
                            }
                        else if (mUserItems.contains(position)) {
                                mUserItems.set(position, null);
                            }
                        }
                });

                mBuilder.setCancelable(false);
                // set a buttons for the AlertDialog
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String item= "";
                        for(int i=0; i<mUserItems.size(); i++){
                            if (mUserItems.get(i)==null) {
                                item+="";
                            } else {
                                if(i!= mUserItems.size())
                                item+= listItems[mUserItems.get(i)]+",";
                            }
                            //add "," in between the Items except of the last item
//                            if(i!= mUserItems.size()-1 ){
//                                {
//                                item=item+",";}
//                            }
                        }
                        mItemSelected.setText(item);
                    }
                });


                mBuilder.setNegativeButton(R.string.Dismiss_button, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                mBuilder.setNeutralButton(R.string.clear_all_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for(int i=0; i<checkedItems.length; i++){

                            // to clear the Items inside the user selection list
                            checkedItems[i]=false;
                            //clear the Items inside the Array list
                            mUserItems.set(i, null);
                            //set an empty text inside the text view
                            mItemSelected.setText("");
                        }

                    }
                });

                AlertDialog dialog= mBuilder.create();
                dialog.show();

            }
        });
    }
}
