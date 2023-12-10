package org.example;

import java.sql.*;

public class Main {
    private static final String DB_URL = "jdbc:sqlite:organizer.db";
    private static Connection connection;

    public static void main(String[] args) {
        connectToDatabase();

        createEventsTable(); // Create events table if it doesn't exist

// Code to interact with the organizer
// Add, delete, view, or edit events

// For example:
        addEvent("Birthday", 2023, 12, 25, "John's Birthday");
        addEvent("Meeting", 2023, 12, 10, "Important Board Meeting");

        viewEvents();

        editEvent(1, "Meeting", 2023, 12, 10, "Changed Important Board Meeting");

        deleteEvent(2);

        viewEvents();

        disconnectFromDatabase();
    }

    private static void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306:MySQL82", "root", "12345678");
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            System.out.println("Connection to database failed: " + e.getMessage());
        }
    }

    private static void createEventsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS events (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT," +
                "year INTEGER," +
                "month INTEGER," +
                "day INTEGER," +
                "description TEXT)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Events table created.");
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }

    private static void addEvent(String type, int year, int month, int day, String description) {
        String sql = "INSERT INTO events (type, year, month, day, description) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, type);
            statement.setInt(2, year);
            statement.setInt(3, month);
            statement.setInt(4, day);
            statement.setString(5, description);
            statement.executeUpdate();
            System.out.println("Event added successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to add event: " + e.getMessage());
        }
    }

    private static void viewEvents() {
        String sql = "SELECT * FROM events";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Type: " + resultSet.getString("type") +
                        ", Date: " + resultSet.getInt("year") + "-" + resultSet.getInt("month") + "-" + resultSet.getInt("day") +
                        ", Description: " + resultSet.getString("description"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve events: " + e.getMessage());
        }
    }

    private static void editEvent(int id, String type, int year, int month, int day, String description) {
        String sql = "UPDATE events SET type = ?, year = ?, month = ?, day = ?, description = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, type);
            statement.setInt(2, year);
            statement.setInt(3, month);
            statement.setInt(4, day);
            statement.setString(5, description);
            statement.setInt(6, id);
            statement.executeUpdate();
            System.out.println("Event updated successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to update event: " + e.getMessage());
        }
    }

    private static void deleteEvent(int id) {
        String sql = "DELETE FROM events WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Event deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to delete event: " + e.getMessage());
        }
    }

    private static void disconnectFromDatabase() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Disconnected from database.");
            }
        } catch (SQLException e) {
            System.out.println("Disconnection failed: " + e.getMessage());
        }
    }
}