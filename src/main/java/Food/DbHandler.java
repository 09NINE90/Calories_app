package Food;

import java.sql.*;
import java.util.HashMap;

import static Food.MainFoodController.calories;


public class DbHandler {
    Connection connectionFood = null;
    Connection connectionCalories = null;
    static HashMap<Integer, String> hashMapFood = new HashMap<>();
    static Food food = new Food();

    public void getDbConnectionMain() throws ClassNotFoundException, SQLException {
        try {
            connectionFood = DriverManager.getConnection("jdbc:sqlite:food_db.db");
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void getDbConnection() throws ClassNotFoundException, SQLException {
        try {
            connectionCalories = DriverManager.getConnection("jdbc:sqlite:calories.db");
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void createMain() {
        try {
            Statement statement = connectionFood.createStatement();
            String query = "CREATE TABLE if not exists food_db ( " +
                    "id_food            INTEGER      PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name_food          VARCHAR (45) NOT NULL," +
                    "calories_food      VARCHAR (15) NOT NULL," +
                    "protein_food       VARCHAR (10) NOT NULL," +
                    "fats_food          VARCHAR (10) NOT NULL," +
                    "carbohydrates_food VARCHAR (10) NOT NULL);";

            statement.executeQuery(query);
        } catch (SQLException e) {
        }
    }

    public void create() {
        try {
            Statement statement = connectionCalories.createStatement();
            String query = "CREATE TABLE if not exists calories ( " +
                    "id                         INTEGER      PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "calories_quantity          DOUBLE NOT NULL," +
                    "calories_day_norm          DOUBLE  NOT NULL," +
                    "date_calories              INTEGER NOT NULL);";
            statement.executeQuery(query);
        } catch (SQLException e) {
        }
    }


    // добавляем данные в новый день
    public void writeToDatabase() {
        PreparedStatement statementP = null;
        try {
            statementP = connectionCalories.prepareStatement("INSERT INTO calories (calories_quantity,calories_day_norm,date_calories) VALUES (?, ?, ?)");
            statementP.setDouble(1, calories.getCaloriesQuantity());
            statementP.setDouble(2, calories.getCaloriesDayNormal());
            statementP.setInt(3, calories.getDateCalories());
            statementP.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // меняем данные в текущем дне
    public void changeCaloriesQuantity() {
        String update1 = "UPDATE calories SET calories_quantity = calories_quantity + " + calories.getCaloriesQuantity() + " WHERE date_calories = " + calories.getDateCalories();
        String update2 = "UPDATE calories SET calories_day_norm = " + calories.getCaloriesDayNormal() + " WHERE date_calories = " + calories.getDateCalories();
        try {
            PreparedStatement statement = connectionCalories.prepareStatement(update1);
            statement.executeUpdate();
            statement = connectionCalories.prepareStatement(update2);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }


    public ResultSet getCalories() {
        ResultSet resultSet = null;
        String select = "SELECT * FROM calories WHERE date_calories = " + calories.getDateCalories();
        try {
            Statement statement = connectionCalories.createStatement();
            resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                calories.setCaloriesDay(resultSet.getFloat(2));
                calories.setCaloriesDayNormalDb(resultSet.getFloat(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }

        return resultSet;
    }


    public ResultSet getResult() {
        ResultSet resultSet = null;
        String select = "SELECT * FROM food_db";
        try {
            Statement statement = connectionFood.createStatement();
            resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name_food = resultSet.getString(2);
                hashMapFood.put(id, name_food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
        return resultSet;
    }


    // получаем данные о продукте из БД
    public ResultSet getResultFoodVar() {
        ResultSet resultSet = null;
        String select = "SELECT * FROM food_db WHERE id_food = " + food.getId();
        try {
            Statement statement = connectionFood.createStatement();
            resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                food.setCaloriesFood(resultSet.getFloat(3));
                food.setProteinFood(resultSet.getFloat(4));
                food.setFatFood(resultSet.getFloat(5));
                food.setCarbohydratesFood(resultSet.getFloat(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
        return resultSet;
    }
}
