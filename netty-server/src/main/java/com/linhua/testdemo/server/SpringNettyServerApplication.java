package com.linhua.testdemo.server;

import com.linhua.testdemo.server.netty.NettyServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author linhua
 * @version 1.0
 * @Description:
 * @date 2020/8/24$ 3:10 下午$
 */
@SpringBootApplication
public class SpringNettyServerApplication  implements CommandLineRunner {


    @Autowired
    private NettyServer server;

    public static void main(String[] args) {
        SpringApplication.run(SpringNettyServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ChannelFuture future = server.start("127.0.0.1",8089);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                server.destroy();
            }
        });
        //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
        future.channel().closeFuture().syncUninterruptibly();
    }
}
