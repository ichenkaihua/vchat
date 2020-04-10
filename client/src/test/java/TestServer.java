import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TestServer {


    public static void main(String[] args){

        try {
            AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open();
             serverSocket.bind(new InetSocketAddress(5885));
            Future<AsynchronousSocketChannel> accept = serverSocket.accept();
            AsynchronousSocketChannel socketChannel = accept.get();
            ByteBuffer allocate = ByteBuffer.allocate(20);
            socketChannel.read(allocate, allocate, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {

                    System.out.println("读取到的字节数"+result);
                    ByteBuffer buffer = ByteBuffer.allocate(20);
                    socketChannel.read(buffer,buffer,this);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.out.println("读取出错");

                }
            });



            Thread.sleep(55555);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
