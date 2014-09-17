package com.hgs.course.expense.datastore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgs.course.expense.constants.ExpenseConstants;
import com.hgs.course.expense.database.DatabaseImpl;

import android.content.Context;
import android.util.Log;

public class FacadeStore implements ExpenseConstants{
	public static DatabaseImpl objDB = null;
	
	public static List<CategoryDatabaseOperationImpl> getCategories(Context context) {
		try {
			if(objDB == null){
				objDB = new DatabaseImpl(context);
			}			
			CategoryDatabaseOperationImpl objCatDataStore = new CategoryDatabaseOperationImpl(objDB);		
			return objCatDataStore.getRecords(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public static boolean saveExpenses(Context context, Map<String,?> objData){
		try {
			if(objDB == null){
				objDB = new DatabaseImpl(context);
			}
			CategoryDatabaseOperationImpl objCatDataStore = new CategoryDatabaseOperationImpl(objDB);
			CategoryDatabaseOperationImpl objCategory = null;
			ExpenseDatabaseOperationImpl objExpense = new ExpenseDatabaseOperationImpl(objDB);
			List<ExpenseDatabaseOperationImpl> newExpenseRecords = new ArrayList<ExpenseDatabaseOperationImpl>(1);
			if(null != objData.get(NEW_CATEGORY_LABEL)){
				List<CategoryDatabaseOperationImpl> newRecords = new ArrayList<CategoryDatabaseOperationImpl>(1);
				objCategory = new CategoryDatabaseOperationImpl();
				objCategory.setId(objCatDataStore.getNextId());
				objCategory.setType((String)objData.get(NEW_CATEGORY_LABEL));
				newRecords.add(objCategory);
				objCatDataStore.insertRecords(newRecords);
			}else {
				objCategory = objCatDataStore.viewRecord(new String[]{(String)objData.get(CATEGORY_LABEL)});
			}
			objExpense.setId(objExpense.getNextId());
			objExpense.setName((String)objData.get(MERCHANT_LABEL));
			objExpense.setType(objCategory);
			objExpense.setAmount((Double)objData.get(AMOUNT_LABEL));
			objExpense.setExpenseDate((Date)objData.get(DATE_LABEL));
			newExpenseRecords.add(objExpense);
			objExpense.insertRecords(newExpenseRecords);
			Log.d("objData = ", objData.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		return true;
	}
}
