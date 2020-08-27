package cn.sh.cares.serialport;

import cn.sh.cares.serialport.ui.MainFrame;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SerialportApplication {

    public static void main(String[] args) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder(SerialportApplication.class);
        builder.headless(false).web(WebApplicationType.NONE).run(args);

        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

}
