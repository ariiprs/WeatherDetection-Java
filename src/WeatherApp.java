import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherApp extends JFrame{
    private JSONObject weatherData;

    public WeatherApp(){
        //membuat gui dan menambahkan judul
        super( "Weather App");
        // konfigurasi saat program selesai dan ditutup
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // mengatur ukuran dari tampilan
        setSize(450, 650);

        // load tampilan
        setLocationRelativeTo(null);

        setLayout(null);

        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents(){
        // field pencarian
        JTextField searchTextField = new JTextField();

        // mengatur penempatan dan ukuran dari komponen
        searchTextField.setBounds(15, 15, 351, 45);

        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);


        // gambar cuaca
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217 );
        add(weatherConditionImage);

        // teks temperature
        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);


        //deskripsi kondisi cuaca

        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450,36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);


        JLabel humidiyImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidiyImage.setBounds(15, 500, 74, 66);
        add(humidiyImage);

        JLabel humidityText = new JLabel("<html> <b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(220, 500, 74, 66);
        add(windSpeedImage);

        JLabel windspeedText = new JLabel("<html> <b>Windspeed</b> 15km</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);


        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        // hovering ke button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();

                //validasi input
                if (userInput.replaceAll("\\s", "").length() <=0){
                    return;
                }

                //mengambil data cuaca
                weatherData = WeatherAppBack.getWeatherData(userInput);

                String weatherCondition = (String) weatherData.get("weather_condition");

                switch (weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }

                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " C");

                weatherConditionDesc.setText(weatherCondition);

                //update kelembaban
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity<b> "+ humidity + "%</html>");

                //update kecepatan angin
                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed<b> "+ windspeed + "km/h</html>");

            }
        });
        add(searchButton);

    }

    //create gambar di tampilan
    private ImageIcon loadImage(String resourcePath){
        try {

            BufferedImage image = ImageIO.read(new File(resourcePath));

            return new ImageIcon(image);
        }catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;
    }
}
