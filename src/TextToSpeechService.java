import javax.sound.sampled.*;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TextToSpeechService {
    private final String region = "eastasia";
    private final String apiKey = "b2732572853145d599061f47de724a0f";
    private final String issueTokenUrl = "https://eastasia.api.cognitive.microsoft.com/sts/v1.0/issueToken";
    private final String textToSpeedUrl = "https://eastasia.tts.speech.microsoft.com/cognitiveservices/v1";
    private String token;
    private void issueToken() {
        System.setProperty("jdk.httpclient.allowRestrictedHeaders", "Content-Length");

        try {
            String data = "";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(issueTokenUrl))
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .header("Ocp-Apim-Subscription-Key", apiKey)
                    .header("Content-Length", "0")
                    .header("Accept", "*/*")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            token = response.body();
            System.out.println(token);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    // language: en-US OR
    public void textToSpeech(String text, String language) {
        issueToken();
        String data;
        if(language.equals("vi-VN")) {
            data = "<speak version='1.0' xml:lang='vi-VN'>\n" +
                    "    <voice xml:lang='vi-VN' xml:gender='Female' name='vi-VN-HoaiMyNeural'>\n" +
                    text + "\n" +
                    "    </voice>\n" +
                    "</speak>";
        }
        else {
            data = "<speak version='1.0' xml:lang='en-US'>\n" +
                    "    <voice xml:lang='en-US' xml:gender='Male' name='en-US-ChristopherNeural'>\n" +
                    text + "\n" +
                    "    </voice>\n" +
                    "</speak>";
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()

                .uri(URI.create(textToSpeedUrl))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .header("Content-Type", "application/ssml+xml")
                .header("X-Microsoft-OutputFormat", "riff-24khz-16bit-mono-pcm")
                .header("Authorization", "Bearer " + token)
                .header("Ocp-Apim-Subscription-Key", this.apiKey)
                .header("Accept", "*/*")
                .build();
        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() == 200) {
                byte[] bytes = response.body().readAllBytes();
                try (OutputStream os = new FileOutputStream("out.wav")) {
                    os.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("out.wav"));
                // Get a Clip instance
                Clip clip = AudioSystem.getClip();

                // Open the audioInputStream
                clip.open(audioInputStream);

                // Start playing the clip
                clip.start();

                // Sleep while the clip is playing
                Thread.sleep(clip.getMicrosecondLength() / 1000);

                // Close the clip
                clip.close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//       var service = new TextToSpeechService();
//       service.issueToken();
//       service.textToSpeech("hello", "english");
//   }
}




