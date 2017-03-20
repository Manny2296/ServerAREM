import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.util.Base64;
import javax.imageio.ImageIO;
public class Ejercicio5 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        while(true){
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
        String inputLine, outputLine;
       URL url = new URL("http://www.definicionabc.com/wp-content/uploads/Im%C3%A1gen-Vectorial.jpg");
       BufferedImage img = ImageIO.read(url);
       
        File img2 = new File("imagen.jpg");
        ImageIO.write(img, "jpg", img2);
        FileInputStream ficheroStream = new FileInputStream(img2); 
byte contenido[] = new byte[(int)img2.length()]; 
ficheroStream.read(contenido);
String encoded = Base64.getEncoder().encodeToString(contenido);
        System.out.println("Received: Imagen cod"+encoded);
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            if (!in.ready()) {
                break;
            }
            if(inputLine.contains("out")){
                System.exit(0);
            }
            
        }
        outputLine ="HTTP/1.1 200 OK\r\n"
        + "Content-Type: text/html\r\n"
         + "\r\n"+ 
                "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Title of the document</title>\n"
                + "</head>"
                + "<body style='background:red'>"
                + "<h2>Pagina solicitada</h2>"    
                   + "<h3 style='color:white'>Arquitectura Empresarial</h3>" 
                 + "<h3 style='color:white'>Si desea salir del servidor Por Favor escriba OUT en la barra de direccion</h3>" 
                   + "<h4>Servidor HTTP</h4>"
                 
                + "</body>"
                + "</html>" + inputLine;
        out.println(outputLine);
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
        }
    }
}