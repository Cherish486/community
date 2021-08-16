package com.nowcoder.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;


@ContextConfiguration(classes = CommunityApplication.class)
@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStrings() {
        String redisKey = "test:count";

        redisTemplate.opsForValue().set(redisKey, 3);

        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));
    }

    @Test
    public void testHashes(){
        String redisKey = "test:user";
        redisTemplate.opsForHash().put(redisKey, "id", 1);
        redisTemplate.opsForHash().put(redisKey, "username", "zhangsan");

        System.out.println(redisTemplate.opsForHash().get(redisKey, "id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey, "username"));

    }

    @Test
    public void testList(){
        String redisKey = "test:ids";

        redisTemplate.opsForList().leftPush(redisKey, 101);
        redisTemplate.opsForList().leftPush(redisKey, 102);
        redisTemplate.opsForList().leftPush(redisKey, 103);

        System.out.println(redisTemplate.opsForList().size(redisKey));
        System.out.println(redisTemplate.opsForList().index(redisKey, 0));
        System.out.println(redisTemplate.opsForList().range(redisKey, 0, 2));

        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));

        System.out.println(redisTemplate.opsForList().size(redisKey));
    }

    @Test
    public void testSets(){
        String redisKey = "test:teachers";

        redisTemplate.opsForSet().add(redisKey, "刘备","关羽","赵云","张飞","诸葛亮");

        System.out.println(redisTemplate.opsForSet().size(redisKey));
        System.out.println(redisTemplate.opsForSet().pop(redisKey));
        System.out.println(redisTemplate.opsForSet().members(redisKey));

    }


    @Test
    public void testSortedSets(){
        String redisKey = "test:students";

        redisTemplate.opsForZSet().add(redisKey, "唐僧", 80);
        redisTemplate.opsForZSet().add(redisKey, "白龙马", 70);
        redisTemplate.opsForZSet().add(redisKey, "猪八戒", 60);
        redisTemplate.opsForZSet().add(redisKey, "孙悟空", 90);
        redisTemplate.opsForZSet().add(redisKey, "沙僧", 100);

        System.out.println(redisTemplate.opsForZSet().zCard(redisKey)); // 统计里面一共有多少个数据
        System.out.println(redisTemplate.opsForZSet().score(redisKey, "猪八戒"));  // 统计某一个人的分数
        System.out.println(redisTemplate.opsForZSet().rank(redisKey, "猪八戒")); // 统计某个人的排名，默认从小到大
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey, "猪八戒")); // 统计某个人的排名，从大到小
        System.out.println(redisTemplate.opsForZSet().range(redisKey, 0, 2)); // 从小到大取前三名
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, 2)); // 从大到小取前三名

    }

    @Test
    public void testKeys(){
        redisTemplate.delete("test:user");  // 删除键

        System.out.println(redisTemplate.hasKey("test:user"));  // 判断是否含有建

        redisTemplate.expire("test:students", 10, TimeUnit.SECONDS);  // 设置超时时间
    }


    // 多次访问一个key
    @Test
    public void testBoundOperation(){
        String redisKey = "test:count";

        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
        operations.increment();
        operations.increment();
        operations.increment();

        System.out.println(operations.get());
    }

    @Test
    public void testTransaction(){
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {

                String redisKey = "test:tx";

                operations.multi(); // 启用事务

                operations.opsForSet().add(redisKey, "张三");
                operations.opsForSet().add(redisKey, "李四");
                operations.opsForSet().add(redisKey, "王五");

                System.out.println(operations.opsForSet().members(redisKey));
                return operations.exec();  // 提交事务
            }
        });
        System.out.println(obj);
    }
}
