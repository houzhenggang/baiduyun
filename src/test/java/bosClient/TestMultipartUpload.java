package bosClient;

import java.io.IOException;

import org.junit.Test;

import com.baidubce.services.bos.model.InitiateMultipartUploadResponse;
import com.baidubce.services.bos.model.ListPartsRequest;
import com.baidubce.services.bos.model.ListPartsResponse;
import com.baidubce.services.bos.model.PartSummary;

/**
 * ���Էֿ��ϴ�
 * 
 * @author Administrator
 * 
 */
public class TestMultipartUpload {
    /**
     * <pre>
     * �ֿ��ϴ�Object
     * Ӧ�ó������£�
     * 1����Ҫ֧�ֶϵ��ϴ���
     * 2���ϴ�����5GB��С���ļ���
     * 3�����������ϲ��BOS�ķ�����֮������Ӿ����Ͽ���
     * 4����Ҫ��ʽ���ϴ��ļ���
     * 5���ϴ��ļ�֮ǰ���޷�ȷ���ϴ��ļ��Ĵ�С��
     * </pre>
     * 
     * @throws IOException
     */
    @Test
    public void multipartUpload() throws IOException {
        String bucketName = "";
        String objectKey = "";
        
        MultipartUpload.multipartUpload(bucketName, objectKey);
    }
    
    /**
     * ȡ���ֿ��ϴ�
     */
    @Test
    public void abortMultipartUpload() {
        String bucketName = "";
        String objectKey = "";
        
        MultipartUpload.abortMultipartUpload(bucketName, objectKey);
    }
    
    /**
     * ��ȡδ��ɵķֿ��ϴ��¼�
     * 
     */
    @Test
    public void listMultipartUploads() {
        String bucketName = "";
        
        MultipartUpload.listMultipartUploads(bucketName);
    }
    
    /**
     * ��ȡ�������ϴ��Ŀ���Ϣ
     * 
     */
    @Test
    public void listParts() {
        String bucketName = "";
        String objectKey = "";
        
        MultipartUpload.listParts(bucketName, objectKey);
    }
}
