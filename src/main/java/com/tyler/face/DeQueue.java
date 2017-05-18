package com.tyler.face;

import java.util.List;
import java.util.Map;

/**
 * 出队列接口
 * Created by tyler on 2017/5/18.
 */
public interface DeQueue {
    /**
     * URL队列出队列
     * @param queueName 出队列的队列名
     * @return 队列中的URL
     */
    String urlDeQueue(String queueName);

    /**
     * 关系队列出队列
     * @param queueName 关系队列的名字
     * @return 队列中的URL关系集合
     */
    Map<String,List<String>> mapDeQueue(String queueName);

    /**
     * 销毁队列
     * @param queueName 待销毁队列的队列名
     */
    void destroyQueue(String queueName);
}
