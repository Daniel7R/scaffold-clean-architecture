package co.com.bancolombia.factory.adapters;

import static co.com.bancolombia.Constants.APP_SERVICE;
import static co.com.bancolombia.utils.Utils.buildImplementationFromProject;

import co.com.bancolombia.exceptions.CleanException;
import co.com.bancolombia.factory.ModuleBuilder;
import co.com.bancolombia.factory.ModuleFactory;
import java.io.IOException;

/**
 * Factory for generating gRPC client driven adapter.
 *
 * <p>This factory creates the infrastructure for consuming external gRPC services using Spring
 * gRPC. It generates a client stub that can be used to communicate with gRPC servers.
 *
 * <p>Usage: {@code gradle generateDrivenAdapter --type grpc}
 */
public class DrivenAdapterGrpc implements ModuleFactory {

  @Override
  public void buildModule(ModuleBuilder builder) throws IOException, CleanException {
    builder.appendToSettings("grpc-consumer", "infrastructure/driven-adapters");
    String dependency = buildImplementationFromProject(":grpc-consumer");
    builder.appendDependencyToModule(APP_SERVICE, dependency);
    builder
        .appendToProperties("spring.grpc.client.channels.example-service")
        .put("address", "static://localhost:9090")
        .put("negotiation-type", "PLAINTEXT");
    builder.setupFromTemplate("driven-adapter/grpc-consumer");
  }
}
