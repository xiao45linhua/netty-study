package com.linhua.testdemo.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author linhua
 * @version 1.0
 * @Description:
 * @date 2020/8/24$ 2:05 下午$
 */
@Slf4j
@Component
public class NettyServer {
    /**
    * NioEventLoop并不是一个纯粹的I/O线程，它除了负责I/O的读写之外
    * 创建了两个NioEventLoopGroup，
    * 它们实际是两个独立的Reactor线程池。
    * 一个用于接收客户端的TCP连接，
    * 另一个用于处理I/O相关的读写操作，或者执行系统Task、定时任务Task等。
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private Channel channel;

    public ChannelFuture start(String hostname,int port) throws Exception {
        final NettyServerHandler nettyServerHandler = new NettyServerHandler();
        ChannelFuture f = null;
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(hostname,port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(nettyServerHandler);
                    }
                });

        f = b.bind().sync();
        channel = f.channel();
        log.info("======EchoServer启动成功!!!=========");
        return f;
    }


    /**
     * 停止服务
     */
    public void destroy() {
        log.info("Shutdown Netty Server...");
        if(channel != null) { channel.close();}
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        log.info("Shutdown Netty Server Success!");
    }

}
