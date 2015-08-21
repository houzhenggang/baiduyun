package bosClient;

import java.io.IOException;
import java.util.Properties;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;

public class BosClientUtil {

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

	/**
	 * ��ʼ��BOS����
	 * 
	 * @return
	 */
	private static BosClient initBosClient() {
		// ��ʼ�����Զ���
		Properties prop = initProperties();
		// ��ʼ�����ö���
		BosClientConfiguration config = initBosClientConfiguration(prop);

		bosClient = new BosClient(config);
		return bosClient;
	}

	/**
	 * ���ðٶ���BOS���ö���
	 * 
	 * @param properties
	 * @return
	 */
	private static BosClientConfiguration initBosClientConfiguration(Properties prop) {
		BosClientConfiguration config = new BosClientConfiguration();

		// ���ô���
		setProxy(config, prop);
		// ������Ҫ�û���֤�Ĵ���
		setProxyByValidate(config, prop);
		// �����������
		setNetwork(config, prop);

		// ���õ�¼��Ϣ
		setCredentials(config, prop);
		// ����������Ϣ
		setEndpoint(config, prop);

		return config;
	}

	/**
	 * ��ȡ�ٶ���BOS��������
	 * 
	 * @return
	 */
	private static Properties initProperties() {
		Properties prop = new Properties();
		try {
			prop.load(BosClientUtil.class.getResourceAsStream("/baiduBos.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param config
	 * @param prop
	 */
	private static void setEndpoint(BosClientConfiguration config, Properties prop) {
		String setEndpoint = prop.getProperty("setEndpoint");
		if (!Boolean.parseBoolean(setEndpoint)) {
			return;
		}

		/** �û��Լ�ָ�������� */
		String ENDPOINT = prop.getProperty("ENDPOINT");
		config.setEndpoint(ENDPOINT);
	}

	/**
	 * ���õ�¼��Ϣ
	 * 
	 * @param config
	 * @param prop
	 */
	private static void setCredentials(BosClientConfiguration config, Properties prop) {
		/** �û���Access Key ID */
		String ACCESS_KEY_ID = prop.getProperty("ACCESS_KEY_ID");
		/** �û���Secret Access Key */
		String SECRET_ACCESS_KEY = prop.getProperty("SECRET_ACCESS_KEY");

		DefaultBceCredentials bceCredentials = new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
		config.setCredentials(bceCredentials);
	}

	/**
	 * �����������
	 * 
	 * @param config
	 * @param prop
	 */
	private static void setNetwork(BosClientConfiguration config, Properties prop) {
		String setNetwork = prop.getProperty("setNetwork");
		if (!Boolean.parseBoolean(setNetwork)) {
			return;
		}

		// ����HTTP���������Ϊ10
		String maxConnections = prop.getProperty("maxConnections");
		config.setMaxConnections(Integer.parseInt(maxConnections));

		// ����TCP���ӳ�ʱΪ5000����
		String connectionTimeoutInMillis = prop.getProperty("connectionTimeoutInMillis");
		config.setConnectionTimeoutInMillis(Integer.parseInt(connectionTimeoutInMillis));

		// ����Socket�������ݳ�ʱ��ʱ��Ϊ2000����
		String socketTimeoutInMillis = prop.getProperty("socketTimeoutInMillis");
		config.setSocketTimeoutInMillis(Integer.parseInt(socketTimeoutInMillis));
	}

	/**
	 * ������Ҫ�û���֤�Ĵ���
	 * 
	 * @param config
	 * @param prop
	 */
	private static void setProxyByValidate(BosClientConfiguration config, Properties prop) {
		String setProxyByValidate = prop.getProperty("setProxyByValidate");
		if (!Boolean.parseBoolean(setProxyByValidate)) {
			return;
		}

		String proxyUsername = prop.getProperty("proxyUsername");
		config.setProxyUsername(proxyUsername);

		String proxyPassword = prop.getProperty("proxyPassword");
		config.setProxyPassword(proxyPassword);
	}

	/**
	 * ���ô���
	 * 
	 * @param config
	 * @param prop
	 */
	private static void setProxy(BosClientConfiguration config, Properties prop) {
		String setProxy = prop.getProperty("setProxy");
		if (!Boolean.parseBoolean(setProxy)) {
			return;
		}

		String proxyHost = prop.getProperty("proxyHost");
		config.setProxyHost(proxyHost);

		String proxyPort = prop.getProperty("proxyPort");
		config.setProxyPort(Integer.parseInt(proxyPort));
	}
}
