import com.example.bean.MyBatisPlusGenerateBean.TestMybatisPlusUser;
import com.example.mapperInterface.MyBatisPlusGenerateMapper.TestMybatisPlusUserMapper;
import com.example.springboot.SpringbootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 程刘德
 * @version 1.0
 * @Description TODO
 * @date 2019/10/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringbootApplication.class)
public class SampleTest {
    @Autowired
    private TestMybatisPlusUserMapper testMybatisPlusUserMapper;
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<TestMybatisPlusUser> userList = testMybatisPlusUserMapper.selectList(null);
        TestMybatisPlusUser user = new TestMybatisPlusUser();
        user.setName("Sandy");

        Map map = new HashMap();
        map.put("name" , "Sandy");

        List<TestMybatisPlusUser> userList2 = testMybatisPlusUserMapper.selectByMap(map);


//        Assert.assertEquals(5, userList.size());
        userList2.forEach(System.out::println);
    }
}
