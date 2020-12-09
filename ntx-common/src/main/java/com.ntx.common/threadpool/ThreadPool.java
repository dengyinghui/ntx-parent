package com.ntx.common.threadpool;

import java.util.concurrent.*;

/**
 * @description 线程池工具类
 * @author dengyinghui
 * @date 2019/11/26 20:09
 * @version 1.0.0.1
 */
public class ThreadPool {

    private static ThreadPoolExecutor executorService;

    /**
     * 核心线程数
     * Runtime.getRuntime().availableProcessors()返回虚拟机的最大可用的处理器数量;决不会小于一个
     * 处理的任务:
     * IO密集型任务(数据库数据交互、文件上传下载、网络数据传输等等)CORE_POOL_SIZE 取 2 * Runtime.getRuntime().availableProcessors()
     * 计算密集型任务(复杂算法)CORE_POOL_SIZE 取 Runtime.getRuntime().availableProcessors()
     */
    private static int CORE_POOL_SIZE = 2 * Runtime.getRuntime().availableProcessors();

    /**
     * 线程池中允许的最大线程数
     * 当线程数量大于队列长度时,此时线程池会创建新的线程
     * 如果当前线程数小于队列长度时,此时线程池只会维护核心线程数数量的线程
     */
    private static int MAXIMUM_POOL_SIZE = 200;

    /**
     * 线程池中空闲线程等待工作的超时时间
     * 当线程池中线程数量大于corePoolSize(核心线程数)或者线程设置了allowCoreThreadTimeOut(是否允许空闲核心线程超时)时,该参数就会生效
     * 线程会根据keepAliveTime的值进行活性检查，一旦超时便销毁线程
     * 数值单位：
     * TimeUnit.DAYS;               //天
     * TimeUnit.HOURS;             //小时
     * TimeUnit.MINUTES;           //分钟
     * TimeUnit.SECONDS;           //秒
     * TimeUnit.MILLISECONDS;      //毫秒
     * TimeUnit.MICROSECONDS;      //微妙
     * TimeUnit.NANOSECONDS;       //纳秒
     *
     */
    private static long KEEP_ALIVE_TIME = 5L;

    /**
     * 任务阻塞队列
     * 当线程数量超过核心线程数(此时核心线程都在处理任务)时,会把进来的线程任务放到该队列中
     * 如果队列中的任务超过了队列长度,则线程池会新建线程(新建的线程数量不会超过MAXIMUM_POOL_SIZE设置值)
     */
    private static LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();

    /**
     * 线程创建工程
     */
    private ThreadFactory threadFactory = Executors.defaultThreadFactory();


    /**
     * 拒绝策略：当线程数量大于任务阻塞队列的长度时就会调用拒绝策略处理超出的线程
     * 可取值：
     * 1、rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();   //ThreadPoolExecutor的默认拒绝策略,直接抛出异常
     * 2、rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();  //会调用当前线程池的所在的线程去执行被拒绝的任务
     * 3、rejectedExecutionHandler = new ThreadPoolExecutor.DiscardPolicy(); //线程直接被抛弃，不会抛异常也不会执行
     * 4、rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();   //抛弃任务队列中最旧的任务也就是最先加入队列的，再把这个新任务添加进去
     * 5、自定义拒绝策略:实现RejectedExecutionHandler接口,重写rejectedExecution方法
     */
    private RejectedExecutionHandler rejectedExecutionHandler = new MyRejectedExecutionHandler();


    private ThreadPool(){
        init();
    }


    /**
     * @description 线程池初始化
     * @return void
     * @author dengyinghui
     * @date 2019/11/27 10:19
     * @version 1.0.0.1
     */
    private void init(){
        executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, linkedBlockingQueue,
                threadFactory, rejectedExecutionHandler);
    }


    /**
     * @description 静态内部类实现单例
     * @author dengyinghui
     * @date 2019/11/26 20:32
     * @version 1.0.0.1
     */
    private static class ThreadPoolHolder{
        private static ThreadPool threadPool = new ThreadPool();
    }

    /**
     * @description 获取线程池对象
     * @return com.example.demo.ThreadPoolUtil
     * @author dengyinghui
     * @date 2019/11/26 20:31
     * @version 1.0.0.1
     */
    public static ThreadPoolExecutor getInstance(){
        return ThreadPoolHolder.threadPool.executorService;
    }


    public static void main(String[] args) throws Exception {

    }

}
