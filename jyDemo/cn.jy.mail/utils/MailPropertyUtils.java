package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Auther: 69013
 * @Date: 2018/11/21 17:18
 * @Description: 获取邮箱配置文件信息
 */
public class MailPropertyUtils {
    private static final Logger logger = LoggerFactory.getLogger(MailPropertyUtils.class);
    private static Properties props;
    static{
        loadProps();
    }

    synchronized static private void loadProps(){
        props = new Properties();
        InputStream in = null;
        try {
            //in = PropertyUtil.class.getClassLoader().getResourceAsStream("param.properties");
            in = MailPropertyUtils.class.getResourceAsStream("/mail.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("邮箱配置文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("邮箱配置文件流关闭出现异常");
            }
        }

    }

    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
