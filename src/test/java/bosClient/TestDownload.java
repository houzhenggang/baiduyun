package bosClient;

import java.io.IOException;

import org.junit.Test;

/**
 * ��������Object
 * 
 * @author Administrator
 * 
 */
public class TestDownload {
    /**
     * ��ȡbucket�е�object
     * 
     * @throws IOException
     */
    @Test
    public void getObject() throws IOException {
        String bucketName = "binbinpictures";
        String objectKey = "test";
        
        DownloadUtil.getObject(bucketName, objectKey);
    }
    
    /**
     * ��ȡbucket�е�object(�߼�)
     * 
     * @throws IOException
     */
    @Test
    public void getObjectAdvanced() throws IOException {
        String bucketName = "";
        String objectKey = "";
        
        DownloadUtil.getObjectAdvanced(bucketName, objectKey);
    }
}
