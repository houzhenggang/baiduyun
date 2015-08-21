package bosClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import org.springframework.util.StringUtils;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectResponse;

/**
 * �ϴ�Object
 * 
 * @author Administrator
 * 
 */
public class BosUploadUtil {
	private static BosClient bosClient = BosClientUtil.getInstance();

	/**
	 * <pre>
	 * �ļ�·����ʽ�ϴ��ļ�
	 * 1��putObject����֧�ֲ�����5GB��Object�ϴ�
	 * 2��BOS����Header�з���Object��ETag��Ϊ�ļ���ʶ
	 * </pre>
	 * 
	 * @throws IOException
	 */
	public static String putObject(String bucketName, String directoryUnderBucket, String filePath) throws IOException {
		if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(directoryUnderBucket)
				|| StringUtils.isEmpty(filePath)) {
			return null;
		}

		if (!BosBucketUtil.doesBucketExist(bucketName)) {
			BosBucketUtil.createBucket(bucketName);
		}

		// ���ļ���ʽ�ϴ�Object
		File file = new File(filePath);
		PutObjectResponse putObjectResponse = bosClient.putObject(bucketName, directoryUnderBucket, file);
		String eTag = putObjectResponse.getETag();

		return eTag;
	}

	/**
	 * �ļ������ַ�����ʽ�ϴ��ļ�
	 * 
	 * @param bucketName
	 * @param directoryUnderBucket
	 * @param content
	 */
	public static String pubObjectByString(String bucketName, String directoryUnderBucket, String content) {
		if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(directoryUnderBucket)
				|| StringUtils.isEmpty(content)) {
			return null;
		}

		if (!BosBucketUtil.doesBucketExist(bucketName)) {
			BosBucketUtil.createBucket(directoryUnderBucket);
		}

		// ���ַ����ϴ�Object
		PutObjectResponse putObjectResponse = bosClient.putObject(bucketName, directoryUnderBucket, content);
		String eTag = putObjectResponse.getETag();

		return eTag;
	}

	/**
	 * ��������ʽ�ϴ��ļ�
	 * 
	 * @param bucketName
	 * @param directoryUnderBucket
	 * @param b
	 */
	public static String pubObject(String bucketName, String directoryUnderBucket, byte[] b) {
		if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(directoryUnderBucket) || b == null) {
			return null;
		}

		if (!BosBucketUtil.doesBucketExist(bucketName)) {
			BosBucketUtil.createBucket(bucketName);
		}

		// �Զ����ƴ��ϴ�Object��readֻ��һ�У��ڴ˽����ڲ���
		PutObjectResponse putObjectResponse = bosClient.putObject(bucketName, directoryUnderBucket, b);
		String eTag = putObjectResponse.getETag();

		return eTag;
	}

	/**
	 * ��������ʽ�ϴ��ļ�
	 * 
	 * @param bucketName
	 * @param directoryUnderBucket
	 * @param inputStream
	 */
	public static String pubObject(String bucketName, String directoryUnderBucket, InputStream inputStream) {
		if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(directoryUnderBucket) || inputStream == null) {
			return null;
		}

		if (!BosBucketUtil.doesBucketExist(bucketName)) {
			BosBucketUtil.createBucket(bucketName);
		}

		// ����������ʽ�ϴ�Object
		PutObjectResponse putObjectResponse = bosClient.putObject(bucketName, directoryUnderBucket, inputStream);
		String eTag = putObjectResponse.getETag();

		return eTag;
	}

	/**
	 * �ļ�·����ʽ�ϴ��ļ����߼���
	 * 
	 * @param bucketName
	 * @param directoryUnderBucket
	 * @param filePath
	 */
	public static String putObjectAdvanced(String bucketName, String directoryUnderBucket, String filePath) {
		if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(directoryUnderBucket)
				|| StringUtils.isEmpty(filePath)) {
			return null;
		}

		if (!BosBucketUtil.doesBucketExist(bucketName)) {
			BosBucketUtil.createBucket(bucketName);
		}

		File file = new File(filePath);
		ObjectMetadata metadata = initObjectMetadata();
		PutObjectResponse putObjectResponse = bosClient.putObject(bucketName, directoryUnderBucket, file, metadata);
		String eTag = putObjectResponse.getETag();

		return eTag;
	}

	/**
	 * ��ʼ���ϴ�������
	 * 
	 * @return
	 */
	private static ObjectMetadata initObjectMetadata() {
		// ��ʼ���ϴ�������
		ObjectMetadata metadata = new ObjectMetadata();

		// ����ContentLength��С
		metadata.setContentLength(1000);
		// �����Զ���Ԫ����name��ֵΪmy-data
		metadata.addUserMetadata("name", "my-data");
		// ����ContentType
		metadata.setContentType(MediaType.APPLICATION_JSON);

		return metadata;
	}
}