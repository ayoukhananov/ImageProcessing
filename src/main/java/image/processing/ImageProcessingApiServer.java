package image.processing;

import com.outbrain.ob1k.HttpRequestMethodType;
import com.outbrain.ob1k.server.Server;
import com.outbrain.ob1k.server.endpoints.EndpointMappingService;
import com.outbrain.ob1k.server.spring.SpringBeanContext;
import com.outbrain.ob1k.server.spring.SpringContextBuilder;
import com.outbrain.ob1k.server.spring.SpringContextServerBuilder;
import image.processing.services.ProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static com.outbrain.ob1k.server.endpoints.EndpointMappingServiceBuilder.registerMappingService;
import static com.outbrain.ob1k.swagger.service.SwaggerServiceBuilder.enableSwagger;
import static java.util.Collections.singletonList;

public final class ImageProcessingApiServer {

  private static final String CONTEXT_PATH = "/imageProcessing";
  private static final Logger LOGGER = LoggerFactory.getLogger(ImageProcessingApiServer.class);
  private static final int REQUEST_TIMEOUT = 5000;
  private static final int PORT = 4646;

  private ImageProcessingApiServer() {
  }

  public static void main(final String[] args) {
    final Server server = buildServer();
    server.start();
    LOGGER.info("******** ImageProcessing started ********");
  }

  //http://localhost:4646/imageProcessing/api/swagger-ui.html
  private static Server buildServer() {
    final SpringBeanContext ctx = new SpringContextBuilder(CONTEXT_PATH).
      setMainContext("main", "classpath:imageProcessing-service.xml").
      build();

    return SpringContextServerBuilder.newBuilder(ctx).
      contextPath(CONTEXT_PATH).
      configure(builder -> builder.usePort(PORT)
        .requestTimeout(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
//        .configureExecutorService(10, 200)
//        .acceptKeepAlive(true)
      ).
      serviceFromContext(builder -> builder.register("main", ProcessingService.class, "/api", bind -> {
          bind.endpoint(HttpRequestMethodType.GET, "hello", "/hello");
          bind.endpoint(HttpRequestMethodType.POST, "crop", "/crop");
        }
      )).
      withExtension(registerMappingService("/endpoints")).
      withExtension(enableSwagger("/api/swagger", singletonList(EndpointMappingService.class))).
      build();
  }
}
