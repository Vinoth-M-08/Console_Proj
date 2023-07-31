package Student;import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Project {
    private int id;
    private String title;
    private String description;
    private String progress;

    // Constructors
    public Project() {
    }

    public Project(int id, String title, String description, String progress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.progress = progress;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public static void uploadProject(Connection connection, String title, String description) throws SQLException {
        String insertQuery = "INSERT INTO projects (title, description) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();
        }
    }

    public static void editProject(Connection connection, int projectId, String title, String description) throws SQLException {
        String updateQuery = "UPDATE projects SET title = ?, description = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, projectId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Project with ID " + projectId + " updated successfully!");
            } else {
                System.out.println("Project with ID " + projectId + " not found.");
            }
        }
    }

    public static void updateProjectProgress(Connection connection, int projectId, String progress) throws SQLException {
        String updateQuery = "UPDATE projects SET progress = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, progress);
            preparedStatement.setInt(2, projectId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Project progress updated for ID " + projectId + " successfully!");
            } else {
                System.out.println("Project with ID " + projectId + " not found.");
            }
        }
    }

    public static void viewAllProjects(Connection connection) {
        String selectQuery = "SELECT * FROM projects";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            System.out.println("All Projects:");
            while (resultSet.next()) {
                int projectId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String progress = resultSet.getString("progress");

                System.out.println("Project ID: " + projectId);
                System.out.println("Title: " + title);
                System.out.println("Description: " + description);
                System.out.println("Progress: " + progress);
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
