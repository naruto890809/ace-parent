import com.ace.framework.base.BaseEntity;

import java.io.Serializable;

/**
 * @author WuZhiWei
 * @since 2016-05-25 16:32
 */
public class Test extends BaseEntity {
    private static final long serialVersionUID = 2100741068917318749L;
    private String a;
    private String b;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public static void main(String[] args) {
        String s=" d684b2e8b86c4e998ad9fd44df395b97";
        System.out.println(s.trim());
    }
}

