package bosClient;

import java.net.URL;

import javax.ws.rs.core.MediaType;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.CopyObjectRequest;
import com.baidubce.services.bos.model.CopyObjectResponse;
import com.baidubce.services.bos.model.ListObjectsRequest;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.baidubce.services.bos.model.ObjectMetadata;

/**
 * ����Object����
 * 
 * @author Administrator
 * 
 */
public class OperateObjectUtil {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
    /**
     * <pre>
     * �鿴bucket�е�Object�б�
     * 1��Ĭ������£����Bucket�е�Object��������1000����ֻ�᷵��1000��Object�����ҷ��ؽ����IsTruncatedֵΪTrue��������NextMarker��Ϊ�´ζ�ȡ�����
     * 2���������󷵻�Object����Ŀ������ʹ��Marker�����ִζ�ȡ
     * </pre>
     */
    public static void listObjects(String bucketName) {
        // ��ȡָ��Bucket�µ�����Object��Ϣ
        ListObjectsResponse listing = bosClient.listObjects(bucketName);
        
        // ��������Object
        for (BosObjectSummary objectSummary : listing.getContents()) {
            System.out.println("ObjectKey: " + objectSummary.getKey());
        }
    }
    
    /**
     * �鿴bucket�е�Object�б�(�߼�)
     */
    public static void listObjectsAdvanced(String bucketName) {
        // ����ListObjectsRequest����
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        
        // ���ò���
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setMarker("123");
        listObjectsRequest.setPrefix("xx");
        listObjectsRequest.setMaxKeys(1000);
        
        ListObjectsResponse listing = bosClient.listObjects(listObjectsRequest);
        
        // ��������Object
        for (BosObjectSummary objectSummary : listing.getContents()) {
            System.out.println("ObjectKey: " + objectSummary.getKey());
        }
    }
    
    /**
     * ��ȡ����Object��URL
     * 
     * @return
     */
    public static String generatePresignedUrl(String bucketName, String objectKey, int expirationInSeconds) {
        URL url = bosClient.generatePresignedUrl(bucketName, objectKey, expirationInSeconds);
        
        return url.toString();
    }
    
    /**
     * ɾ��Object
     */
    public static void deleteObject(String bucketName, String objectKey) {
        bosClient.deleteObject(bucketName, objectKey);
    }
    
    /**
     * ����Object
     */
    public static void copyObject(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        // ����Object
        CopyObjectResponse copyObjectResponse = bosClient.copyObject(srcBucketName, srcKey, destBucketName, destKey);
        // ��ӡ���
        System.out.println("ETag: " + copyObjectResponse.getETag() + " LastModified: " + copyObjectResponse.getLastModified());
    }
    
    /**
     * ����Object(�߼�)
     */
    public static void copyObjectAdvanced(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        // ����CopyObjectRequest����
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(srcBucketName, srcKey, destBucketName, destKey);
        
        // �����µ�Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        // ����ContentLength��С
        metadata.setContentLength(1000);
        // �����Զ���Ԫ����name��ֵΪmy-data
        metadata.addUserMetadata("name", "my-data");
        // ����ContentType
        metadata.setContentType(MediaType.APPLICATION_JSON);
        
        copyObjectRequest.setNewObjectMetadata(metadata);
        
        // ����Object
        CopyObjectResponse copyObjectResponse = bosClient.copyObject(copyObjectRequest);
        
        System.out.println("ETag: " + copyObjectResponse.getETag() + " LastModified: " + copyObjectResponse.getLastModified());
    }
}
