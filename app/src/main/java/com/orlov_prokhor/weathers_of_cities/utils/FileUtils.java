package com.orlov_prokhor.weathers_of_cities.utils;

import android.content.Context;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import timber.log.Timber;

/**
 * Created by orlov_proxor@mail.ru on 27.02.2018.
 */

public class FileUtils {

  public static String copyAssetFile(Context myContext, String srcAssetFileName,
                                     String dstFileName) {
    InputStream  myInput;
    OutputStream myOutput = null;
    try {
      //Open your local file as the input stream
      myInput = myContext.getAssets().open(srcAssetFileName);

      //Open the empty file as the output stream
      myOutput = new FileOutputStream(dstFileName);

      //transfer bytes from the inputfile to the outputfile
      byte[] buffer = new byte[1024];
      int    length;
      while ((length = myInput.read(buffer)) > 0) {
        myOutput.write(buffer, 0, length);
      }

      //Close the streams
      myOutput.flush();
      myOutput.close();
      myInput.close();
    } catch (Exception e) {
      if (myOutput != null) {
        try {
          myOutput.close();
        } catch (IOException e1) {
          //  e1.printStackTrace();
        }
      }
      return e.getLocalizedMessage();
    }
    return null;
  }


  public static String getApplicationDir(Context context) {
/*        if (android.os.Build.VERSION.SDK_INT >= 17 && android.os.Build.VERSION.SDK_INT<26) {
            return context.getApplicationInfo().dataDir;
        } else {
            return "/data/data/" + context.getPackageName();
        }
        */
    /*    PackageManager m = context.getPackageManager();
        String s = context.getPackageName();
        PackageInfo p = m.getPackageInfo(s, 0);
        s = p.applicationInfo.dataDir;*/
    return "/data/data/" + context.getPackageName() + "/";
    // return  context.getFilesDir().getParent();

  }

  public static Boolean fileExists(String fileName) {
    return (new File(fileName).exists());
  }

  public static void writeToFile(Context context, String fileName, String mValue) {

    String filename = null;

    filename = getApplicationDir(context) + fileName;// "/Timber.txt";

    File file = new File(filename);
    file.getParentFile().mkdirs();
    try (FileOutputStream oos = new FileOutputStream(file);
        Writer w = new OutputStreamWriter(oos, "UTF-8")) {
      PrintWriter pw = new PrintWriter(w);
      pw.println(mValue + "\n\n");
      pw.close();
    } catch (Exception e) {
      Timber.e(e);
    }

  }

  public static boolean makeDir(String fullDirName) {
    File    folder  = new File(fullDirName);
    boolean success = true;
    if (!folder.exists()) {
      success = folder.mkdirs();
    }
    return success;
  }

  public static Boolean logToFile(String fileName, String text) {

    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter(fileName, true);

      PrintWriter printWriter = new PrintWriter(fileWriter, true);
      printWriter.print(text);
      // printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
      printWriter.close();
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  public static Boolean deleteFile(String fileName) {
    File file = new File(fileName);

    return file.delete();

  }

  public static void close(Closeable closable) {
    if (closable != null) {
      try {
        closable.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
/**/