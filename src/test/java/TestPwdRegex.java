import org.junit.Test;

/**
 * 测试密码正则验证
 *
 * @author littleblackLB
 * @date 2020/3/13 14:36
 */
public class TestPwdRegex {

    @Test
    public void testPwdRegex() {
        String patten = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,16}$";
        System.out.println("Abc123".matches(patten));
    }
}
