package labfx.controllers.player;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import labfx.controllers.page.Page;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PlayerController extends Page {
    @FXML
    private Text errorLabel;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Label sourceLabel;
    @FXML
    private Button btnPlay;
    @FXML
    private MediaView mediaView;

    private MediaPlayer player;
    private TwoWayEnumerator<File> mediaEnumerator;

    private static final String mediaFolder = "media";

    @Override
    public void onLoaded() {
        mediaEnumerator = EnumeratorFactory.getTwoWayEnumerator(getMediaFiles());

        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (player != null)
                    player.setVolume(volumeSlider.getValue());
            }
        });

        errorLabel.setVisible(!next());
    }

    @Override
    public void onClosed() {
        if (player != null) {
            player.stop();
            player.dispose();
        }
    }

    private static Collection<File> getMediaFiles() {
        ArrayList<File> mediaList = new ArrayList<File>();
        File[] mediaFiles = new File(mediaFolder).listFiles(new SupportedExtensions());
        if (mediaFiles != null) {
            Collections.addAll(mediaList, mediaFiles);
        }
        return mediaList;
    }

    private static MediaPlayer createPlayer(File file) {
        return new MediaPlayer(new Media(file.toURI().toString()));
    }

    private void setupPlayer() {
        if (player != null) {
            player.stop();
            player.dispose();
        }

        player = createPlayer(mediaEnumerator.current());
        player.setOnPlaying(new Runnable() {
            @Override
            public void run() {
                btnPlay.setText("||");
            }
        });
        player.setOnPaused(new Runnable() {
            @Override
            public void run() {
                btnPlay.setText(">");
            }
        });
        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (next()) {
                    playMedia();
                } else {
                    mediaEnumerator.reset();
                    nextMedia();
                }
            }
        });

        sourceLabel.setText(new File(player.getMedia().getSource()).getName().replace("%20", " "));
        mediaView.setMediaPlayer(player);
        mediaView.setFitWidth(720);
        mediaView.setFitHeight(500);

        player.setVolume(volumeSlider.getValue());
    }

    private boolean next() {
        if (mediaEnumerator.moveNext()) {
            setupPlayer();
            return true;
        }

        return false;
    }

    private boolean prev() {
        if (mediaEnumerator.movePrevious()) {
            setupPlayer();
            return true;
        }

        return false;
    }

    @FXML
    private void playMedia() {
        if (player == null) {
            setupPlayer();
        }

        if (player != null) {
            MediaPlayer.Status status = player.getStatus();

            if (status == MediaPlayer.Status.HALTED) {
                return;
            }

            if (status == MediaPlayer.Status.UNKNOWN) {
                player.setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        player.setOnReady(null);
                        playMedia();
                    }
                });
                return;
            }

            if (status == MediaPlayer.Status.PLAYING)
            {
                player.pause();
            } else if (status == MediaPlayer.Status.PAUSED ||
                    status == MediaPlayer.Status.STOPPED ||
                    status == MediaPlayer.Status.READY) {
                player.play();
            }
        }
    }

    @FXML
    private void nextMedia() {
        if (next()) playMedia();
    }

    @FXML
    private void prevMedia() {
        if (prev()) playMedia();
    }
}
