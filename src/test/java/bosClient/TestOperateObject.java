package bosClient;

import java.util.List;

import org.junit.Test;

import com.baidubce.services.bos.model.BosObjectSummary;

/**
 * ����Object(����������ɾ������ѯ���������鿴Object��URL)
 * 
 * @author Administrator
 * 
 */
public class TestOperateObject {
	/**
	 * <pre>
	 * �鿴bucket�е�Object�б�
	 * 1��Ĭ������£����Bucket�е�Object��������1000����ֻ�᷵��1000��Object�����ҷ��ؽ����IsTruncatedֵΪTrue��������NextMarker��Ϊ�´ζ�ȡ�����
	 * 2���������󷵻�Object����Ŀ������ʹ��Marker�����ִζ�ȡ
	 * </pre>
	 */
	@Test
	public void listObjects() {
		String bucketName = "binbinpictures";

		List<BosObjectSummary> listObjects = OperateObjectUtil.listObjects(bucketName);
		for (BosObjectSummary bosObjectSummary : listObjects) {
			System.out.println(bosObjectSummary.getETag());
		}
	}

	/**
	 * �鿴bucket�е�Object�б�(�߼�)
	 */
	@Test
	public void listObjectsAdvanced() {
		// ����ListObjectsRequest����
		String bucketName = "binbinpictures";

		List<BosObjectSummary> listObjectsAdvanced = OperateObjectUtil.listObjectsAdvanced(bucketName);
		for (BosObjectSummary bosObjectSummary : listObjectsAdvanced) {
			System.out.println(bosObjectSummary.getETag());
		}
	}

	/**
	 * ��ȡ����Object��URL
	 * 
	 * @return
	 */
	@Test
	public void generatePresignedUrl() {
		String bucketName = "binbinpictures";
		String objectKey = "test";

		String directory = OperateObjectUtil.generatePresignedUrlDirectory(bucketName, objectKey);
		System.out.println(directory);
	}

	/**
	 * ɾ��Object
	 */
	@Test
	public void deleteObject() {
		String bucketName = "binbinpictures";
		String objectKey = "xxxx";

		OperateObjectUtil.deleteObject(bucketName, objectKey);
	}

	/**
	 * ����Object
	 */
	@Test
	public void copyObject() {
		String srcBucketName = "";
		String srcKey = "";

		String destBucketName = "";
		String destKey = "";

		OperateObjectUtil.copyObject(srcBucketName, srcKey, destBucketName, destKey);
	}

	/**
	 * ����Object(�߼�)
	 */
	@Test
	public void copyObjectAdvanced() {
		String srcBucketName = "";
		String srcKey = "";

		String destBucketName = "";
		String destKey = "";

		OperateObjectUtil.copyObjectAdvanced(srcBucketName, srcKey, destBucketName, destKey);
	}
}
