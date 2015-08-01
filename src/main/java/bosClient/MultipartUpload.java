package bosClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.AbortMultipartUploadRequest;
import com.baidubce.services.bos.model.CompleteMultipartUploadRequest;
import com.baidubce.services.bos.model.CompleteMultipartUploadResponse;
import com.baidubce.services.bos.model.InitiateMultipartUploadRequest;
import com.baidubce.services.bos.model.InitiateMultipartUploadResponse;
import com.baidubce.services.bos.model.ListMultipartUploadsRequest;
import com.baidubce.services.bos.model.ListMultipartUploadsResponse;
import com.baidubce.services.bos.model.ListPartsRequest;
import com.baidubce.services.bos.model.ListPartsResponse;
import com.baidubce.services.bos.model.MultipartUploadSummary;
import com.baidubce.services.bos.model.PartETag;
import com.baidubce.services.bos.model.PartSummary;
import com.baidubce.services.bos.model.UploadPartRequest;
import com.baidubce.services.bos.model.UploadPartResponse;

/**
 * �ֿ��ϴ�����
 * 
 * @author Administrator
 * 
 */
public class MultipartUpload {
    private static BosClient bosClient = BosClientUtil.getInstance();
    
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
    public static void multipartUpload(String bucketName, String objectKey) throws IOException {
        //��ʼ���ֿ��ϴ����
        InitiateMultipartUploadResponse initiateMultipartUploadResponse = initiateMultipartUploadResponse(bucketName, objectKey);
        
        //��ʼ�ϴ�
        List<PartETag> partETags = startMultipartUpload(initiateMultipartUploadResponse);
        
        //��ɷֿ��ϴ�
        completeMultipartUpload(initiateMultipartUploadResponse, partETags);
    }
    
    /**
     * ��ʼ���ֿ��ϴ����
     */
    private static InitiateMultipartUploadResponse initiateMultipartUploadResponse(String bucketName, String objectKey) {
        // ��ʼ��multipart Upload
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, objectKey);
        InitiateMultipartUploadResponse initiateMultipartUploadResponse = bosClient.initiateMultipartUpload(initiateMultipartUploadRequest);
        
        // ��ӡUploadId
        System.out.println("UploadId: " + initiateMultipartUploadResponse.getUploadId());
        
        return initiateMultipartUploadResponse;
    }
    
    /**
     * <pre>
     * ���Էֿ��ϴ�Object
     * 
     * ע��㣺
     * 1��UploadPart ����Ҫ������һ��Part���⣬������Part��С��Ҫ����5MB��
     * 2������Upload Part�ӿڲ���������У���ϴ�Part�Ĵ�С��ֻ�е�Complete Multipart Upload��ʱ��Ż�У�顣
     * 3��Ϊ�˱�֤���������紫������в����ִ��󣬽����û����յ�BOS�ķ����������UploadPart���ص�Content-MD5ֵ��֤�ϴ����ݵ���ȷ�ԡ�
     * 4��Part����ķ�Χ��1~10000��������������Χ��BOS������InvalidArgument�Ĵ����롣
     * 5��ÿ���ϴ�Partʱ��Ҫ������λ���˴��ϴ��鿪ͷ����Ӧ��λ�á�
     * 6��ÿ���ϴ�Part֮��BOS�ķ��ؽ�������һ�� PartETag ���������ϴ����ETag����ţ�PartNumber������ϣ��ں�����ɷֿ��ϴ��Ĳ����л��õ����������Ҫ���䱣��������
     * 7��һ��������Щ PartETag ���󽫱����浽List��
     * </pre>
     * 
     * @param initiateMultipartUploadResponse �ֿ��ϴ��������
     * @throws IOException
     */
    private static List<PartETag> startMultipartUpload(InitiateMultipartUploadResponse initiateMultipartUploadResponse) throws IOException {
        // ����ÿ��Ϊ 5MB
        final long partSize = 1024 * 1024 * 5L;
        File partFile = new File("/path/to/file.zip");
        int partCount = getPartCount(partFile, partSize);
        
        // �½�һ��List����ÿ���ֿ��ϴ����ETag��PartNumber
        List<PartETag> partETags = new ArrayList<PartETag>();
        
        UploadPartResponse uploadPartResponse = null;
        for (int i = 0; i < partCount; i++) {
            uploadPartResponse = uploadPart(partFile, partSize, i, initiateMultipartUploadResponse);
            
            // �����ص�PartETag���浽List�С�
            partETags.add(uploadPartResponse.getPartETag());
        }
        
        return partETags;
    }
    
    /**
     * ÿ���ֿ���ϴ�
     * 
     * @param partFile
     * @param partSize
     * @param currentPart
     * @param initiateMultipartUploadResponse
     * @return
     * @throws IOException
     */
    private static UploadPartResponse uploadPart(File partFile, long partSize, int currentPart, InitiateMultipartUploadResponse initiateMultipartUploadResponse) throws IOException {
        // ����ÿ���ֿ�Ŀ�ͷ
        long skipBytes = partSize * currentPart;
        // ����ÿ���ֿ�Ĵ�С
        long size = partSize < partFile.length() - skipBytes ? partSize : partFile.length() - skipBytes;
        
        // ��ȡ�ļ���
        FileInputStream fis = new FileInputStream(partFile);
        fis.skip(skipBytes);
        
        // ����UploadPartRequest���ϴ��ֿ�
        UploadPartRequest uploadPartRequest = new UploadPartRequest();
        uploadPartRequest.setBucketName(initiateMultipartUploadResponse.getBucketName());
        uploadPartRequest.setKey(initiateMultipartUploadResponse.getKey());
        uploadPartRequest.setUploadId(initiateMultipartUploadResponse.getUploadId());
        uploadPartRequest.setInputStream(fis);
        uploadPartRequest.setPartSize(size);
        uploadPartRequest.setPartNumber(currentPart + 1);
        UploadPartResponse uploadPartResponse = bosClient.uploadPart(uploadPartRequest);
        
        // �ر��ļ�
        fis.close();
        
        return uploadPartResponse;
    }
    
    /**
     * ��ȡ�ֿ���Ŀ
     * 
     * @param partFile
     * @return
     */
    private static int getPartCount(File partFile, long partSize) {
        int partCount = (int)(partFile.length() / partSize);
        if (partFile.length() % partSize != 0) {
            partCount++;
        }
        
        return partCount;
    }
    
    /**
     * ��ɷֿ��ϴ�
     * 
     * @throws IOException
     */
    private static void completeMultipartUpload(InitiateMultipartUploadResponse initiateMultipartUploadResponse, List<PartETag> partETags) throws IOException {
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(initiateMultipartUploadResponse.getBucketName(), initiateMultipartUploadResponse.getKey(), initiateMultipartUploadResponse.getUploadId(), partETags);
        
        // ��ɷֿ��ϴ�
        CompleteMultipartUploadResponse completeMultipartUploadResponse = bosClient.completeMultipartUpload(completeMultipartUploadRequest);
        
        // ��ӡObject��ETag
        System.out.println(completeMultipartUploadResponse.getETag());
    }
    
    /**
     * ȡ���ֿ��ϴ�
     */
    public static void abortMultipartUpload(String bucketName, String objectKey) {
        //��ʼ���ֿ��ϴ����
        InitiateMultipartUploadResponse initiateMultipartUploadResponse = initiateMultipartUploadResponse(bucketName, objectKey);
        AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest(bucketName, objectKey, initiateMultipartUploadResponse.getUploadId());
        
        // ȡ���ֿ��ϴ�
        bosClient.abortMultipartUpload(abortMultipartUploadRequest);
    }
    
    /**
     * ��ȡδ��ɵķֿ��ϴ��¼�
     * 
     * @param bucketName
     */
    public static void listMultipartUploads(String bucketName) {
        ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest(bucketName);
        
        // ��ȡBucket�������ϴ��¼�
        ListMultipartUploadsResponse listing = bosClient.listMultipartUploads(listMultipartUploadsRequest);
        
        // ���������ϴ��¼�
        for (MultipartUploadSummary multipartUpload : listing.getMultipartUploads()) {
            System.out.println("Key: " + multipartUpload.getKey() + " UploadId: " + multipartUpload.getUploadId());
        }
    }
    
    /**
     * ��ȡ�������ϴ��Ŀ���Ϣ
     * 
     * @param bucketName
     * @param objectKey
     */
    public static void listParts(String bucketName, String objectKey) {
        //��ʼ���ֿ��ϴ����
        InitiateMultipartUploadResponse initiateMultipartUploadResponse = initiateMultipartUploadResponse(bucketName, objectKey);
        
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, objectKey, initiateMultipartUploadResponse.getUploadId());
        
        // ��ȡ�ϴ�������Part��Ϣ
        ListPartsResponse partListing = bosClient.listParts(listPartsRequest);
        
        // ��������Part
        for (PartSummary part : partListing.getParts()) {
            System.out.println("PartNumber: " + part.getPartNumber() + " ETag: " + part.getETag());
        }
    }
}
