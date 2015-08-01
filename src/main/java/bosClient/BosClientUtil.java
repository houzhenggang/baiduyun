package bosClient;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;

public class BosClientUtil {
    /** �û���Access Key ID */
    private static String ACCESS_KEY_ID = "c5f66ab5ad63445da2965fa894b2a983";
    
    /** �û���Secret Access Key */
    private static String SECRET_ACCESS_KEY = "7235e1484c6f46f28ed6b57ed868efe3";
    
    /** �ٶ��� BOS�ͻ��� */
    private volatile static BosClient bosClient;
    
    private BosClientUtil() {
        
    }
    
    /**
     * ��ʼ��bosClient
     * 
     * @return
     */
    public static BosClient getInstance() {
        if (bosClient == null) {
            synchronized (BosClientUtil.class) {
                if (bosClient == null) {
                    bosClient = initBosClient();
                }
            }
        }
        return bosClient;
    }
    
    private static BosClient initBosClient() {
        BosClientConfiguration config = new BosClientConfiguration();
        
        // ���ô���
        //        config.setProxyHost("127.0.0.1");
        //        config.setProxyPort(8080);
        //������Ҫ�û���֤�Ĵ���
        //        config.setProxyUsername("username");
        //        config.setProxyPassword("password");
        
        //�����������
        // ����HTTP���������Ϊ10
        //        config.setMaxConnections(10);
        // ����TCP���ӳ�ʱΪ5000����
        //        config.setConnectionTimeoutInMillis(5000);
        // ����Socket�������ݳ�ʱ��ʱ��Ϊ2000����
        //        config.setSocketTimeoutInMillis(5000);
        
        DefaultBceCredentials bceCredentials = new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
        config.setCredentials(bceCredentials);
        //        config.setEndpoint(ENDPOINT);
        
        bosClient = new BosClient(config);
        
        return bosClient;
    }
}
