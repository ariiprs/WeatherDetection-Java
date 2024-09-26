import javax.swing.*;

public class AppLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // menampilkan apps weather
                new WeatherApp().setVisible(true);

//                System.out.println(WeatherAppBack.getLocationData("Tangerang"));

                System.out.println(WeatherAppBack.getCurrentTime());

            }
        });
    }
}
