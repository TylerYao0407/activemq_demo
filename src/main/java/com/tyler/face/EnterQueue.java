package com.tyler.face;

import java.util.List;
import java.util.Map;

/**
 * 入队列接口
 * Created by tyler on 2017/5/18.
 */
public interface EnterQueue {
    /**
     * URL入队列
     * @param queueName 队列名
     * @param url 进入队列的URL
     * @return 插入是否成功
     */
    boolean enterQueue(String queueName,String url);

    /**
     * URL关系入队列
     * @param queueName 队列名
     * @param urlMap 进入队列的url（一对多）
     * @return 插入是否成功
     */
    boolean enterQueue(String queueName, Map<String,List<String>> urlMap);
}
