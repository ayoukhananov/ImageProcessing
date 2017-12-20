//package image.processing.mycode.api;
//
//
//import com.google.common.base.Charsets;
//import com.google.common.hash.HashCode;
//import com.google.common.hash.Hashing;
//
//
//import java.security.NoSuchAlgorithmException;
//
//public class PathInfo {
//    private final String url;
//    private final String originalContentType;
//    private final String urlMD5Hash;
//
//    public String getUrl() {
//        return url;
//    }
//
//    public String getUrlMD5Hash() {
//        return this.urlMD5Hash;
//    }
//
//    public String getOriginalContentType() {
//        return originalContentType;
//    }
//
//    public PathInfo(String url, String contentType) throws NoSuchAlgorithmException {
//        this.url = url;
//        this.originalContentType = contentType;
//        this.urlMD5Hash = execMDFHash(url);
//    }
//
//    private static String execMDFHash(String imageUrl) throws NoSuchAlgorithmException {
//
//        final String normalizedUrl = getNormalizedUrl(imageUrl);
//
//        final HashCode hashCode = Hashing.md5().hashString(normalizedUrl, Charsets.UTF_8);
//        final byte[] hash = hashCode.asBytes();
//
//        final StringBuilder out = new StringBuilder();
//        for (final byte b : hash) {
//            out.append(String.format("%02X", b));
//        }
//        //log.debug("execMDFHash() hashed name ={}", out.toString().toLowerCase());
//        return out.toString().toLowerCase();
//    }
//
//    private static String getNormalizedUrl(String imageUrl) {
//       return imageUrl;
//    }
//
//}
