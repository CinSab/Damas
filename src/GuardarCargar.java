import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class GuardarCargar {
    static int[][] matriz = new int[8][8];
    static boolean sigueturno;

    public static void guardarPartida(int[][] tableroFichas, boolean siguesuturno) {
        JSONObject estadoPartida = new JSONObject();
        estadoPartida.put("Turno", siguesuturno);

        JSONArray tabler = new JSONArray();
        for (int i = 0; i < tableroFichas.length; i++) {
            for (int j = 0; j < tableroFichas.length; j++) {
                tabler.add(tableroFichas[i][j]);
            }
        }
        estadoPartida.put("Tablero", tabler);

        try (FileWriter file = new FileWriter("partida.json")) {
            file.write(estadoPartida.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(estadoPartida);

    }

    public static void cargarPartida() {
        JSONParser parser = new JSONParser();
        try {
            Reader reader = new FileReader("partida.json");
            JSONObject partida = (JSONObject) parser.parse(reader);

            boolean siguesuturno = (boolean) partida.get("Turno");
            sigueturno = siguesuturno;

            JSONArray tablero = (JSONArray) partida.get("Tablero");
            Iterator<Object> iterator = tablero.iterator();
            while (iterator.hasNext()) {
                for (int i = 0; i < matriz.length; i++) {
                    for (int j = 0; j < matriz[0].length; j++) {
                        long num = (long) iterator.next();
                        matriz[i][j] = (int) num;

                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
}

