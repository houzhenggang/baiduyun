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
        String bucketName = "binbinpictures";
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
        String bucketName = "binbinpictures";
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
        String bucketName = "binbinpictures";
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
