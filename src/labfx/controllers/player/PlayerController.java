package labfx.controllers.player;

import javafx.animation.ScaleTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import labfx.controllers.page.Page;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PlayerController extends Page {
    //<editor-fold desc="Fields">
    @FXML
    private Text errorLabel;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Label sourceLabel;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnPrev;
    @FXML
    private Button btnNext;
    @FXML
    private MediaView mediaView;

    private MediaPlayer player;
    private TwoWayEnumerator<File> mediaEnumerator;

    private static final String mediaFolder = "media";
    //</editor-fold>

    private void playScaleAnimation(Control control, final double amount, double duration) {
        final ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(duration), control);
        scaleTransition.setToX(amount);
        scaleTransition.setToY(amount);
        scaleTransition.playFromStart();
    }

    @Override
    public void onLoaded() {
        final EventHandler<MouseEvent> scaleEnter = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                playScaleAnimation((Control)mouseEvent.getSource(), 1.1, 100);
            }
        };

        final EventHandler<MouseEvent> scaleExit = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                playScaleAnimation((Control)mouseEvent.getSource(), 1.0, 50);
            }
        };

        btnPlay.setOnMouseEntered(scaleEnter);
        btnPlay.setOnMouseExited(scaleExit);
        btnNext.setOnMouseEntered(scaleEnter);
        btnNext.setOnMouseExited(scaleExit);
        btnPrev.setOnMouseEntered(scaleEnter);
        btnPrev.setOnMouseExited(scaleExit);

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
        mediaView.setFitWidth(900);
        mediaView.setFitHeight(625);

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
