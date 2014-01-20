package co.infinum.ava.annotations.processor.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Templates utility methods.
 *
 * Created by ivan on 13/01/14.
 */
public class Templates {

    protected static Templates instance;

    public static Templates getInstance() {
        if(instance == null) {
            instance = new Templates();
        }

        return instance;
    }

    public String read(String path) {
        InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream(path));

        BufferedReader bufferedReader = new BufferedReader(reader);

        StringBuilder builder = new StringBuilder();
        String line = null;
        do {
            try {
                if(line != null) {
                    builder.append("\n");
                }

                line = bufferedReader.readLine();

                if(line != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        } while(line != null);

        return builder.toString();
    }
}
