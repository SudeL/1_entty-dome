package dome1;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 服务端
 */
public class Service1 {
    public static void main(String[] args) {
        //1.启动器，组装netty组件
        new ServerBootstrap()
                //2.添加组件
                .group(new NioEventLoopGroup()) //创建组，NioEventLoopGroup中包含线程和选择器
                //3.选择ServerSocketChannel的实现，支持BIO,NIO等
                .channel(NioServerSocketChannel.class)
                //4. boss 负责处理连接 worker(child)负责处理读写 childHandler()决定了child可以实现那些操作
                .childHandler(
                        //5. Channel 代表和客户端进行数据读写的通道 Initializer 代表初始化处理器
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch ) throws Exception{
                        //6.添加具体的handel
                        ch.pipeline().addLast(new StringDecoder()); //数据解码 StringDecoder 方法可以将ByteBuf类型的数据转换为String类型
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){ //自定义 handler
                            @Override //处理读事件，msg就是上一步中StringDecoder转换的数据
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws  Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                //7. 决定NioServerSocketChannel.class启动之后绑定的监听端口
                .bind(8080);
    }
}
