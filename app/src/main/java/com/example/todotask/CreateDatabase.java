package com.example.todotask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.todotask.Model.TagsModel;
import com.example.todotask.Model.Task;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateDatabase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Demo001.db";
    private static final int DATABASE_VERSION =1;
    private static final String TB_TASK = "TASK";
    private static final String TB_TAG = "TAG";

    private static final String TB_TASK_NAME = "TASK_NAME";
    private static final String TB_TASK_DATE = "TASK_DATE";
    private static final String TB_TASK_ID = "TASK_ID";

    private static final String TB_TASK_REPEAT="TASK_REPEAT";
    private static final String TB_TASK_START = "TASK_START";
    private static final String TB_TASK_END = "TASK_END";

    private static final String TB_TASK_TAG = "TAG_ID";
    private static final String TB_TASK_NOTIFICATION = "TASK_NOTIFICATION";

    private static final String TB_TASK_CHECK= "TASK_CHECKED";
    public static final String TB_TAG_NAME = "TAG_NAME";
    public static final String TB_TAG_ID = "TAG_ID";
    public static final String TB_TAG_ICON = "TAG_ICON";
    public static final String TB_TAG_COLOR = "TAG_COLOR";
    //    public static int TB_TAG_ICON = Integer.parseInt("TAG_ICON");
//    public static int TB_TAG_COLOR = Integer.parseInt("TAG_COLOR");
    public CreateDatabase(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String tbTask = "CREATE TABLE IF NOT EXISTS " + TB_TASK + " ("
                + TB_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_TASK_NAME + " TEXT, "
                + TB_TASK_DATE + " TEXT, "
                + TB_TASK_START + " TEXT, "
                + TB_TASK_END + " TEXT, "
                + TB_TASK_REPEAT + " TEXT, "
                + TB_TASK_TAG + " INTEGER, "
                + TB_TASK_NOTIFICATION + " TEXT, "
                + TB_TASK_CHECK + " INTEGER, "
                + "FOREIGN KEY (" + TB_TASK_TAG + ") REFERENCES " + TB_TAG + "(" + TB_TAG_ID + ") ON DELETE CASCADE)";
        db.execSQL(tbTask);

        String tbTag = "CREATE TABLE IF NOT EXISTS " + TB_TAG + " ("
                + TB_TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_TAG_NAME + " TEXT, "
                + TB_TAG_ICON + " INTEGER, "
                + TB_TAG_COLOR + " INTEGER)";
        db.execSQL(tbTag);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_TASK);
        db.execSQL("DROP TABLE IF EXISTS " + TB_TAG);
        onCreate(db);
    }

    public void insertSampleData() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra nếu bảng đã có dữ liệu thì không thêm nữa
        if (isTableEmpty(db, TB_TASK) && isTableEmpty(db, TB_TAG)) {

            // Thêm dữ liệu mẫu cho bảng Tags
            insertTag(db, "EAT", 2131230912, -354250);
            insertTag(db, "LAUNDRY", 2131230914, -2455817);
            insertTag(db, "GYM", 2131230921, -7944457);
            insertTag(db, "READING", 2131230918, -555347);
            insertTag(db, "STUDY", 2131230919, -7952905);

            // Thêm dữ liệu mẫu cho bảng Tasks
            insertTask(db, "LUNCH", "10/12/2024", "12:00", "12:20",
                    "Do not repeat", 1, "1", "test");
            insertTask(db, "Do laundry", "10/12/2024", "6:00", "6:30",
                    "Do not repeat", 2, "1", "test");
            insertTask(db, "GYM", "10/12/2024", "05:30", "06:00",
                    "Do not repeat", 3, "1", "test");
            insertTask(db, "READ BOOKS", "10/12/2024", "18:30", "19:00",
                    "Do not repeat", 4, "1", "test");
            insertTask(db, "EXAM", "11/12/2024", "09:30", "10:30",
                    "Do not repeat", 5, "1", "test");
        }
        db.close();

    }
    // Hàm kiểm tra bảng có rỗng hay không
    private boolean isTableEmpty(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        boolean isEmpty = false;
        if (cursor.moveToFirst()) {
            isEmpty = cursor.getInt(0) == 0;
        }
        cursor.close();
        return isEmpty;
    }
    private void insertTask(SQLiteDatabase db, String name, String date, String start, String end,
                            String repeat, int tagId, String check, String notification) {
        ContentValues values = new ContentValues();
        values.put(TB_TASK_NAME, name);
        values.put(TB_TASK_DATE, date);
        values.put(TB_TASK_START, start);
        values.put(TB_TASK_END, end);
        values.put(TB_TASK_REPEAT, repeat);
        values.put(TB_TAG_ID, tagId);
        values.put(TB_TASK_CHECK, check);
        values.put(TB_TASK_NOTIFICATION, notification);
        db.insert(TB_TASK, null, values);
    }
    private void insertTag(SQLiteDatabase db, String name, int icon, int color) {
        ContentValues values = new ContentValues();
        values.put(TB_TAG_NAME, name);
        values.put(TB_TAG_ICON, icon);
        values.put(TB_TAG_COLOR, color);
        db.insert(TB_TAG, null, values);
    }
    public ArrayList<TagsModel> getAllTag(){
        ArrayList<TagsModel> list= new ArrayList<>();
        String sql = "SELECT * FROM " + TB_TAG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor != null)
            while (cursor.moveToNext()){
                TagsModel tagsModel = new TagsModel(cursor.getInt(0),
                        cursor.getString(1), cursor.getInt(2),
                        cursor.getInt(3));
                list.add(tagsModel);
            }
        return list;
    }
    public void addTag(String Name, int Icon, int Color){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //  contentValues.put(TB_TAG_ID,Id);
        contentValues.put(TB_TAG_NAME,Name);
        contentValues.put(TB_TAG_ICON,Icon);
        contentValues.put(TB_TAG_COLOR,Color);
        long result =  db.insert(TB_TAG,null,contentValues);
//        //CHI DE CHECK
//        if (result == -1){
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//        }
        db.close();

    }
    //
    public void UpdateTag (int id, String Name, int Icon, int Color ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_TAG_NAME, Name);
        contentValues.put(TB_TAG_ICON, Icon);
        contentValues.put(TB_TAG_COLOR, Color);
        long result = db.update(TB_TAG, contentValues, "TAG_ID = ?",new String[]{String.valueOf(id)});
//        if (result == -1){
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//        }
        db.close();

    }
    public void deleteTag(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Delete From " + TB_TAG + " Where TAG_ID=" + id;
        db.delete(TB_TAG, TB_TAG_ID + "=?",new String[]{String.valueOf(id)});
        db.execSQL(sql);
        db.close();
    }

    public TagsModel getIdTag(int id){
        TagsModel tagsModel = new TagsModel();
        String sql = "SELECT * FROM " + TB_TAG + " WHERE TAG_ID = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor != null)
            while (cursor.moveToNext()){
                tagsModel = new TagsModel(cursor.getInt(0),
                        cursor.getString(1), cursor.getInt(2),
                        cursor.getInt(3));

            }
        return tagsModel;
    }
    //Task

    public ArrayList<Task> getAllTask(){
        ArrayList<Task> list= new ArrayList<>();
        String sql = "SELECT * FROM " + TB_TASK + " WHERE TASK_CHECKED = " + 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor != null)
            while (cursor.moveToNext()){
                Task task = new Task(cursor.getInt(0),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3)
                        ,cursor.getString(4 ),cursor.getString(5), cursor.getInt(6),
                        cursor.getString(7), cursor.getInt(8));
                list.add(task);
            }
        return list;
    }

    public ArrayList<Task> getAllTaskinBin(){
        ArrayList<Task> list= new ArrayList<>();
        int checked = 0;
        String sql = "SELECT * FROM " + TB_TASK + " WHERE TASK_CHECKED = "+ checked ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor != null)
            while (cursor.moveToNext()){
                Task task = new Task(cursor.getInt(0),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3)
                        ,cursor.getString(4 ),cursor.getString(5), cursor.getInt(6),
                        cursor.getString(7) , cursor.getInt(8));
                list.add(task);
            }
        return list;
    }
    public void addTask(String NameTask,
                        String Date, String TimeStart,
                        String TimeEnd, String Repeat,
                        int Tag, String Notification, int checked){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //  contentValues.put(TB_TAG_ID,Id);
        contentValues.put(TB_TASK_NAME,NameTask);
        contentValues.put(TB_TASK_DATE,Date);
        contentValues.put(TB_TASK_START,TimeStart);
        contentValues.put(TB_TASK_END,TimeEnd);
        contentValues.put(TB_TASK_REPEAT,Repeat);
        contentValues.put(TB_TASK_TAG, Tag);
        contentValues.put(TB_TASK_NOTIFICATION,Notification);
        contentValues.put(TB_TASK_CHECK, checked);

        long result =  db.insert(TB_TASK,null,contentValues);
        //CHI DE CHECK
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
        db.close();

    }
    //co the gop ham
    public void PutToBin(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_TASK_CHECK, 0);
        long result = db.update(TB_TASK, contentValues, "TASK_ID = ?",new String[]{String.valueOf(id)});
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public void Putback(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_TASK_CHECK, 1);
        long result = db.update(TB_TASK, contentValues, "TASK_ID = ?",new String[]{String.valueOf(id)});
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public void UpdateTask(int id, Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //  contentValues.put(TB_TAG_ID,Id);
        contentValues.put(TB_TASK_NAME,task.getNameTask());
        contentValues.put(TB_TASK_DATE,task.getDate());
        contentValues.put(TB_TASK_START,task.getTimeStart());
        contentValues.put(TB_TASK_END,task.getTimeEnd());
        contentValues.put(TB_TAG_ID, task.getTag());
        contentValues.put(TB_TASK_NOTIFICATION,task.getNotification());
        long result = db.update(TB_TASK, contentValues, "TASK_ID = ?",new String[]{String.valueOf(id)});
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    //delete
    public void deleteTask(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Delete From " + TB_TASK + " Where TASK_ID=" + id;
        db.delete(TB_TASK, TB_TASK_ID + "=?",new String[]{String.valueOf(id)});
        db.execSQL(sql);
        db.close();
    }
    //delete all task
    public void deleteAllTask(int checked)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Delete From " + TB_TASK + " Where TASK_CHECKED = " + checked;
        db.delete(TB_TASK, TB_TASK_ID + "=?",new String[]{String.valueOf(checked)});
        db.execSQL(sql);
        db.close();
    }
    //lay ngay
    public ArrayList<Task> getDateTask(String date){
        ArrayList<Task> list= new ArrayList<>();
        int checked  = 1;
        String sql = "SELECT * FROM " + TB_TASK + " WHERE TASK_DATE = '" + date + "'" + " AND TASK_CHECKED =  " + checked  ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor != null)
            while (cursor.moveToNext()){
                Task task = new Task(cursor.getInt(0),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3)
                        ,cursor.getString(4 ),cursor.getString(5), cursor.getInt(6),
                        cursor.getString(7),  cursor.getInt(8));
                list.add(task);
            }
//        if (list.size() == 0 )  Toast.makeText(context, "You dont have task this day", Toast.LENGTH_SHORT).show();
//       else  Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
      return list;
    }
    //repreating day
    public void addRepeatingTask(String NameTask,
                                 String Date, String TimeStart,
                                 String TimeEnd, String Repeat,
                                 int Tag, String Notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        int count=0;
        int month=0;
        switch (Repeat) {
            case "Do not repeat":
                addTask(NameTask,Date, TimeStart,TimeEnd, Repeat,Tag, Notification , 1);
                break;
            case "Daily":
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.setTime(CalendarHelper.parseDate(Date));
                for (int i = 0; i < 7; i++) {
                    // Calculate the date for each week after the start date
                    Calendar nextDate = (Calendar) startCalendar.clone();
                    nextDate.add(Calendar.DAY_OF_MONTH, i);
                    month= startCalendar.get(Calendar.MONTH)+2;
                    count++;
                    if (nextDate.get(Calendar.MONTH) != startCalendar.get(Calendar.MONTH)) {
                        // count++;
                        break; // If not, stop adding tasks
                    }
                    // Convert the next date to a string
                    String nextDateString = CalendarHelper.formatDate(nextDate.getTime());
                    // Add the task for the next date
                    addTask(NameTask,nextDateString, TimeStart,TimeEnd, Repeat,Tag, Notification, 1);
                }
                for (int i = 0; i <= 7-count; i++) {
                    String next = "0"+(i+1)+"/";
                    if(month<9) next+="0"+ month+"/";
                    else next+= ""+month+"/";
                    next+= startCalendar.get(Calendar.YEAR);
                    addTask(NameTask,next, TimeStart,TimeEnd, Repeat,Tag, Notification, 1);
                }
                break;
            case "Weekly":
                count=0;
                month=0;
                int day=0;
                startCalendar = Calendar.getInstance();
                startCalendar.setTime(CalendarHelper.parseDate(Date));
                // Add the task for the selected date
                addTask(NameTask, Date, TimeStart, TimeEnd, Repeat, Tag, Notification, 1);
                month= startCalendar.get(Calendar.MONTH)+2;
                for (int i = 0; i < 4; i++) { // Repeat for 4 weeks
                    // Calculate the date for each week after the start date
                    Calendar nextDate = (Calendar) startCalendar.clone();
                    nextDate.add(Calendar.DAY_OF_MONTH, 7 * (i + 1)); // Add 7 days for each week
                    count++;
                    // Check if the next date falls in the same month as the start date
                    if (nextDate.get(Calendar.MONTH) != startCalendar.get(Calendar.MONTH)) {
                        day = nextDate.get(Calendar.DAY_OF_MONTH);
                        break; // If not, stop adding tasks
                    }

                    // Convert the next date to a string
                    String nextDateString = CalendarHelper.formatDate(nextDate.getTime());

                    // Add the task for the next date
                    addTask(NameTask, nextDateString, TimeStart, TimeEnd, Repeat, Tag, Notification, 1);
                }
                for (int i = 0; i <= 3-count; i++) {
                    String next="";
                    if(day<10)  next+="0"+(day);
                    else next+= ""+(day);
                    day+=7;
                    if(month<9) next+="/0"+ month+"/";
                    else next+= "/"+month+"/";
                    next+= startCalendar.get(Calendar.YEAR);
                    addTask(NameTask,next, TimeStart,TimeEnd, Repeat,Tag, Notification, 1);
                }
                break;
            case "Monthly":
                startCalendar = Calendar.getInstance();
                startCalendar.setTime(CalendarHelper.parseDate(Date));
                for (int i = 0; i < 12; i++) {
                    // Calculate the date for each week after the start date
                    Calendar nextDate = (Calendar) startCalendar.clone();
                    nextDate.add(Calendar.MONTH, i);

                    // Convert the next date to a string
                    String nextDateString = CalendarHelper.formatDate(nextDate.getTime());
                    // Add the task for the next date
                    addTask(NameTask,nextDateString, TimeStart,TimeEnd, Repeat,Tag, Notification, 1);
                }
                break;
            case "Annually":
                startCalendar = Calendar.getInstance();
                startCalendar.setTime(CalendarHelper.parseDate(Date));
                for (int i = 0; i < 5; i++) {
                    // Calculate the date for each week after the start date
                    Calendar nextDate = (Calendar) startCalendar.clone();
                    nextDate.add(Calendar.YEAR, i);
                    // Convert the next date to a string
                    String nextDateString = CalendarHelper.formatDate(nextDate.getTime());
                    // Add the task for the next date
                    addTask(NameTask,nextDateString, TimeStart,TimeEnd, Repeat,Tag, Notification, 1);
                }
                break;
        }
        db.close();
    }
    public String getTaskfromTag(int id){
        TagsModel tagsModel = null; // Initialize tagsModel to null
        String sql = "SELECT "+ TB_TAG + ".TAG_ID, TAG_NAME, TAG_ICON, TAG_COLOR  FROM " + TB_TASK + " JOIN " + TB_TAG +
                " ON " + TB_TASK + ".TAG_ID = " + TB_TAG + ".TAG_ID " + // Corrected table alias
                " WHERE " + TB_TAG + ".TAG_ID = " + id; // Changed to column name id_tag
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor != null && cursor.moveToFirst()) {
            tagsModel = new TagsModel(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3));
            cursor.close();
        }
        return (tagsModel != null) ? tagsModel.getTagContent() : null;
    }
}