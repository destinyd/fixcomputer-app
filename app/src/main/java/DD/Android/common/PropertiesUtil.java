package DD.Android.common;

import DD.Android.FixComputer.core.Settings;
import android.os.Environment;
import android.util.Log;
import com.alibaba.fastjson.JSON;

import java.io.*;


public class PropertiesUtil {
	static final String TAG = "PropertiesUtil";

	public static String readInStream(FileInputStream inStream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}
			outStream.close();
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
		}
		return null;
	}

	/**
	 * 1、判断SD卡是否存在
	 */
	public static boolean hasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static void writeConfiguration(String path,String file_name,Object c) {
		if (!hasSdcard())
			return;
        String sd_path = Environment
                .getExternalStorageDirectory() + path;
		String str_json = JSON.toJSONString(c);
		File file = new File(sd_path);
		if (!file.exists()) {
			file.mkdirs();
		}

		file = new File(sd_path, file_name);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				Log.d(TAG, "IOException");
				e.printStackTrace();
			}
		}

		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(str_json);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static <T> T readConfiguration(String path,String file_name,java.lang.Class<T> clazz) {
        String sd_path = Environment
                .getExternalStorageDirectory() + path;
		File file = new File(sd_path, file_name);
		String str_json = null;
		FileInputStream fi;
		try {
			fi = new FileInputStream(file);
			str_json = readInStream(fi);

			if (str_json.length() > 0) {
				return JSON.parseObject(str_json,clazz);
			}
		} catch (FileNotFoundException e) {
			Log.d(TAG, "read FileNotFoundException");
			e.printStackTrace();
		}

        return null;

	}
}
