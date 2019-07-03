import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author WuZhiWei
 * @since 2016-05-26 10:27
 */
public class JavaBat {
    public static void main(String[] args) {
        try {
            Runtime.getRuntime().exec("set CLASSPATH=D:\\ace\\framework\\framework-base\\target\\framework-base-1.0-SNAPSHOT.jar;%CLASSPATH%;");
            Runtime.getRuntime().exec("cd D:\\ace\\paas\\report\\report-api\\src\\main\\java\\com\\ace\\paas\\report\\accountAchievement\\domain");
            Process p = Runtime.getRuntime().exec("javac AccountAchievement.java");
            InputStream is = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine())!= null){
                System.out.println(line);
            }
            p.waitFor();
            is.close();
            reader.close();
            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
