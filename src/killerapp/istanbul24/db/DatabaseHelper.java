package killerapp.istanbul24.db;

/* * * * * * 
 * DatabaseHelper
 * v0.2
 * -----
 * TODO Write better code, combine methods.
 * * * * * *
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "24Istanbul-db";

	// Table Names
	private static final String TABLE_VENUE = "venues";
	private static final String TABLE_QUESTION = "questions";
	private static final String TABLE_CATEGORY = "categories";
	private static final String TABLE_TAG = "tags";
	private static final String TABLE_OPTION = "options";
	private static final String TABLE_VENUE_META = "venue_metas";

	// Common columns
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_TAG_ID = "tagId";
	private static final String KEY_VENUE_ID = "venueId";
	private static final String KEY_CATEGORY_ID = "categoryId";
	private static final String KEY_LAST_UPDATE_DATE = "lastUpdateDate";

	// VENUES Table - columns
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_LATITUDE = "latitude";

	// QUESTIONS Table - columns
	private static final String KEY_QUESTION = "question";

	// CATEGORIES Table - columns
	// all defined in common columns

	// OPTIONS Table - columns
	private static final String KEY_QUESTION_ID = "questionId";

	// TAGS Table - columns
	// all defined in common columns

	// VENUE_METAS Table - columns
	// all defined in common columns

	private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE IF NOT EXISTS categories ("
			+ "id TINYINT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
			+ "name VARCHAR(32) NOT NULL,"
			+ "lastUpdateDate DATE NOT NULL"
			+ ");";

	private static final String CREATE_TABLE_OPTION = "CREATE TABLE IF NOT EXISTS options ("
			+ "id TINYINT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
			+ "questionId TINYINT(4) unsigned NOT NULL,"
			+ "name VARCHAR(64) NOT NULL,"
			+ "tagId TINYINT(4) NOT NULL,"
			+ "FOREIGN KEY(tagId) REFERENCES tags(id),"
			+ "FOREIGN KEY(questionId) REFERENCES questions(id)," + ");";

	private static final String CREATE_TABLE_QUESTION = "CREATE TABLE IF NOT EXISTS questions ("
			+ "id TINYINT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
			+ "question VARCHAR(255) NOT NULL,"
			+ "lastUpdateDate DATE NOT NULL," + ");";

	private static final String CREATE_TABLE_TAG = "CREATE TABLE IF NOT EXISTS questions ("
			+ "id TINYINT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
			+ "name VARCHAR(32) NOT NULL,"
			+ "categoryId TINYINT(4) NOT NULL,"
			+ ");";

	private static final String CREATE_TABLE_VENUE = "CREATE TABLE IF NOT EXISTS venues ("
			+ "id VARCHAR(36) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
			+ "name VARCHAR(255) NOT NULL,"
			+ "latitude FLOAT(10,6) NOT NULL,"
			+ "longitude FLOAT(10,6) NOT NULL,"
			+ "lastUpdateDate DATE NOT NULL,"
			+ "address VARCHAR(255) DEFAULT NULL" + ");";

	private static final String CREATE_TABLE_VENUE_META = "CREATE TABLE IF NOT EXISTS venue_meta ("
			+ "id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
			+ "venueId VARCHAR(36) NOT NULL,"
			+ "tagId TINYINT(4) NOT NULL,"
			+ "FOREIGN KEY(venueId) REFERENCES venues(id),"
			+ "FOREIGN KEY(tagId) REFERENCES tags(id)" + ");";
	
	public DatabaseHelper(Context context, CursorFactory factory)
	{
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// CREATE methods
	public long createCategory(Category arg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("id", arg.getId());
		values.put("name", arg.getName());
		values.put("lastUpdateDate", arg.getLastUpdateDate());

		return db.insert(TABLE_CATEGORY, null, values);
	}

	public long createOption(Option arg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, arg.getId());
		values.put(KEY_QUESTION_ID, arg.getQuestionId());
		values.put(KEY_NAME, arg.getName());
		values.put(KEY_TAG_ID, arg.getTagId());

		return db.insert(TABLE_OPTION, null, values);
	}

	public long createQuestion(Question arg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, arg.getId());
		values.put(KEY_QUESTION, arg.getQuestion());
		values.put(KEY_LAST_UPDATE_DATE, arg.getLastUpdateDate());

		return db.insert(TABLE_QUESTION, null, values);
	}

	public long createTag(Tag arg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, arg.getId());
		values.put(KEY_NAME, arg.getName());
		values.put(KEY_CATEGORY_ID, arg.getCategoryId());

		return db.insert(TABLE_TAG, null, values);
	}

	public long createVenue(Venue arg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, arg.getId());
		values.put(KEY_NAME, arg.getName());
		values.put(KEY_LATITUDE, arg.getLatitude());
		values.put(KEY_LONGITUDE, arg.getLongitude());
		values.put(KEY_ADDRESS, arg.getAddress());
		values.put(KEY_LAST_UPDATE_DATE, arg.getLastUpdateDate());

		return db.insert(TABLE_VENUE, null, values);
	}

	public long createVenueMeta(VenueMeta arg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, arg.getId());
		values.put(KEY_VENUE_ID, arg.getVenueId());
		values.put(KEY_TAG_ID, arg.getTagId());

		return db.insert(TABLE_VENUE_META, null, values);
	}

	// CREATE methods - end

	// READ methods

	public Category getCategory(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY + " WHERE "
				+ KEY_ID + " = " + id;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		return new Category(c.getInt(c.getColumnIndex(KEY_ID)), c.getString(c
				.getColumnIndex(KEY_NAME)), c.getString(c
				.getColumnIndex(KEY_LAST_UPDATE_DATE)));
	}

	public Option getOption(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_OPTION + " WHERE "
				+ KEY_ID + " = " + id;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		return new Option(c.getInt(c.getColumnIndex(KEY_ID)), c.getInt(c
				.getColumnIndex(KEY_QUESTION_ID)), c.getInt(c
				.getColumnIndex(KEY_TAG_ID)), c.getString(c
				.getColumnIndex(KEY_NAME)));
	}

	public Question getQuestion(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_QUESTION + " WHERE "
				+ KEY_ID + " = " + id;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		return new Question(c.getInt(c.getColumnIndex(KEY_ID)), c.getString(c
				.getColumnIndex(KEY_QUESTION)), c.getString(c
				.getColumnIndex(KEY_LAST_UPDATE_DATE)));
	}

	public Tag getTag(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_TAG + " WHERE " + KEY_ID
				+ " = " + id;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		return new Tag(c.getInt(c.getColumnIndex(KEY_ID)), c.getInt(c
				.getColumnIndex(KEY_CATEGORY_ID)), c.getString(c
				.getColumnIndex(KEY_NAME)));
	}

	public Venue getVenue(String id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_VENUE + " WHERE "
				+ KEY_ID + " = " + id;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		return new Venue(c.getString(c.getColumnIndex(KEY_ID)), c.getString(c
				.getColumnIndex(KEY_ADDRESS)), c.getString(c
				.getColumnIndex(KEY_NAME)), c.getDouble(c
				.getColumnIndex(KEY_LONGITUDE)), c.getDouble(c
				.getColumnIndex(KEY_LATITUDE)), c.getString(c
				.getColumnIndex(KEY_LAST_UPDATE_DATE)));
	}

	public VenueMeta getVenueMeta(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_VENUE_META + " WHERE "
				+ KEY_ID + " = " + id;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		return new VenueMeta(c.getInt(c.getColumnIndex(KEY_ID)), c.getInt(c
				.getColumnIndex(KEY_TAG_ID)), c.getString(c
				.getColumnIndex(KEY_VENUE_ID)));
	}

	// READ methods - end

	// UPDATE methods

	public int updateCategory(Category arg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, arg.getId());
		values.put(KEY_NAME, arg.getName());
		values.put(KEY_LAST_UPDATE_DATE, arg.getLastUpdateDate());

		return db.update(TABLE_CATEGORY, values, KEY_ID + " = ?",
				new String[] { String.valueOf(arg.getId()) });
	}

	public long updateOption(Option arg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, arg.getId());
		values.put(KEY_QUESTION_ID, arg.getQuestionId());
		values.put(KEY_NAME, arg.getName());
		values.put(KEY_TAG_ID, arg.getTagId());

		return db.update(TABLE_OPTION, values, KEY_ID + " = ?",
				new String[] { String.valueOf(arg.getId()) });
	}

	public long updateQuestion(Question arg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, arg.getId());
		values.put(KEY_QUESTION, arg.getQuestion());
		values.put(KEY_LAST_UPDATE_DATE, arg.getLastUpdateDate());

		return db.update(TABLE_QUESTION, values, KEY_ID + " = ?",
				new String[] { String.valueOf(arg.getId()) });
	}

	public long updateTag(Tag arg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, arg.getId());
		values.put(KEY_NAME, arg.getName());
		values.put(KEY_CATEGORY_ID, arg.getCategoryId());

		return db.update(TABLE_TAG, values, KEY_ID + " = ?",
				new String[] { String.valueOf(arg.getId()) });
	}

	public long updateVenue(Venue arg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, arg.getId());
		values.put(KEY_NAME, arg.getName());
		values.put(KEY_LATITUDE, arg.getLatitude());
		values.put(KEY_LONGITUDE, arg.getLongitude());
		values.put(KEY_ADDRESS, arg.getAddress());
		values.put(KEY_LAST_UPDATE_DATE, arg.getLastUpdateDate());

		return db.update(TABLE_VENUE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(arg.getId()) });
	}

	public long updateVenueMeta(VenueMeta arg)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, arg.getId());
		values.put(KEY_VENUE_ID, arg.getVenueId());
		values.put(KEY_TAG_ID, arg.getTagId());

		return db.update(TABLE_VENUE_META, values, KEY_ID + " = ?",
				new String[] { String.valueOf(arg.getId()) });
	}

	// UPDATE methods - end

	// DELETE methods

	public void deleteCategory(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CATEGORY, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	public void deleteQuestion(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CATEGORY, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	public void deleteTag(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TAG, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	public void deleteVenue(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_VENUE, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	public void deleteVenueMeta(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_VENUE_META, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	// DELETE methods - end

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_TABLE_CATEGORY);
		db.execSQL(CREATE_TABLE_OPTION);
		db.execSQL(CREATE_TABLE_QUESTION);
		db.execSQL(CREATE_TABLE_TAG);
		db.execSQL(CREATE_TABLE_VENUE);
		db.execSQL(CREATE_TABLE_VENUE_META);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTION);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENUE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENUE_META);
	}

}
