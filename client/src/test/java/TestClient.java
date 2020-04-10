import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TestClient {

    public static void main(String[] args){
        byte[] bytes = "asdfadsfdsakljfldsjfksajdfopeuwo我们撒地方垃圾萨拉的房价阿拉斯加爱上了地方据了解".getBytes(Charset.forName("utf-8"));
        System.out.println("发送的bytes数"+bytes.length);
        try {
            AsynchronousSocketChannel socket = AsynchronousSocketChannel.open();
            Future<Void> connect = socket.connect(new InetSocketAddress("127.0.0.1", 5885));
            connect.get();
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            Future<Integer> future = socket.write(byteBuffer);
            Integer integer = future.get();
            System.out.println("写入了bytes数:"+integer);

            Thread.sleep(55555555);



        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
