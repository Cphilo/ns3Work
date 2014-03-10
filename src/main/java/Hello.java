import static spark.Spark.*;
import spark.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Hello {
    public static String SERVER_PUBLIC = System.getProperty("user.dir")+"/src/main/resources/public";
    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }

    public static void main(String[] args) {
        staticFileLocation("/public");
        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                response.type("text/html");
                try {
                    response.status(200);
                    System.out.println(SERVER_PUBLIC);
                    String content = readFile(SERVER_PUBLIC+"/templates/index.html", StandardCharsets.UTF_8);
                    return content;
                } catch (IOException e) {
                    response.status(404);
                    return "404 NOT FOUND!";
                }
            }
        });
    }

}