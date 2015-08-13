package bosClient;

import java.io.IOException;

import org.junit.Test;

import bean.BaiduBos;

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
        BaiduBos bos = new BaiduBos();
        bos.setBucketName("binbinpictures");
        bos.setNewFileName("test");
        
        UploadUtil.putObject(bos);
    }
    
    /**
     * �����ļ��ϴ�
     */
    @Test
    public void putObjectAdvanced() {
        BaiduBos bos = new BaiduBos();
        bos.setBucketName("binbinpictures");
        bos.setNewFileName("test");
        
        UploadUtil.putObjectAdvanced(bos);
    }
}
