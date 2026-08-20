interface Playable {
    void play();
}

interface Compressible {
    void compress();
}

abstract class MediaFile {
    private String fileName;

    public MediaFile(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public abstract void displayInfo();
}

class ImageFile extends MediaFile implements Compressible {
    public ImageFile(String fileName) {
        super(fileName);
    }

    @Override
    public void displayInfo() {
        System.out.println("影像檔案: " + getFileName());
    }

    @Override
    public void compress() {
        System.out.println("正在壓縮圖片: " + getFileName());
    }
}

class AudioFile extends MediaFile implements Playable {
    public AudioFile(String fileName) {
        super(fileName);
    }

    @Override
    public void displayInfo() {
        System.out.println("音訊檔案: " + getFileName());
    }

    @Override
    public void play() {
        System.out.println("正在播放音樂: " + getFileName());
    }
}

class VideoFile extends MediaFile implements Playable, Compressible {
    public VideoFile(String fileName) {
        super(fileName);
    }

    @Override
    public void displayInfo() {
        System.out.println("影片檔案: " + getFileName());
    }

    @Override
    public void play() {
        System.out.println("正在播放影片: " + getFileName());
    }

    @Override
    public void compress() {
        System.out.println("正在壓縮影片: " + getFileName());
    }
}

public class MediaProcessingSystem {
    public static void main(String[] args) {
        MediaFile[] files = {
            new ImageFile("photo.jpg"),
            new AudioFile("song.mp3"),
            new VideoFile("movie.mp4")
        };

        for (MediaFile file : files) {
            file.displayInfo();

            if (file instanceof Playable) {
                ((Playable) file).play();
            }

            if (file instanceof Compressible) {
                ((Compressible) file).compress();
            }

            System.out.println("--------------------");
        }
    }
}