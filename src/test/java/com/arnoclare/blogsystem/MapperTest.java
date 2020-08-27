package com.arnoclare.blogsystem;

import com.arnoclare.blogsystem.dao.StatisticMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Arno Clare
 * @date 10:28 2020/7/22
 */
@SpringBootTest
public class MapperTest {
    @Autowired
    StatisticMapper statisticMapper;

    @Test
    public void StatisticMapperTest() {
        System.out.println(statisticMapper.selectStatisticWithCommentId(10).getHits());
    }
}
