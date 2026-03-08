package com.example.demo;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读文件（有界流 by DataSet）
 * Note: 不推荐DataSet，推荐DataStream
 */
public class Demo00_01_WordCountBatch_DataSet {
    private static final Logger LOGGER = LoggerFactory.getLogger(Demo00_01_WordCountBatch_DataSet.class);

    /**
     * 基于DataSet API对数据按数据集方式进行处理转换。
     * <br>
     * p.s.
     * 实际上，从Flink 1.12开始，官方已统一流批处理接口，可以直接使用DataStream API编写代码（从而只维护一份代码）。
     * 如果想按批处理执行，则只需在提交任务时通过将执行模式设置为BATCH来进行批处理：
     * <pre>$ bin/flink run -Dexecution.runtime-mode=BATCH BatchWordCount.jar</pre>
     */
    @Test
    public void test() {
        // 创建执行环境
        LOGGER.info("========> executionEnvironment");
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

        // 读取数据：从文件读取
        LOGGER.info("========> dataSource");
        DataSource<String> dataSource = executionEnvironment.readTextFile("data/word.txt");

        // 切分、转换 => （单词，数量） e.g.（word，1）
        LOGGER.info("========> wordOperator");
        FlatMapOperator<String, Tuple2<String, Integer>> wordOperator = dataSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> collector) throws Exception {
                // 按照空格切分单词
                String[] words = value.split(" ");
                for (String word : words) {
                    // 二元组
                    Tuple2<String, Integer> tuple = Tuple2.of(word, 1);
                    // 向下游发送数据
                    collector.collect(tuple);
                }
            }
        });
        // 按照word分组
        LOGGER.info("========> wordGroup");
        UnsortedGrouping<Tuple2<String, Integer>> wordGroup = wordOperator.groupBy(0); // 按“单词”分组（二元组中下标为0）
        // 各分组内聚合
        LOGGER.info("========> wordSum");
        AggregateOperator<Tuple2<String, Integer>> wordSum = wordGroup.sum(1); // 按"数量"聚合（二元组中下标为1）

        // 输出
        LOGGER.info("========> print");
        try {
            // 输出：
            // (wold,1)
            // (flink,1)
            // (hello,3)
            // (java,1)
            wordSum.print();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
