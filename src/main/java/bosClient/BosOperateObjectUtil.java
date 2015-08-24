package bosClient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.MediaType;

import org.springframework.util.StringUtils;

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
public class BosOperateObjectUtil {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
    private static Properties prop;
    
    static {
        prop = new Properties();
        try {
            prop.load(BosOperateObjectUtil.class.getResourceAsStream("/baiduBos.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * <pre>
     * �鿴bucket�е�Object�б�
     * 1��Ĭ������£����Bucket�е�Object��������1000����ֻ�᷵��1000��Object�����ҷ��ؽ����IsTruncatedֵΪTrue��������NextMarker��Ϊ�´ζ�ȡ�����
     * 2���������󷵻�Object����Ŀ������ʹ��Marker�����ִζ�ȡ
     * </pre>
     */
    public static List<BosObjectSummary> listObjects(String bucketName) {
        if (StringUtils.isEmpty(bucketName)) {
            return new ArrayList<BosObjectSummary>();
        }
        
        // ��ȡָ��Bucket�µ�����Object��Ϣ
        ListObjectsResponse listObjectsResponse = bosClient.listObjects(bucketName);
        if (listObjectsResponse == null) {
            return new ArrayList<BosObjectSummary>();
        }
        
        List<BosObjectSummary> contents = listObjectsResponse.getContents();
        return contents;
    }
    
    /**
     * �鿴bucket�е�Object�б�(�߼�)
     */
    public static List<BosObjectSummary> listObjectsAdvanced(String bucketName) {
        if (StringUtils.isEmpty(bucketName)) {
            return new ArrayList<BosObjectSummary>();
        }
        
        // ����ListObjectsRequest����
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        
        // ���ò���
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setMarker("123");
        listObjectsRequest.setPrefix("xx");
        listObjectsRequest.setMaxKeys(1000);
        
        ListObjectsResponse listObjectsResponse = bosClient.listObjects(listObjectsRequest);
        if (listObjectsResponse == null) {
            return new ArrayList<BosObjectSummary>();
        }
        
        List<BosObjectSummary> contents = listObjectsResponse.getContents();
        return contents;
    }
    
    /**
     * ��ȡ����Object��URL
     * 
     * @param bucketName
     * @param objectKey
     * @param expirationInSeconds
     * @return
     */
    public static URL generatePresignedUrl(String bucketName, String objectKey, int expirationInSeconds) {
        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(objectKey)) {
            return null;
        }
        
        URL url = bosClient.generatePresignedUrl(bucketName, objectKey, expirationInSeconds);
        return url;
    }
    
    /**
     * ��ȡ����Object��URL
     * 
     * @return
     */
    public static String generatePresignedUrlDirectory(String bucketName, String objectKey, int expirationInSeconds) {
        URL url = generatePresignedUrl(bucketName, objectKey, expirationInSeconds);
        if (url == null) {
            return null;
        }
        
        return url.toString();
    }
    
    /**
     * ��ȡ����Object��URL
     * 
     * @return
     */
    public static String generatePresignedUrlDirectory(String bucketName, String objectKey) {
        int expirationInSeconds = Integer.parseInt(prop.getProperty("expirationInSeconds"));
        URL url = generatePresignedUrl(bucketName, objectKey, expirationInSeconds);
        if (url == null) {
            return null;
        }
        
        return url.toString();
    }
    
    /**
     * ɾ��Object
     */
    public static void deleteObject(String bucketName, String objectKey) {
        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(objectKey)) {
            return;
        }
        
        try {
            bosClient.deleteObject(bucketName, objectKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * ����Object
     */
    public static String copyObject(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        if (StringUtils.isEmpty(srcBucketName) || StringUtils.isEmpty(srcKey) || StringUtils.isEmpty(destBucketName) || StringUtils.isEmpty(destKey)) {
            return null;
        }
        
        // ����Object
        CopyObjectResponse copyObjectResponse = bosClient.copyObject(srcBucketName, srcKey, destBucketName, destKey);
        if (copyObjectResponse == null) {
            return null;
        }
        
        String eTag = copyObjectResponse.getETag();
        
        return eTag;
    }
    
    /**
     * ����Object(�߼�)
     */
    public static String copyObjectAdvanced(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        if (StringUtils.isEmpty(srcBucketName) || StringUtils.isEmpty(srcKey) || StringUtils.isEmpty(destBucketName) || StringUtils.isEmpty(destKey)) {
            return null;
        }
        
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
        if (copyObjectResponse == null) {
            return null;
        }
        
        String eTag = copyObjectResponse.getETag();
        
        return eTag;
    }
}
