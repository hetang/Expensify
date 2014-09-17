package com.hgs.course.expense.constants;

public interface ExpenseConstants {		
	int DATABASE_VERSION = 1;
	
	String DATABASE_NAME = "expense.db";
	
	/* Queries Goes Here */
	String sqlDropTableCategory = "DROP TABLE IF EXISTS CATEGORY";
	String sqlDropTableExpense = "DROP TABLE IF EXISTS EXPENSE";
	String sqlCreateTableCategory = "CREATE TABLE IF NOT EXISTS CATEGORY(ID INTEGER PRIMARY KEY, TYPE TEXT)";
	String sqlCreateTableExpense = "CREATE TABLE IF NOT EXISTS EXPENSE(ID INTEGER PRIMARY KEY, NAME TEXT, TYPE INTEGER, AMOUNT REAL, EXPENSE_DATE TEXT, FOREIGN KEY(TYPE) REFERENCES CATEGORY(ID))";
	String sqlInsertCategory1 = "INSERT INTO CATEGORY (ID,TYPE) VALUES(1,'Food')";
	String sqlInsertCategory2 = "INSERT INTO CATEGORY (ID,TYPE) VALUES(2,'Rent')";
	String sqlInsertCategory3 = "INSERT INTO CATEGORY (ID,TYPE) VALUES(3,'GAS')";
	String sqlInsertCategory4 = "INSERT INTO CATEGORY (ID,TYPE) VALUES(4,'ENTERTAINMENT')";
	
	String id_Label = "ID";
	String type_Label = "Type";
	
	String tableCategory = "CATEGORY";	
	String tableExpense = "EXPENSE";
	String expenseName_Label = "Name";	
	String expenseAmount_Label = "Amount";
	String AMOUNT_LABEL = "AMOUNT";
	String MERCHANT_LABEL = "MERCHANT";
	String DATE_LABEL = "DATE";
	String CATEGORY_LABEL = "CATEGORY";
	String NEW_CATEGORY_LABEL = "NEW_CATEGORY";
	String expenseDate_Label = "EXPENSE_DATE";
	
	String sqlExpenseSelect = "SELECT E.ID AS ID,NAME,C.ID AS C_ID,C.TYPE AS C_TYPE,AMOUNT,EXPENSE_DATE FROM CATEGORY C, EXPENSE E WHERE C.ID = E.TYPE ORDER BY NAME";
	String sqlCategorySelect = "SELECT ID, TYPE FROM CATEGORY ORDER BY TYPE";
	String sqlCategorySelectByType = "SELECT ID, TYPE FROM CATEGORY WHERE TYPE = ?";
	String sqlMaxCategory = "SELECT MAX(ID) FROM CATEGORY";
	String sqlMaxExpenses = "SELECT MAX(ID) FROM EXPENSE";
	
	String ADD_CATEGORY_LABEL = "ADD CATEGORY";
	
	String dateFormat = "mm/dd/yyyy";
	String _BLANK = "";
	String ALERT_TITLE = "Save Alert";
	String ALERT_MESSAGE = " Expense Saved Successfully";
}
