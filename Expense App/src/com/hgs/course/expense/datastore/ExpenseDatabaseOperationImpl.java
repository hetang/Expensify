package com.hgs.course.expense.datastore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hgs.course.expense.constants.ExpenseConstants;
import com.hgs.course.expense.database.DatabaseImpl;

public class ExpenseDatabaseOperationImpl implements DatabaseOperation<ExpenseDatabaseOperationImpl>, ExpenseConstants {
	private DatabaseImpl m_objDB = null;
	private int id = 0;
	private String name = null;
	private CategoryDatabaseOperationImpl type = null;
	private double amount = 0;
	private Date expenseDate = new Date();
	
	public ExpenseDatabaseOperationImpl() {	}
	
	public ExpenseDatabaseOperationImpl(DatabaseImpl objDB) {
		this.m_objDB = objDB;
	}
	
	@Override
	public boolean insertRecords(List<ExpenseDatabaseOperationImpl> newRecords) throws Exception {
		if(null != m_objDB) {
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			SQLiteDatabase db = m_objDB.getWritableDatabase();
			ContentValues newExpense = new ContentValues();
			Iterator<ExpenseDatabaseOperationImpl> itrRecords = newRecords.iterator();
			while(itrRecords.hasNext()) {
				ExpenseDatabaseOperationImpl objData = itrRecords.next();
				newExpense.clear();
				newExpense.put(id_Label, objData.getId());
				newExpense.put(expenseName_Label, objData.getName());
				newExpense.put(type_Label, objData.getType().getId());
				newExpense.put(expenseAmount_Label, objData.getAmount());
				newExpense.put(expenseDate_Label, df.format(objData.getExpenseDate()));
				db.insert(tableExpense, null, newExpense);
			}
			return true;
		} else {
			throw new Exception("Database Object is not Created");
		}
	}

	@Override
	public boolean updateRecords(List<ExpenseDatabaseOperationImpl> existingRecords) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRecords(List<ExpenseDatabaseOperationImpl> removeRecords) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ExpenseDatabaseOperationImpl> getRecords(String[] queryArgs) throws Exception {
		List<ExpenseDatabaseOperationImpl> expenses = new ArrayList<ExpenseDatabaseOperationImpl>();
		if(null != m_objDB) {
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			SQLiteDatabase db = m_objDB.getWritableDatabase();			
			Cursor result = db.rawQuery(sqlExpenseSelect, null);
			int rowCount = result.getCount();
			if(rowCount > 0) {
				result.moveToFirst();
				for(int i=0;i<rowCount;i++){
					result.moveToPosition(i);
					ExpenseDatabaseOperationImpl objData = new ExpenseDatabaseOperationImpl();
					CategoryDatabaseOperationImpl objCategory = new CategoryDatabaseOperationImpl();
					objData.setId(result.getInt(0));
					objData.setName(result.getString(1));
					objCategory.setId(result.getInt(2));
					objCategory.setType(result.getString(3));
					objData.setType(objCategory);
					objData.setAmount(result.getLong(4));
					objData.setExpenseDate(df.parse(result.getString(5)));
					expenses.add(objData);
				}
			}			
		} else {
			throw new Exception("Database Object is not Created");
		}
		return expenses;
	}

	@Override
	public ExpenseDatabaseOperationImpl viewRecord(String[] queryArgs) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CategoryDatabaseOperationImpl getType() {
		return type;
	}

	public void setType(CategoryDatabaseOperationImpl type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	@Override
	public int getNextId() throws Exception {
		if(null != m_objDB) {
			SQLiteDatabase db = m_objDB.getWritableDatabase();			
			Cursor result = db.rawQuery(sqlMaxExpenses, null);
			if(result.moveToFirst()) {
				return result.getInt(0) + 1;
			}
		}else {
			throw new Exception("Database Object is not Created");
		}
		return 0;
	}
}
