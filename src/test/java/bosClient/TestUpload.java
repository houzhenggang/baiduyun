package bosClient;

import java.io.File;
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
		String bucketName = "cndwineapp";
		String directoryUnderBucket = "image";

		String filePath = "D:/zhengjiabin/work/֣�α�/��ҵ/temp/temp7/test.jpg";
		File file = new File(filePath);
		String suffix = BosDirectoryUtil.getFileSuffix(file.getAbsolutePath());
		directoryUnderBucket = directoryUnderBucket + "/test" + suffix;

		String directory = BosUploadUtil.putObject(bucketName, directoryUnderBucket, file);
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

		String directory = BosUploadUtil.putObjectAdvanced(bucketName, newFileName, filePath);
		System.out.println(directory);
	}
}
