package com.hgs.course.expense.database;

import com.hgs.course.expense.constants.ExpenseConstants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseImpl extends SQLiteOpenHelper implements ExpenseConstants {	
	
	public DatabaseImpl(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}
	
	@Override
	public void onCreate(SQLiteDatabase objDB) {
		objDB.execSQL(sqlDropTableCategory);
		objDB.execSQL(sqlDropTableExpense);
		objDB.execSQL(sqlCreateTableCategory);
		objDB.execSQL(sqlCreateTableExpense);
		objDB.execSQL(sqlInsertCategory1);
		objDB.execSQL(sqlInsertCategory2);
		objDB.execSQL(sqlInsertCategory3);
		objDB.execSQL(sqlInsertCategory4);
	}

	@Override
	public void onUpgrade(SQLiteDatabase objDB, int oldVersion, int newVersion) {
		// TODO Need to Upgrade When Older Version Found
	}

}
