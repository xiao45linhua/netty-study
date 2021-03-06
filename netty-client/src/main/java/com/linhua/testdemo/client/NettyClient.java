package com.linhua.testdemo.client;

import com.linhua.testdemo.client.handler.NettyClientHandler;
import com.linhua.testdemo.client.handler.NettyClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author linhua
 * @version 1.0
 * @Description:
 * @date 2020/8/24$ 12:35 下午$
 */
@Slf4j
public class NettyClient {

    private final String host;
    private final int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new NettyClient("127.0.0.1",10010).start();
    }

    private void start() throws Exception {

        /**
         * Netty用于接收客户端请求的线程池职责如下。
         * （1）接收客户端TCP连接，初始化Channel参数；
         * （2）将链路状态变更事件通知给ChannelPipeline
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer());
            log.info("绑定端口成功");
            //绑定端口
            ChannelFuture f = b.connect(host,port).sync();
            f.channel().writeAndFlush("netty");
            f.channel().closeFuture().sync();
            log.info("绑定端口成功2");
        } catch (Exception e) {
            group.shutdownGracefully().sync();
            log.info("绑定端口失败");
        }finally {
            group.shutdownGracefully();
        }
    }

}
