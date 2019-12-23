package com.smallcake.zanghua;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText mEdtMsg;
    private CheckBox mChkIsLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDataBase();

        mEdtMsg = findViewById(R.id.edtMsg);
        mChkIsLong = findViewById(R.id.cekIsLong);

    }

    private void initDataBase() {
        AssetManager am = getAssets();
        try {
            InputStream inputStream = am.open("zanghua.db");
            FileOutputStream fileOutputStream = new FileOutputStream(getFilesDir() + "/zanghua.db");
            byte[] buf = new byte[1024];
            int len;

            while ((len = inputStream.read(buf)) != -1)
            {
                fileOutputStream.write(buf, 0, len);
            }

            inputStream.close();
            fileOutputStream.close();

        }
        catch (Exception e)
        {

        }
    }

    public void onClickCopy(View v){
        ClipboardManager cm = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", mEdtMsg.getText().toString());
        cm.setPrimaryClip(mClipData);
        Toast.makeText(this,"内容已复制",Toast.LENGTH_SHORT).show();
    }

    public void onClickGet(View v){
        String tableName = mChkIsLong.isChecked()?"max_1":"min_1";
        Random random = new Random();
        random.nextInt(100);

        ZanghuaDataBase zanghuaDataBase = new ZanghuaDataBase(this);
        SQLiteDatabase sqLiteDatabase = zanghuaDataBase.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT msg FROM "+ tableName +" ORDER BY (RANDOM() % (SELECT COUNT(*) FROM "+ tableName +")) limit 1",null);

        if (cursor.moveToNext())
        {
            mEdtMsg.setText(cursor.getString(0));
        }

        cursor.close();
        sqLiteDatabase.close();
        zanghuaDataBase.close();


    }
}
