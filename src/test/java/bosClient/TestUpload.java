package bosClient;

import java.io.IOException;

import org.junit.Test;

/**
 * �����ϴ�Object
 * 
 * @author Administrator
 * 
 */
public class TestUpload {
    /**
     * <pre>
     * �����ļ��ϴ�
     * 1��putObject����֧�ֲ�����5GB��Object�ϴ�
     * 2��BOS����Header�з���Object��ETag��Ϊ�ļ���ʶ
     * </pre>
     * 
     * @throws IOException
     */
    @Test
    public void putObject() throws IOException {
        String bucketName = "";
        String objectKey = "";
        
        UploadUtil.putObject(bucketName, objectKey);
    }
    
    /**
     * �����ļ��ϴ�
     */
    @Test
    public void putObjectAdvanced() {
        String bucketName = "";
        String objectKey = "";
        
        UploadUtil.putObjectAdvanced(bucketName, objectKey);
    }
}
