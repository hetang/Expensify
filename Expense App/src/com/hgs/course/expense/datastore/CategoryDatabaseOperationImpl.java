package com.hgs.course.expense.datastore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hgs.course.expense.constants.ExpenseConstants;
import com.hgs.course.expense.database.DatabaseImpl;

public class CategoryDatabaseOperationImpl implements DatabaseOperation<CategoryDatabaseOperationImpl>, ExpenseConstants {
	private DatabaseImpl m_objDB = null;
	private int id = 0;
	private String type = null;
	
	public CategoryDatabaseOperationImpl() {	}
	
	public CategoryDatabaseOperationImpl(DatabaseImpl objDB) {
		this.m_objDB = objDB;
	}
	
	@Override
	public boolean insertRecords(List<CategoryDatabaseOperationImpl> newRecords) throws Exception {
		if(null != m_objDB) {
			SQLiteDatabase db = m_objDB.getWritableDatabase();
			ContentValues newExpense = new ContentValues();
			Iterator<CategoryDatabaseOperationImpl> itrRecords = newRecords.iterator();
			while(itrRecords.hasNext()) {
				CategoryDatabaseOperationImpl objData = itrRecords.next();
				newExpense.clear();
				newExpense.put(id_Label, objData.getId());
				newExpense.put(type_Label, objData.getType());				
				db.insert(tableCategory, null, newExpense);
			}
			return true;
		} else {
			throw new Exception("Database Object is not Created");
		}
	}

	@Override
	public boolean updateRecords(List<CategoryDatabaseOperationImpl> existingRecords) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CategoryDatabaseOperationImpl> getRecords(String[] queryArgs) throws Exception {
		List<CategoryDatabaseOperationImpl> categories = new ArrayList<CategoryDatabaseOperationImpl>();
		if(null != m_objDB) {
			SQLiteDatabase db = m_objDB.getWritableDatabase();			
			Cursor result = db.rawQuery(sqlCategorySelect, queryArgs);
			if(result.moveToFirst()) {
				do{
					CategoryDatabaseOperationImpl objCategory = new CategoryDatabaseOperationImpl();
					objCategory.setId(result.getInt(0));
					objCategory.setType(result.getString(1));					
					categories.add(objCategory);
				}while(result.moveToNext());
			}			
		} else {
			throw new Exception("Database Object is not Created");
		}
		return categories;
	}

	@Override
	public CategoryDatabaseOperationImpl viewRecord(String[] queryArgs) throws Exception {
		CategoryDatabaseOperationImpl objCategory = new CategoryDatabaseOperationImpl();
		if(null != m_objDB) {
			SQLiteDatabase db = m_objDB.getWritableDatabase();			
			Cursor result = db.rawQuery(sqlCategorySelectByType, queryArgs);
			if(result.moveToFirst()) {
				objCategory.setId(result.getInt(0));
				objCategory.setType(result.getString(1));					
			}
		}else {
			throw new Exception("Database Object is not Created");
		}
		return objCategory;
	}

	@Override
	public boolean deleteRecords(List<CategoryDatabaseOperationImpl> removeRecords) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int getNextId() throws Exception {
		if(null != m_objDB) {
			SQLiteDatabase db = m_objDB.getWritableDatabase();			
			Cursor result = db.rawQuery(sqlMaxCategory, null);
			if(result.moveToFirst()) {
				return result.getInt(0) + 1;
			}
		}else {
			throw new Exception("Database Object is not Created");
		}
		return 0;
	}
	
}
