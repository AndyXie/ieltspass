package tt.lab.android.ieltspass.data;

import android.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: hejind
 * Date: 3/1/14
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static DataBaseHelper mInstance = null;

    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "ieltspass"; //保存的数据库文件名
    public static final String PACKAGE_NAME = "tt.lab.android.ieltspass";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME + "/";
    public SQLiteDatabase db = null;
    private static Context context = null;

    //数据库名称
    public static final String DATABASE_NAME = "ieltspass";

    // 数据库版本号
    private static final int DATABASE_VERSION = 1;

    //数据库SQL语句 添加一个表
    private static final String NAME_TABLE_CREATE = "create table words(BE_phonetic_symbol text,AE_phonetic_symbol text,BE_sound text,AE_sound text,Cn_explanation text,En_explanation text,pic text,word_vocabulary text); ";

    DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        initData();
    }

    /**
     * 单例模式*
     */
    public static synchronized DataBaseHelper getInstance(Context context1) {
        context = context1;

        if (mInstance == null) {
            mInstance = new DataBaseHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // initData();
    }

    private void initData() {
        File file = new File(DB_PATH + DB_NAME);
        if (file.exists()) {
            db = openDatabase(DB_PATH + DATABASE_NAME);
        } else {
            deepFile(context, DB_NAME);
            db = openDatabase(DB_PATH + DATABASE_NAME);
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
/**可以拿到当前数据库的版本信息 与之前数据库的版本信息   用来更新数据库**/
    }


    /**
     * 删除数据库
     *
     * @param context
     * @return
     */
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DATABASE_NAME);
    }


    public void openDatabase() {

        System.out.println(DB_PATH + "/" + DB_NAME);

        this.db = this.openDatabase(DB_PATH + "/" + DB_NAME);

    }


    public SQLiteDatabase openDatabase(String dbfile) {


//        try {
//
//            if (!(new File(dbfile).exists())) {
//
//                //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
//
//                InputStream is = this.context.getResources().openRawResource(
//
//                        R.raw.ieltspass; //欲导入的数据库
//
//                FileOutputStream fos = new FileOutputStream(dbfile);
//
//                byte[] buffer = new byte[BUFFER_SIZE];
//
//                int count = 0;
//
//                while ((count = is.read(buffer)) > 0) {
//
//                    fos.write(buffer, 0, count);
//
//                }
//
//                fos.close();
//
//                is.close();
//
//            }


        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);

        return db;

//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        return null;

    }


    public void closeDatabase() {

        this.db.close();


    }

    public void deepFile(Context ctxDealFile, String path) {

        try {

            String str[] = ctxDealFile.getAssets().list(path);
            if (str.length > 0) {//如果是目录

                File file = new File(DB_PATH + path);

                file.mkdirs();

                for (String string : str) {

                    path = path + "/" + string;

                    System.out.println("path::::\t" + path);

                    // textView.setText(textView.getText()+"\t"+path+"\t");

                    deepFile(ctxDealFile, path);

                    path = path.substring(0, path.lastIndexOf('/'));

                }

            } else {//如果是文件

                InputStream is = ctxDealFile.getAssets().open(path);
                FileOutputStream fos = new FileOutputStream(new File(DB_PATH

                        + path));

                byte[] buffer = new byte[1024];

                int count = 0;

                while (true) {

                    count++;

                    int len = is.read(buffer);

                    if (len == -1) {

                        break;

                    }

                    fos.write(buffer, 0, len);

                }

                is.close();

                fos.close();

            }

        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }
    }

    public SQLiteDatabase getWordsDB() {
        return db;
    }
}
