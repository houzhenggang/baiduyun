package bosClient;

import java.io.IOException;
import java.util.Properties;

import org.springframework.util.StringUtils;

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
	 * ��ȡbucket�µ�·��
	 * 
	 * @param bucketName
	 * @param filePath
	 * @return
	 */
	public static String getDirectoryUnderBucket(String bucketName, String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return null;
		}
		if (StringUtils.isEmpty(bucketName)) {
			return filePath;
		}

		int index = filePath.indexOf(bucketName);
		String directoryUnderBucket = filePath.substring(index + bucketName.length() + 1, filePath.length());
		return directoryUnderBucket;
	}

	/**
	 * ��ȡ�ļ�����׺
	 * 
	 * @param fileAbsolutePath
	 */
	public static String getFileSuffix(String fileAbsolutePath) {
		if (StringUtils.isEmpty(fileAbsolutePath)) {
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
		if (StringUtils.isEmpty(directory)) {
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
		if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(directoryUnderBucket)) {
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
