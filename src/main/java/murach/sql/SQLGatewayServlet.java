package murach.sql;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.io.IOException;
import java.io.*;
public class SQLGatewayServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sqlStatement = req.getParameter("sqlStatement");
        String sqlResult = "";
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/murach";
            String user = "root";
            String password = "@Hoang352004";
            connection = DriverManager.getConnection(dbURL, user, password);

            // Create a statement
            statement = connection.createStatement();

            sqlStatement = sqlStatement.trim();
            if (sqlStatement.length() >= 6) {
                String sqlType = sqlStatement.substring(0, 6);

                if (sqlType.equalsIgnoreCase("select")) {
                    ResultSet resultSet = statement.executeQuery(sqlStatement);
                    sqlResult = SQLUtil.getHtmlTable(resultSet);
                    resultSet.close();
                }
                else{
                    int i = statement.executeUpdate(sqlStatement);
                    if (i == 0) {
                        sqlResult = "<p>The statement executed successfully.</p>";
                    }
                    else {
                        sqlResult="<p>The statement executed successfully.<br> "
                                +i+"row(s) affected.</p>;";
                    }
                }
            }
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            sqlResult = "<p>Error loading the database drive: <br>"
                    +e.getMessage()+"</p>;";
        } catch (SQLException e) {
            sqlResult = "<p>Error execting the SQL statement: <br>"
                    +e.getMessage()+"</p>;";
        }


        HttpSession session = req.getSession();
        session.setAttribute("sqlResult", sqlResult);
        session.setAttribute("sqlStatement", sqlStatement);

        String url = "/index.jsp";
        getServletContext()
                .getRequestDispatcher(url)
                .forward(req, resp);
    }
}

