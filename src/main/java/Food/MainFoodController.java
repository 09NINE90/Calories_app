package Food;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import static Food.DbHandler.*;

public class MainFoodController {
    @FXML
    private TextField ageField;

    @FXML
    private Button btnResult;

    @FXML
    private Button btnResultActive;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<String> comboActive;

    @FXML
    private ComboBox<String> comboFood;

    @FXML
    private TextField growthField;

    @FXML
    private Label labelWeight;

    @FXML
    private Label labelSave;

    @FXML
    private RadioButton radioFemale;

    @FXML
    private RadioButton radioMale;

    @FXML
    private TextField textFieldCalories;

    @FXML
    private TextField textFieldCarbohydrates;

    @FXML
    private TextField textFieldFats;

    @FXML
    private TextField textFieldProteins;

    @FXML
    private TextField textFieldResultActive;

    @FXML
    private TextField textFieldWeight;

    @FXML
    private TextField weightField;

    DbHandler dbHandler = new DbHandler();
    static Calories calories;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        labelSave.setText("");
        labelSave.setWrapText(true);

        labelSave.setTextAlignment(TextAlignment.CENTER);
        textFieldCalories.setEditable(false);
        textFieldProteins.setEditable(false);
        textFieldFats.setEditable(false);
        textFieldCarbohydrates.setEditable(false);
        // настройка калькулятора калорий
        active();

        // подключаем базы данных
        dbHandler.getDbConnectionMain();
        dbHandler.getDbConnection();

        // создаем базы данных
        dbHandler.create();
        dbHandler.createMain();

        // заполняем comboBox названиями продуктов
        dbHandler.getResult();
        ArrayList<String> values = new ArrayList<>(hashMapFood.values());
        comboFood.getItems().addAll(values);
        comboFood.setValue("Банан");

        // кнопка получения данных о продукте
        btnResult.setOnAction(event -> result());

        // сохранить прием пищи
        btnSave.setOnAction(event -> saveFood());

    }

    public void result(){

        for (Map.Entry entry : hashMapFood.entrySet()) {
            if (entry.getValue() == comboFood.getValue()) {
                food.setId((Integer) entry.getKey());
            }
        }

        // получаем данные о продукте из БД
        dbHandler.getResultFoodVar();

        if (textFieldWeight.getText() != "") {
            float weight = Float.parseFloat(textFieldWeight.getText()) / 100;
            textFieldCalories.setText(String.valueOf((Math.round(food.getCaloriesFood() * 100) / 100.0) * weight));
            textFieldFats.setText(String.valueOf((Math.round(food.getFatFood() * 100) / 100.0) * weight));
            textFieldCarbohydrates.setText(String.valueOf((Math.round(food.getCarbohydratesFood() * 100) / 100.0) * weight));
            textFieldProteins.setText(String.valueOf((Math.round(food.getProteinFood() * 100) / 100.0) * weight));
        } else {
            textFieldCalories.setText(String.valueOf((Math.round(food.getCaloriesFood() * 100) / 100.0)));
            textFieldFats.setText(String.valueOf((Math.round(food.getFatFood() * 100) / 100.0)));
            textFieldCarbohydrates.setText(String.valueOf((Math.round(food.getCarbohydratesFood() * 100) / 100.0)));
            textFieldProteins.setText(String.valueOf((Math.round(food.getProteinFood() * 100) / 100.0)));
        }

    }

    public void saveFood(){
        // получение текущей даты в формате год месяц день
        int simpleDateFormat = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()));

        if(textFieldCalories.getText() != "" && textFieldResultActive.getText() != ""){
            textFieldCalories.setStyle("-fx-border-color: none ; -fx-border-radius: 7px ;");
            textFieldProteins.setStyle("-fx-border-color: none ; -fx-border-radius: 7px ;");
            textFieldCarbohydrates.setStyle("-fx-border-color: none ; -fx-border-radius: 7px ;");
            textFieldFats.setStyle("-fx-border-color: none; -fx-border-radius: 7px ;");
            textFieldResultActive.setStyle("-fx-border-color: none ; -fx-border-radius: 7px ;");
            calories = new Calories(Double.parseDouble(textFieldCalories.getText()), Double.parseDouble(textFieldResultActive.getText()), simpleDateFormat);

            // работа в БД
            dbHandler.getCalories();
            if (calories.getCaloriesDay() != 0 && calories.getCaloriesDayNormalDb() != 0){
                dbHandler.changeCaloriesQuantity();
            }else {
                dbHandler.writeToDatabase();
            }
            dbHandler.getCalories();
            // вывод в label отношение полученных ккал к необходимым
            labelSave.setText(calories.getCaloriesDay() + "/" + calories.getCaloriesDayNormal() + " ккал");
            if (calories.getCaloriesDay() > calories.getCaloriesDayNormal()){
                labelSave.setTextFill(new Color(1,0,0,1));
            }else {
                labelSave.setTextFill(new Color(1,1,1,1));
            }
        }else {
            textFieldCalories.setStyle("-fx-border-color: #228F20 ; -fx-border-radius: 7px ;");
            textFieldProteins.setStyle("-fx-border-color: #228F20 ; -fx-border-radius: 7px ;");
            textFieldCarbohydrates.setStyle("-fx-border-color: #228F20 ; -fx-border-radius: 7px ;");
            textFieldFats.setStyle("-fx-border-color: #228F20 ; -fx-border-radius: 7px ;");
            textFieldResultActive.setStyle("-fx-border-color: #228F20 ; -fx-border-radius: 7px ;");

        }

    }


    public void active(){
        ToggleGroup group = new ToggleGroup();
        radioMale.setToggleGroup(group);
        radioFemale.setToggleGroup(group);
        comboActive.getItems().addAll("Минимальная нагрузка","3 раза в неделю","5 раз в неделю");
        comboActive.setValue("Минимальная нагрузка");

        // кнопка подсчета требующихся ккал в день
        btnResultActive.setOnAction(event ->resultActive());
    }

    public void resultActive(){
        float a = 0;
        int sex = 5;
        if(ageField.getText() != "" && weightField.getText() != "" && growthField.getText() != ""){
            int age = Integer.parseInt(ageField.getText());
            int weight = Integer.parseInt(weightField.getText());
            int growth = Integer.parseInt(growthField.getText());
            if (radioFemale.isSelected()) {
                sex = -161;
            }
            if (radioMale.isSelected()) {
                sex = 5;
            }
            if (comboActive.getValue() == "Минимальная нагрузка"){
                a += 1.2;
            }
            if (comboActive.getValue() == "3 раза в неделю"){
                a += 1.55;
            }
            if (comboActive.getValue() == "5 раз в неделю"){
                a += 1.7;
            }
            //формула подсчета ккал
            double result = (float) ((10 * weight) + (6.25 * growth) - (5 * age) + sex);
            result *= a;
            result = (Math.round(result * 100) / 100.0);
            textFieldResultActive.setText(String.valueOf(result)); // вывод результата

        }else {
            System.out.println("Введите данные!");
        }
    }
}