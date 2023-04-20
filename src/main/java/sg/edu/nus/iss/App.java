package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App 
{
    /**
     * @param args
     * @throws IOException
     */
    public static void main( String[] args ) throws IOException
    {
        // 2 arguments
        // 1 argument for the file
        // 1 argument for the port the server will start on

        String fileName = args[0];
        String port = args[1];

        File newFile = new File(fileName);

        if (!newFile.exists()) {
            System.out.println("Cookie file not found");
            System.exit(0);
        }

        ServerSocket server = new ServerSocket(Integer.parseInt(port));
        Socket socket = server.accept();
        

        // slide 9 -  allow server to read and write over the communicaation channel
        try (InputStream is = socket.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            
            String msgReceived = "";

            try (OutputStream os = socket.getOutputStream()) {
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);
                
                // write our logic to receive and send
                while (!msgReceived.equalsIgnoreCase("close")) {
                    // slide 9 - receive message
                    msgReceived = dis.readUTF();

                    if (msgReceived.equals("get-cookie")) {

                        // instantiate Cookie.java
                        Cookie cookie = new Cookie();
                        cookie.readCookie(fileName);

                        // get a random cookie
                        String idiom = cookie.getRandomCookie();
                        // System.out.println(idiom);
                        // String idiom2 = cookie.getRandomCookie();
                        // System.out.println(idiom2);

                        // send the random cookie out using DataOutputStream(dos.writeUTF(XXXXXXX))
                        dos.writeUTF(idiom);
                        dos.flush();
                    } else {
                        dos.writeUTF("");
                        dos.flush();
                    }
                }

                // closes all output streams in reverse order   
                dos.close();
                bos.close();
                os.close();

            } catch (EOFException ex) {
                ex.printStackTrace();
            }

            // closes all input streams in reverse order
            dis.close();
            bis.close();
            is.close();

        } catch (EOFException ex) {
            ex.printStackTrace();
            socket.close();
            server.close();
        }

    }
}
