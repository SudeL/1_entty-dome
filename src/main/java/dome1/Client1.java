package dome1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * 客户端
 */
public class Client1 {
    public static void main(String[] args) throws InterruptedException {
        // 启动器
        new Bootstrap()
                //添加EventLoop
                .group(new NioEventLoopGroup())
                //客户端的channel实现
                .channel(NioSocketChannel.class)
                //添加处理器
                .handler(
                        new ChannelInitializer<NioSocketChannel>(){
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                nioSocketChannel.pipeline().addLast(new StringEncoder()); //字符串编码为 byteBuf
                            }
                        }
                )
                .connect(new InetSocketAddress("localhost", 8080))
                .sync()
                .channel()
                //向服务器发送数据
                .writeAndFlush("nnd");
    }
}
