package org.example.kubernetesWildfly;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"application"})
public class Application extends HttpServlet {

    private static final long serialVersionUID = 8532332299687592651L;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Sortie GET avec BD");
        final PrintWriter writer = response.getWriter();
        writer.println("Sortie GET avec BD");
        try {
            final Connection connection = this.connectDatabase();
            final Statement stmt = connection.createStatement();
            final ResultSet resultSet = stmt.executeQuery("SELECT * FROM Personnes");
            while (resultSet.next()) {
                writer.println("PersonneID: " + resultSet.getInt("PersonneID"));
                writer.println("Prenom: " + resultSet.getString("Prenom"));
                writer.println("Nom1: " + resultSet.getString("Nom1"));
                writer.println("Nom2: " + resultSet.getString("Nom2"));
                writer.println("Adresse: " + resultSet.getString("Adresse"));
                writer.println("Population: " + resultSet.getString("Population"));
                writer.println("--------------------------------------------------");
            }
            stmt.close();
            connection.close();

        } catch (final ClassNotFoundException e) {
            e.printStackTrace(writer);
        } catch (final SQLException e) {
            e.printStackTrace(writer);
        }
        writer.flush();
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Sortie POST");
        response.getWriter().println("Sortie POST");
        response.getWriter().flush();

    }

    private Connection connectDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://mariadb-service:3306/cours", "root", "admin123");
    }

}
