package bosClient;

import org.junit.Test;

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
        String bucketName = "";
        
        OperateObjectUtil.listObjects(bucketName);
    }
    
    /**
     * �鿴bucket�е�Object�б�(�߼�)
     */
    @Test
    public void listObjectsAdvanced() {
        // ����ListObjectsRequest����
        String bucketName = "";
        
        OperateObjectUtil.listObjectsAdvanced(bucketName);
    }
    
    /**
     * ��ȡ����Object��URL
     * 
     * @return
     */
    @Test
    public String generatePresignedUrl() {
        String bucketName = "";
        String objectKey = "";
        int expirationInSeconds = 1800;
        
        return OperateObjectUtil.generatePresignedUrl(bucketName, objectKey, expirationInSeconds);
    }
    
    /**
     * ɾ��Object
     */
    @Test
    public void deleteObject() {
        String bucketName = "";
        String objectKey = "";
        
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
