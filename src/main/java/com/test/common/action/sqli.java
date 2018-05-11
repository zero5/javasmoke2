import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/sqli"})
public class sqli extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        PrintWriter responseWriter = response.getWriter();

        String condition = request.getParameter("condition");
        String query = "SELECT * FROM items WHERE '" + condition + "'";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String driverName = "com.mysql.jdbc.Driver";

        try {
            Class.forName(driverName);

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db?user=user&password=pass"
            );
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                responseWriter.println("Owner: " + resultSet.getString(1) + ", item: " + resultSet.getString(2));
            }
        }
        catch (ClassNotFoundException e) {
        }
        catch (SQLException ex) {
        }
        finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
            catch (SQLException ex) {
            }
        }
    }
}