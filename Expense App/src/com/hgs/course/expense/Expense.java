package com.hgs.course.expense;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hgs.course.expense.constants.ExpenseConstants;
import com.hgs.course.expense.datastore.CategoryDatabaseOperationImpl;
import com.hgs.course.expense.datastore.FacadeStore;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Expense extends Activity implements ExpenseConstants, OnFocusChangeListener, Button.OnClickListener {	
	private ArrayAdapter<CharSequence> m_adapterForSpinner;
	private TableLayout table;
	private TableRow objNewCat;
	private DatePickerDialog.OnDateSetListener mDateSetListener;
	private final int DATE_DIALOG_ID = 0;
	private EditText objDate;
	private String category = null;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        table = (TableLayout)findViewById(R.id.tableLayout1);
        objNewCat = (TableRow)findViewById(R.id.tableRow5);
        table.removeView(objNewCat);
        
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        m_adapterForSpinner = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        m_adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(m_adapterForSpinner);
        populateCategory();
        spinner.setOnItemSelectedListener(new SpinnerSelectListner());
        
        objDate = (EditText) findViewById(R.id.editText2);
        objDate.setOnFocusChangeListener(this);
        
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				String date_selected = ((monthOfYear+1 < 10)? "0"+(monthOfYear+1) : (monthOfYear+1))+"/"+((dayOfMonth < 10)? "0"+(dayOfMonth) : (dayOfMonth))+"/"+String.valueOf(year);
				Log.d("date_selected = ",date_selected);
				objDate.setText(date_selected);
			}
        };
        Button submitButton = (Button)findViewById(R.id.button1);
        submitButton.setOnClickListener(this);
    }    
    
	// Creating dialog
	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar c = Calendar.getInstance();
		int cyear = c.get(Calendar.YEAR);
		int cmonth = c.get(Calendar.MONTH);
		int cday = c.get(Calendar.DAY_OF_MONTH);
		switch (id) {
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this,  mDateSetListener,  cyear, cmonth, cday);
		}
		return null;
	}
	
	@Override
	public void onClick(View v) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String merchant = ((EditText) findViewById(R.id.editText1)).getText().toString();			
			String strDate = ((EditText) findViewById(R.id.editText2)).getText().toString();
			String strAmount = ((EditText) findViewById(R.id.editText3)).getText().toString();
			if(_BLANK.equals(merchant)){
				((EditText) findViewById(R.id.editText1)).requestFocus();
				return;
			} else if(_BLANK.equals(strDate)) {
				((EditText) findViewById(R.id.editText2)).requestFocus();
				return;
			} else if(_BLANK.equals(strAmount)) {
				((EditText) findViewById(R.id.editText3)).requestFocus();
				return;
			} else if(ADD_CATEGORY_LABEL.equals(category)){
				if(_BLANK.equals(((EditText) findViewById(R.id.editText4)).getText().toString())) {
					((EditText) findViewById(R.id.editText4)).requestFocus();
					return;
				}
			}
			Date date = df.parse(strDate);
			Double amount = Double.parseDouble(strAmount);
			result.put(MERCHANT_LABEL, merchant);
			result.put(DATE_LABEL, date);
			result.put(AMOUNT_LABEL, amount);
			result.put(CATEGORY_LABEL,category);
			if(ADD_CATEGORY_LABEL.equals(category)){				
				result.put(NEW_CATEGORY_LABEL, ((EditText) findViewById(R.id.editText4)).getText().toString());
			}else {				
				result.put(NEW_CATEGORY_LABEL, null);
			}
			if(FacadeStore.saveExpenses(this,result)) {
				if(ADD_CATEGORY_LABEL.equals(category)){
					populateCategory();
				}
				alertbox(ALERT_TITLE,merchant + ALERT_MESSAGE);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		Editable dateValue = objDate.getText();
		if(v == objDate && hasFocus) {			
			if(dateFormat.equalsIgnoreCase(dateValue.toString())){
				objDate.setText(_BLANK);
			}			
			showDialog(DATE_DIALOG_ID);
		}else if(v == objDate && hasFocus == false){
			if(_BLANK.equalsIgnoreCase(dateValue.toString())){
				objDate.setText(dateFormat);
			}
		}
	}
	
	private void populateCategory() {
    	m_adapterForSpinner.clear();
    	m_adapterForSpinner.add(ADD_CATEGORY_LABEL);
    	List<CategoryDatabaseOperationImpl> objCat = FacadeStore.getCategories(this);
    	Log.v("objCat = ",objCat.toString());
        Iterator<CategoryDatabaseOperationImpl> itr = objCat.iterator();
        while(itr.hasNext()){
        	CategoryDatabaseOperationImpl obj = itr.next();
        	m_adapterForSpinner.add(obj.getType());
        }
    }
	
	protected void alertbox(String title, String mymessage)
	{
		new AlertDialog.Builder(this)
	      .setMessage(mymessage)
	      .setTitle(title)
	      .setCancelable(true)
	      .setNeutralButton(android.R.string.ok,
	         new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int whichButton){}
	         })
	      .show();
	}
	
	private class SpinnerSelectListner implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {			
			category = parent.getSelectedItem().toString();
			Log.v(SpinnerSelectListner.class.toString()," Selected Item is = " + category);
			if(ADD_CATEGORY_LABEL.equals(category)){				
		        table.addView(objNewCat,4);
			}else {				
		        table.removeView(objNewCat);
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// Nothing to Do with this
		}    	
    }	
}