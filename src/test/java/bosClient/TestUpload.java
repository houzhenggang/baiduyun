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
        String bucketName = "binbinpictures";
        String directoryUnderBucket = "photo/test";
        String filePath = "F:/����/soft/baiduyun.png";
        
        String eTag = BosUploadUtil.putObject(bucketName, directoryUnderBucket, filePath);
        System.out.println(eTag);
        
        String directory = OperateObjectUtil.generatePresignedUrlDirectory(bucketName, directoryUnderBucket);
        System.out.println(directory);
    }
    
    /**
     * �����ļ��ϴ�
     */
    @Test
    public void putObjectAdvanced() {
        String bucketName = "binbinpictures/pictures";
        String newFileName = "test";
        String filePath = "E:/�����ĵ�/��ҵ�ĵ�/temp/tupian.png";
        
        String eTag = BosUploadUtil.putObjectAdvanced(bucketName, newFileName, filePath);
        System.out.println(eTag);
    }
}
