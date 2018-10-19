package com.hadooptest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

public class WordCount {

    /* 指定输入输出路径，指的是HDFS上的根目录下的 */
    static String INPUT = "/hadoopdata";
    static String OUTPUT = "/output";

    /* 定义Map函数 */
    static class MyMappper extends Mapper<LongWritable, Text, Text, LongWritable> {
        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LongWritable>.Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] splited = line.split(" ");

            for (String word : splited) {
                context.write(new Text(word), new LongWritable(1));
            }
        }
    }

    /* 定义Reduce函数 */
    static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        @Override
        protected void reduce(Text key, Iterable<LongWritable> value, Reducer<Text, LongWritable, Text, LongWritable>.Context context) throws IOException, InterruptedException {
            Long sum = 0L;
            for (LongWritable c: value) {
                sum += c.get();
            }
            context.write(key, new LongWritable(sum));
        }
    }

    public static void main(String[] args) throws Exception {
        Job job = new Job(new Configuration(), WordCount.class.getSimpleName());
        job.setMapperClass(MyMappper.class);
        job.setReducerClass(MyReducer.class);
        job.setJarByClass(WordCount.class);

        /* 指定输出的格式 */
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        FileInputFormat.setInputPaths(job, INPUT);
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT));

        job.waitForCompletion(true);
    }
}
