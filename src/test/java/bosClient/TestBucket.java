package bosClient;

import org.junit.Test;

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
        String bucketName = "";
        BucketUtil.createBucket(bucketName);
    }
    
    /**
     * ����bucket
     */
    @Test
    public void getBuckets() {
        BucketUtil.getBuckets();
    }
    
    /**
     * �ж�bucket�Ƿ����
     */
    @Test
    public void doesBucketExist() {
        String bucketName = "test";
        BucketUtil.doesBucketExist(bucketName);
    }
    
    /**
     * ɾ��bucket
     * 
     * @param client
     * @param bucketName
     */
    @Test
    public void deleteBucket() {
        String bucketName = "test";
        BucketUtil.deleteBucket(bucketName);
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
        BucketUtil.setBucketPrivate(bucketName);
    }
    
    /**
     * ָ���û��ķ���Ȩ��
     * 
     * @param client
     */
    @Test
    public void SetBucketAclFromBody() {
        String bucketName = "";
        BucketUtil.SetBucketAclFromBody(bucketName);
    }
}
