package bosClient;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.baidubce.services.bos.model.CannedAccessControlList;
import com.baidubce.services.bos.model.Grant;
import com.baidubce.services.bos.model.Grantee;
import com.baidubce.services.bos.model.Permission;

/**
 * ����bucket
 * 
 * @author Administrator
 * 
 */
public class TestBucket {
	/**
	 * ����bucket
	 */
	@Test
	public void createBucket() {
		String bucketName = "binbinpictures";
		BosBucketUtil.createBucket(bucketName);
	}

	/**
	 * ����bucket
	 */
	@Test
	public void getBuckets() {
		BosBucketUtil.getBuckets();
	}

	/**
	 * �ж�bucket�Ƿ����
	 */
	@Test
	public void doesBucketExist() {
		String bucketName = "binbinpictures";
		BosBucketUtil.doesBucketExist(bucketName);
	}

	/**
	 * ɾ��bucket
	 * 
	 * @param client
	 * @param bucketName
	 */
	@Test
	public void deleteBucket() {
		String bucketName = "photo";
		BosBucketUtil.deleteBucket(bucketName);
	}

	/**
	 * ����bucket����Ȩ��
	 * 
	 * @param client
	 * @param bucketName
	 */
	@Test
	public void setBucketPrivate() {
		String bucketName = "test";
		BosBucketUtil.setBucketPrivate(bucketName, CannedAccessControlList.Private);
	}

	/**
	 * ָ���û��ķ���Ȩ��
	 * 
	 * @param client
	 */
	@Test
	public void SetBucketAclFromBody() {
		String bucketName = "";

		List<Grant> grants = new ArrayList<Grant>();
		List<Grantee> grantees = new ArrayList<Grantee>();
		List<Permission> permissiones = new ArrayList<Permission>();

		// ��Ȩ���ض��û�
		Grantee grantee = new Grantee("userId");
		grantees.add(grantee);

		// ��Ȩ��Everyone
		Grantee grantee_all = new Grantee("*");
		grantees.add(grantee_all);

		// ����Ȩ��
		permissiones.add(Permission.READ);
		permissiones.add(Permission.WRITE);

		Grant grant = new Grant();
		grant = grant.withGrantee(grantees);
		grant = grant.withPermission(permissiones);
		grants.add(grant);

		BosBucketUtil.SetBucketAclFromBody(bucketName, grants);
	}
}
