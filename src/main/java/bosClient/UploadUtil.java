package bosClient;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.core.MediaType;

import bean.BaiduBos;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectResponse;

/**
 * �ϴ�Object
 * 
 * @author Administrator
 * 
 */
public class UploadUtil {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
    /**
     * <pre>
     * �����ļ��ϴ�
     * 1��putObject����֧�ֲ�����5GB��Object�ϴ�
     * 2��BOS����Header�з���Object��ETag��Ϊ�ļ���ʶ
     * </pre>
     * 
     * @throws IOException
     */
    public static void putObject(BaiduBos bos) throws IOException {
        // ���ļ���ʽ�ϴ�Object
        String filePath = bos.getFilePath();
        File file = new File(filePath);
        
        String bucketName = bos.getBucketName();
        String newFileName = bos.getNewFileName();
        PutObjectResponse putObjectFromFileResponse = bosClient.putObject(bucketName, newFileName, file);
        System.out.println(putObjectFromFileResponse.getETag());
        
        // ����������ʽ�ϴ�Object
        //        InputStream inputStream = new FileInputStream(file);
        //        PutObjectResponse putObjectResponseFromInputStream = bosClient.putObject(bucketName, newFileName, inputStream);
        //        System.out.println(putObjectResponseFromInputStream.getETag());
        
        // �Զ����ƴ��ϴ�Object��readֻ��һ�У��ڴ˽����ڲ���
        //        byte[] b = new byte[] {};
        //        inputStream.read(b);
        //        PutObjectResponse putObjectResponseFromByte = bosClient.putObject(bucketName, newFileName, b);
        //        System.out.println(putObjectResponseFromByte.getETag());
        
        // ���ַ����ϴ�Object
        //        String content = "";
        //        PutObjectResponse putObjectResponseFromString = bosClient.putObject(bucketName, newFileName, content);
        //        System.out.println(putObjectResponseFromString.getETag());
    }
    
    /**
     * �����ļ��ϴ�
     */
    public static void putObjectAdvanced(BaiduBos bos) {
        String filePath = bos.getFilePath();
        File file = new File(filePath);
        
        ObjectMetadata metadata = initObjectMetadata();
        
        String bucketName = bos.getBucketName();
        String newFileName = bos.getNewFileName();
        bosClient.putObject(bucketName, newFileName, file, metadata);
    }
    
    /**
     * ��ʼ���ϴ�������
     * 
     * @return
     */
    private static ObjectMetadata initObjectMetadata() {
        // ��ʼ���ϴ�������
        ObjectMetadata metadata = new ObjectMetadata();
        
        // ����ContentLength��С
        metadata.setContentLength(1000);
        // �����Զ���Ԫ����name��ֵΪmy-data
        metadata.addUserMetadata("name", "my-data");
        // ����ContentType
        metadata.setContentType(MediaType.APPLICATION_JSON);
        
        return metadata;
    }
}
