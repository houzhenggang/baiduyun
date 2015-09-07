package bosClient;

import java.io.IOException;
import java.util.Properties;

import com.baidubce.services.bos.BosClient;

public class BosDirectoryUtil {
	private static final String defaultEndpoint = "http://bj.bcebos.com";
	private static Properties prop;

	static {
		prop = new Properties();
		try {
			prop.load(BosClientUtil.class.getResourceAsStream("/baiduBos.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ�ļ�����׺
	 * 
	 * @param fileAbsolutePath
	 */
	public static String getFileSuffix(String fileAbsolutePath) {
		if (fileAbsolutePath == null || fileAbsolutePath.length() <= 0) {
			return null;
		}

		int lastIndexOf = fileAbsolutePath.lastIndexOf(".");
		String suffix = fileAbsolutePath.substring(lastIndexOf, fileAbsolutePath.length());
		return suffix;
	}

	/**
	 * �ļ�·���淶��
	 * 
	 * @param directory
	 * @return
	 */
	public static String normalDirectory(String directory) {
		if (directory == null || directory.length() <= 0) {
			return null;
		}

		String newDirectory = directory.replaceAll("\\\\", "/");
		return newDirectory;
	}

	/**
	 * <pre>
	 * ��ȡ�ϴ�·��
	 * ��δ�������������·����http://bj.bcebos.com/v1/binbinpictures/photo/test.png
	 * ��bucketName��binbinpictures,directoryUnderBucket��photo/test.png��
	 * 
	 * ���������������·����http://binbinpictures.bj.bcebos.com/photo/test.png
	 * </pre>
	 */
	public static String getBosDirectory(String bucketName, String directoryUnderBucket) {
		if (bucketName == null || bucketName.length() <= 0 || directoryUnderBucket == null
				|| directoryUnderBucket.length() <= 0) {
			return null;
		}

		String setEndpoint = prop.getProperty("setEndpoint");

		if (Boolean.parseBoolean(setEndpoint)) {
			return getBosDirectoryByEndpoint(bucketName, directoryUnderBucket);
		} else {
			return getBosDirectoryByDefalutEndpoint(bucketName, directoryUnderBucket);
		}
	}

	private static String getBosDirectoryByEndpoint(String bucketName, String directoryUnderBucket) {
		String ENDPOINT = prop.getProperty("ENDPOINT");
		if (defaultEndpoint.equals(ENDPOINT)) {
			return getBosDirectoryByDefalutEndpoint(bucketName, directoryUnderBucket);
		} else {
			return getBosDirectoryBySpecialEndpoint(bucketName, directoryUnderBucket);
		}
	}

	/**
	 * ��ȡָ�������ķ���·��
	 * 
	 * @param bucketName
	 * @param directoryUnderBucket
	 * @return
	 */
	private static String getBosDirectoryBySpecialEndpoint(String bucketName, String directoryUnderBucket) {
		String ENDPOINT = prop.getProperty("ENDPOINT");
		StringBuffer url = new StringBuffer(ENDPOINT);
		url.append("/");
		url.append(BosClient.URL_PREFIX);
		url.append("/");
		url.append(bucketName);
		url.append("/");
		url.append(directoryUnderBucket);

		return url.toString();
	}

	/**
	 * ��ȡĬ�ϵķ���·��
	 * 
	 * @param bucketName
	 * @param directoryUnderBucket
	 * @return
	 */
	private static String getBosDirectoryByDefalutEndpoint(String bucketName, String directoryUnderBucket) {
		StringBuffer url = new StringBuffer(defaultEndpoint);
		url.append("/");
		url.append(BosClient.URL_PREFIX);
		url.append("/");
		url.append(bucketName);
		url.append("/");
		url.append(directoryUnderBucket);

		return url.toString();
	}
}
