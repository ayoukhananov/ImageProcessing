package image.processing.api;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.IOException;


public final class ImageUtils {

    public static final double MAXIMUM_DISTANCE_BETWEEN_COLORS = 510.0d;

    private ImageUtils() {
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        final int originalWidth = imgSize.width;
        final int originalHeight = imgSize.height;
        final int boundWidth = boundary.width;
        final int boundHeight = boundary.height;
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        // first check if we need to scale width
        if (originalWidth > boundWidth) {
            //scale width to fit
            newWidth = boundWidth;
            //scale height to maintain aspect ratio
            newHeight = (newWidth * originalHeight) / originalWidth;
        }

        // then check if we need to scale even with the new height
        if (newHeight > boundHeight) {
            //scale height to fit instead
            newHeight = boundHeight;
            //scale width to maintain aspect ratio
            newWidth = (newHeight * originalWidth) / originalHeight;
        }

        return new Dimension(newWidth, newHeight);
    }

    public static BufferedImage getCroppedImage(BufferedImage source, double tolerance) {
        // Get our top-left pixel color as our "baseline" for cropping
        final int baseColor = source.getRGB(0, 0);

        final int width = source.getWidth();
        final int height = source.getHeight();

        int topY = Integer.MAX_VALUE;
        int topX = Integer.MAX_VALUE;
        int bottomY = -1;
        int bottomX = -1;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (colorWithinTolerance(baseColor, source.getRGB(x, y), tolerance)) {
                    if (x < topX) {
                        topX = x;
                    }
                    if (y < topY) {
                        topY = y;
                    }
                    if (x > bottomX) {
                        bottomX = x;
                    }
                    if (y > bottomY) {
                        bottomY = y;
                    }
                }
            }
        }
        final BufferedImage destination;
        if (source.getTransparency() == Transparency.OPAQUE) {
            destination = new BufferedImage((bottomX - topX + 1),
                    (bottomY - topY + 1), BufferedImage.TYPE_INT_RGB);
        } else {
            destination = new BufferedImage((bottomX - topX + 1),
                    (bottomY - topY + 1), BufferedImage.TYPE_INT_ARGB);

        }


        final Graphics2D g = destination.createGraphics();
        g.drawImage(source, 0, 0,
                destination.getWidth(), destination.getHeight(),
                topX, topY, bottomX, bottomY, null);
        g.dispose();

//        g.setComposite(AlphaComposite.Src);
//        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        g.setRenderingHint(RenderingHints.KEY_RENDERING,
//                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        return destination;
    }

    private static boolean colorWithinTolerance(int a, int b, double tolerance) {
        final int aAlpha = (a & 0xFF000000) >>> 24;   // Alpha level
        final int aRed = (a & 0x00FF0000) >>> 16;   // Red level
        final int aGreen = (a & 0x0000FF00) >>> 8;    // Green level
        final int aBlue = a & 0x000000FF;            // Blue level

        final int bAlpha = (b & 0xFF000000) >>> 24;   // Alpha level
        final int bRed = (b & 0x00FF0000) >>> 16;   // Red level
        final int bGreen = (b & 0x0000FF00) >>> 8;    // Green level
        final int bBlue = b & 0x000000FF;            // Blue level

        final double distance = Math.sqrt((aAlpha - bAlpha) * (aAlpha - bAlpha) +
                (aRed - bRed) * (aRed - bRed) +
                (aGreen - bGreen) * (aGreen - bGreen) +
                (aBlue - bBlue) * (aBlue - bBlue));

        // 510.0 is the maximum distance between two colors
        // (0,0,0,0 -> 255,255,255,255)
        final double percentAway = distance / MAXIMUM_DISTANCE_BETWEEN_COLORS;

        return (percentAway > tolerance);
    }


    public static BufferedImage modifytToransparentBg(BufferedImage buffered, String colorToTransform, int threshold) throws IOException {

        //get first pixel color
        //int color = buffered.getRGB(0, 0);
        return makeColorTransparent(buffered, Color.decode(colorToTransform), threshold);

    }

    private static BufferedImage makeColorTransparent(BufferedImage image, final Color color, int threshold) {
        final ImageFilter filter = new RGBImageFilter() {
            @Override
            public final int filterRGB(int x, int y, int rgb) {
                final int pixel = image.getRGB(x, y);
                final Color pixelColor = new Color(pixel);

                final int dr = Math.abs(pixelColor.getRed() - color.getRed());
                final int dg = Math.abs(pixelColor.getGreen() - color.getGreen());
                final int db = Math.abs(pixelColor.getBlue() - color.getBlue());

                if (dr <= threshold && dg <= threshold && db <= threshold) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        final ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
        final Image img = Toolkit.getDefaultToolkit().createImage(ip);

        return toBufferedImage(img);
    }

    private static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        final BufferedImage intermediate = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        final Graphics2D bGr = intermediate.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        bGr.setComposite(AlphaComposite.Src);
        bGr.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        bGr.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        bGr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // Return the buffered image
        return intermediate;
    }


}
