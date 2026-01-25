package com.example.demo;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Demo00_03_WordCountStream_DataStream {
    private static final Logger LOGGER = LoggerFactory.getLogger(Demo00_03_WordCountStream_DataStream.class);

    @Test
    public void test() throws IOException {
        ServerSocket serverSocket = mockSocket();

        // 创建执行环境
        LOGGER.info("========> executionEnvironment");
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        // 读取数据：Socket
        LOGGER.info("========> dataStreamSource");
        DataStreamSource<String> dataStreamSource = executionEnvironment.socketTextStream("127.0.0.1", serverSocket.getLocalPort());

        // 处理数据：切换、转换、分组、聚合
        LOGGER.info("========> wordOperator");
        SingleOutputStreamOperator<Tuple2<String, Integer>> wordOperator = dataStreamSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
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
        KeyedStream<Tuple2<String, Integer>, String> wordGroup = wordOperator.keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> value) throws Exception {
                return value.getField(0); // 返回分组的标识，即按“单词”分组（二元组中下标为0）
            }
        });
        // 各分组内聚合
        LOGGER.info("========> wordSum");
        SingleOutputStreamOperator<Tuple2<String, Integer>> wordSum = wordGroup.sum(1);// 按"数量"聚合（二元组中下标为1）

        // 输出数据
        LOGGER.info("========> print");
        DataStreamSink<Tuple2<String, Integer>> print = wordSum.print();

        // 执行，类似sparkstreaming最后ssc.start()
        LOGGER.info("========> execute");
        try {
            // 输出：
            // 5> (hello,1)
            // 3> (java,1)
            // 3> (wold,1)
            // 13> (flink,1)
            // 5> (hello,2)
            // 5> (hello,3)
            executionEnvironment.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ServerSocket mockSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(0));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket accept = serverSocket.accept();
                    OutputStream outputStream = accept.getOutputStream();
                    outputStream.write("hello flink\n".getBytes());
                    outputStream.write("hello wold\n".getBytes());
                    outputStream.write("hello java\n".getBytes());
                    accept.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        return serverSocket;
    }
}
