package bosClient;

import java.util.ArrayList;
import java.util.List;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.BucketSummary;
import com.baidubce.services.bos.model.CannedAccessControlList;
import com.baidubce.services.bos.model.Grant;
import com.baidubce.services.bos.model.Grantee;
import com.baidubce.services.bos.model.Permission;
import com.baidubce.services.bos.model.SetBucketAclRequest;

/**
 * bucket��������
 * 
 * @author Administrator
 * 
 */
public class BucketUtil {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
    /**
     * ����bucket
     */
    public static void createBucket(String bucketName) {
        bosClient.createBucket(bucketName);
    }
    
    /**
     * ����bucket
     */
    public static void getBuckets() {
        // ��ȡ�û���Bucket�б�
        List<BucketSummary> buckets = bosClient.listBuckets().getBuckets();
        
        // ����Bucket
        for (BucketSummary bucket : buckets) {
            System.out.println(bucket.getName());
        }
    }
    
    /**
     * �ж�bucket�Ƿ����
     */
    public static boolean doesBucketExist(String bucketName) {
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
        bosClient.deleteBucket(bucketName);
    }
    
    /**
     * ����bucket����Ȩ��
     * 
     * @param client
     * @param bucketName
     */
    public static void setBucketPrivate(String bucketName) {
        bosClient.setBucketAcl(bucketName, CannedAccessControlList.Private);
    }
    
    /**
     * ָ���û��ķ���Ȩ��
     * 
     * @param client
     */
    public static void SetBucketAclFromBody(String bucketName) {
        List<Grant> grants = new ArrayList<Grant>();
        List<Grantee> grantees = new ArrayList<Grantee>();
        List<Permission> permissiones = new ArrayList<Permission>();
        
        //��Ȩ���ض��û�
        Grantee grantee = new Grantee("userId");
        grantees.add(grantee);
        
        //��Ȩ��Everyone
        Grantee grantee_all = new Grantee("*");
        grantees.add(grantee_all);
        
        //����Ȩ��
        permissiones.add(Permission.READ);
        permissiones.add(Permission.WRITE);
        
        Grant grant = new Grant();
        grant = grant.withGrantee(grantees);
        grant = grant.withPermission(permissiones);
        grants.add(grant);
        
        SetBucketAclRequest bucketAclRequest = new SetBucketAclRequest(bucketName, grants);
        bosClient.setBucketAcl(bucketAclRequest);
    }
}
