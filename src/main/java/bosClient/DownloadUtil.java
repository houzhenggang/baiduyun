package bosClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.BosObject;
import com.baidubce.services.bos.model.GetObjectRequest;
import com.baidubce.services.bos.model.ObjectMetadata;

/**
 * ����Object
 * 
 * @author Administrator
 * 
 */
public class DownloadUtil {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
    /**
     * ��ȡbucket�е�object
     * 
     * @throws IOException
     */
    public static void getObject(String bucketName, String objectKey) throws IOException {
        // ��ȡObject�����ؽ��ΪBosObject����
        BosObject object = bosClient.getObject(bucketName, objectKey);
        // ��ȡObjectMeta
        ObjectMetadata meta = object.getObjectMetadata();
        System.out.println(meta.getETag());
        // ��ȡObject��������
        InputStream objectContent = object.getObjectContent();
        System.out.println(objectContent.read());
        
        // ��ȡObject���ļ���
        File file = new File("/path/to/file");
        bosClient.getObject(bucketName, objectKey, file);
        
        //ֻ��ȡObjectMetadata
        ObjectMetadata objectMetadata = bosClient.getObjectMetadata(bucketName, objectKey);
        System.out.println(objectMetadata.getContentType());
        
        // �ر���
        objectContent.close();
    }
    
    /**
     * ��ȡbucket�е�object(�߼�)
     * 
     * @throws IOException
     */
    public static void getObjectAdvanced(String bucketName, String objectKey) throws IOException {
        // �½�GetObjectRequest
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objectKey);
        // ��ȡ0~100�ֽڷ�Χ�ڵ�����
        getObjectRequest.setRange(0, 100);
        
        // ��ȡObject�����ؽ��ΪBosObject����
        BosObject object = bosClient.getObject(getObjectRequest);
        // ��ȡObjectMeta
        ObjectMetadata meta = object.getObjectMetadata();
        System.out.println(meta.getETag());
        // ��ȡObject��������
        InputStream objectContent = object.getObjectContent();
        System.out.println(objectContent.read());
        
        // ��ȡObject���ļ���
        File file = new File("/path/to/file");
        bosClient.getObject(getObjectRequest, file);
        
        // �ر���
        objectContent.close();
    }
}
