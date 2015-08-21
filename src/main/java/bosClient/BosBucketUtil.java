package bosClient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.BucketSummary;
import com.baidubce.services.bos.model.CannedAccessControlList;
import com.baidubce.services.bos.model.Grant;
import com.baidubce.services.bos.model.ListBucketsResponse;
import com.baidubce.services.bos.model.SetBucketAclRequest;

/**
 * bucket��������
 * 
 * @author Administrator
 * 
 */
public class BosBucketUtil {
	private static BosClient bosClient = BosClientUtil.getInstance();

	/**
	 * ����bucket
	 */
	public static void createBucket(String bucketName) {
		bosClient.createBucket(bucketName);
	}

	/**
	 * ��ȡ����bucket
	 */
	public static List<BucketSummary> getBuckets() {
		ListBucketsResponse listBucketsResponse = bosClient.listBuckets();
		if (listBucketsResponse == null) {
			return new ArrayList<BucketSummary>();
		}

		List<BucketSummary> buckets = listBucketsResponse.getBuckets();
		return buckets;
	}

	/**
	 * �ж�bucket�Ƿ����
	 */
	public static boolean doesBucketExist(String bucketName) {
		if (StringUtils.isEmpty(bucketName)) {
			return false;
		}

		boolean exists = bosClient.doesBucketExist(bucketName);
		return exists;
	}

	/**
	 * ɾ��bucket
	 * 
	 * @param client
	 * @param bucketName
	 */
	public static void deleteBucket(String bucketName) {
		if (!doesBucketExist(bucketName)) {
			return;
		}

		bosClient.deleteBucket(bucketName);
	}

	/**
	 * ����bucket����Ȩ��
	 * 
	 * @param client
	 * @param bucketName
	 */
	public static void setBucketPrivate(String bucketName, CannedAccessControlList acl) {
		if (!doesBucketExist(bucketName)) {
			return;
		}

		bosClient.setBucketAcl(bucketName, acl);
	}

	/**
	 * ָ���û��ķ���Ȩ��
	 * 
	 * @param client
	 */
	public static void SetBucketAclFromBody(String bucketName, List<Grant> grants) {
		if (!doesBucketExist(bucketName)) {
			return;
		}

		// List<Grant> grants = new ArrayList<Grant>();
		// List<Grantee> grantees = new ArrayList<Grantee>();
		// List<Permission> permissiones = new ArrayList<Permission>();
		//
		// // ��Ȩ���ض��û�
		// Grantee grantee = new Grantee("userId");
		// grantees.add(grantee);
		//
		// // ��Ȩ��Everyone
		// Grantee grantee_all = new Grantee("*");
		// grantees.add(grantee_all);
		//
		// // ����Ȩ��
		// permissiones.add(Permission.READ);
		// permissiones.add(Permission.WRITE);
		//
		// Grant grant = new Grant();
		// grant = grant.withGrantee(grantees);
		// grant = grant.withPermission(permissiones);
		// grants.add(grant);

		SetBucketAclRequest bucketAclRequest = new SetBucketAclRequest(bucketName, grants);
		bosClient.setBucketAcl(bucketAclRequest);
	}
}
