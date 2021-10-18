package immutable.example1;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

public class UserInfo {
    // info字段本身的值不会改变,但是info字段所指向的实例的状态有可能改变
    private final StringBuffer info;

    public UserInfo(String name,String address) {
        this.info = new StringBuffer(name + ":" + address);
    }

    // StringBuffer包含修改内部状态的方法，所以info可能被外部修改
    // 所以UserInfo不是Immutable类
    public StringBuffer getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "info=" + info +
                '}';
    }
}
