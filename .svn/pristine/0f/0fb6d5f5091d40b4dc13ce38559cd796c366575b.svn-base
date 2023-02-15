package powerservice.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContext;

public class SocketReadThread implements Runnable{
	ServletContext ctx;

    public SocketReadThread(ServletContext ctx) {
    	this.ctx = ctx;
	}

	public void run() {
        String soc_port = "8999";

        ServerSocket server = null;


        try {
            System.out.println("[SOCKET LOG] connect start!!!!");
            server = new ServerSocket(Integer.valueOf(soc_port).intValue()); //포트(10001)를 열고 기다린다...
            System.out.println("접속을 기다립니다.");

            while(true) {
            	Socket client = server.accept();	// 입력받은 클라이언트의  소켓을 생성한다...

            	InetAddress inetaddr = client.getInetAddress();  //접속된 소켓의 ip주소값을 가져온다..
            	System.out.println(inetaddr.getHostAddress() + " 로 부터 접속하였습니다.");  // 호스트주소값을 가져온다..

            	Runnable r = new ThreadServerHandler(client, 1);
            	Thread t = new Thread(r);

            	t.start();
            }
        } catch (Exception except) {
            System.out.println("[SOCKET LOG] except1 : " + except);
        } finally {
        	try {
        		if(server != null) server.close();
        	} catch(Exception except) {
        		System.out.println("[SOCKET LOG] SocketReadThread finally IOException : " + except);
        	}
        }
    }

	class ThreadServerHandler implements Runnable {
		private Socket client;
		private int counter;

        BufferedReader br = null;
        BufferedWriter bw = null;

		 // ThreadServerHandler 구성자
		 public ThreadServerHandler(Socket c, int i) {
			 client = c;
			 counter = i;
		 }

		 // run()메쏘드 구성
		 public void run() {
			 try {
				 InputStream in = client.getInputStream();  	// 접속된 곳의 입력값을 받아오기위해 스트림생성..
	             OutputStream out = client.getOutputStream();  // 접속된 곳의 출력값을 받아오기위해 스트림생성..
	             PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
				 // 출력값을 2바이트형으로 변환하기위해 OutputStreamWriter의 객체 초기화한다.. (변환해야 한글문자가 보이징..)
	             // 	그리고 한글문자를 보여지게 하려면 당연히 PrintWriter의 객체로 담는다..(변환?)해한다...
				 br = new BufferedReader(new InputStreamReader(in));
				 //출력값을 2바이트형으로 변환하기위해 InputStreamWriter의 객체 초기화한다.. (변환해야 한글문자가 표기가능..)
	             // 그리고 한글문자를 보여지게 하려면 당연히 BufferedReader의 객체로 담는다..(변환?)해한다...

				 //bw.println("Welcome ! You are connected ...");
				 //bw.flush();
			 } catch(IOException except) {
				 System.out.println("[SOCKET LOG] ThreadServerHandler Stream Setting IOException : " + except);
			 }

			 try {
			     while (true) {
					 String line = null;

					 while((line = br.readLine()) != null) {
						 System.out.println("클라이언트로 부터 전송받은 문자열 : " + line);
					 }
			     }
			 } catch(Exception except) {
				 System.out.println("[SOCKET LOG] ThreadServerHandler catch Exception : " + except);
			 } finally {
				 try {
					 if(bw != null) bw.close();
		             if(br != null) br.close();
				 } catch (Exception except) {
					 System.out.println("[SOCKET LOG] ThreadServerHandler finally Exception : " + except);
				 }
			 }
		 }
	}
}
