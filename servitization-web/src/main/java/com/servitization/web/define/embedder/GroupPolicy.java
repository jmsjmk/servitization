package com.servitization.web.define.embedder;

/**
 * 池化政策
 */
public enum GroupPolicy {


    /**
     * 安全严谨政策：
     * <p>
     * 1、池化大小维持在给定的size，超出部分进行排队。
     * 2、池中任务空闲超过1分钟后，开始回收，回收率100%。
     * 3、单个任务超时后，以不通过为结果。
     */
    SECURE,


    /**
     * 开放高吞吐政策：
     * <p>
     * 1、池化大小一般情况下维持在给定大小的60%，最多不超过给定size。超过部分降级，以通过为结果。
     * 2、单个任务超时后，以通过为结果。
     */
    OPEN
}
