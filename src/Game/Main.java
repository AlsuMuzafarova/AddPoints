package Game;
import com.google.gson.*;
import org.json.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    private Creature[] creatures;

    private void setCreaturesNames() {
        for (int i = 0; i < creatures.length; i++) {
            creatures[i].setName("Player " + i);
        }
    }
    //задает
    private void initGame() {
        creatures = new Creature[4];
        creatures[0] = new Something();
        creatures[1] = new Something();
        creatures[2] = new Something();
        creatures[3] = new Something();

        for(Creature c : creatures) {
            c.init();
        }
    }

    private String startGame() {
        boolean flag = false;
        String winner = null;
        while (true) {
            for(Creature c : creatures) {
                Point p = c.move();
                if (inSquare(p)) {
                    c.print();
                    winner = c.name;
                    flag = true;
                    break;
                }
            }
            if (flag){
                break;
            }
        }
        return winner;
    }

    private boolean inSquare(Point p) {
        int row = p.getRow();
        int column = p.getColumn();
        if ((400<=row && row<=600) && (400<=column && column<=600)){
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Main main = new Main();
        main.initGame();
        main.setCreaturesNames();
        File file = new File("Results");
        file.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < 6; i++){
            long timeToStart = System.nanoTime();
            String winner = main.startGame();
            long timeToEnd = System.nanoTime();
            long gameTime = timeToEnd - timeToStart;
            String jsonString = new JSONStringer().object().key("Game start time").value(timeToStart)
                    .key("Duration of the game").value(gameTime).key("Winner").value(winner).endObject().toString();
            bw.write(jsonString);
            bw.newLine();
        }
        bw.flush();
        bw.close();
        Stream<String> jsonLinesStream = Files.lines(file.toPath());
        Stream<Map<String, Object>> mapStream = jsonLinesStream
                .map(Main::jsonLineToMap)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(map -> (String) map.get("Winner")));
        mapStream.forEach(System.out::println);
    }

    private static Map<String, Object> jsonLineToMap(String jsonLine) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(jsonLine, JsonElement.class);
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Map<String, Object> map = new HashMap<>();
            jsonObject.entrySet().forEach(entry -> {
                Object value = convertJsonElementToObject(entry.getValue());
                map.put(entry.getKey(), value);
            });
            return map;
        } else {
            return null;
        }
    }
    private static Object convertJsonElementToObject(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            } else if (primitive.isNumber()) {
                return primitive.getAsNumber();
            } else if (primitive.isString()) {
                return primitive.getAsString();
            }
        } else if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            List<Object> list = new ArrayList<>();
            for (JsonElement item : array) {
                list.add(convertJsonElementToObject(item));
            }
            return list;
        } else if (element.isJsonObject()) {
            return jsonLineToMap(element.toString());
        }
        return null;
    }
}
