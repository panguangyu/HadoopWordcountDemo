# hadoopWordcountDemo
第一个Hadoop的WordCount DEMO程序
# hadoopWordcountDemo
第一个Hadoop的WordCount DEMO程序

步骤：

1、在IntelliJ Idea中将WordCount.java打包成jar包，并上传到本地目录如 /home/panguangyu/wordcount.jar

2、在hdfs上传README.txt作为wordCount的计算文件，

> bin/hadoop fs -mkdir /hadoopdata
> bin/hadoop fs -copyFromLocal /usr/local/hadoop/README.txt /hadoopdata

3、启动wordcount计算程序

> bin/hadoop jar /home/panguangyu/wordcount.jar

4、查看hdfs上的输出文件，输出在output目录下

> bin/hadoop fs -cat /output/part-r-00000

5、若要重新运行程序，并输出到output，则需要删除output目录，再执行

> bin/hadoop fs -rm -r /output
