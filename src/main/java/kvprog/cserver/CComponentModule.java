package kvprog.cserver;

import dagger.Module;
import dagger.Provides;
import dagger.grpc.server.GrpcCallMetadataModule;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import javax.inject.Qualifier;
import javax.inject.Singleton;
import kvprog.cserver.KvStoreImplGrpcProxyModule;
import kvprog.cserver.KvStoreImplServiceDefinition;
import kvprog.cserver.ServerApp.ServerComponent;

@Module(includes = KvStoreImplGrpcProxyModule.class)
class CComponentModule {

  @Provides
  static KvStoreImplServiceDefinition.Factory provideServiceFactor(
      final ServerComponent component) {
    return new KvStoreImplServiceDefinition.Factory() {
      @Override
      public KvStoreImplServiceDefinition grpcService(
          GrpcCallMetadataModule metadataModule) {
        return component.serviceComponent(metadataModule);
      }
    };
  }

  @Singleton // Shared between all requests.
  @Provides
  @Cache
  HashMap<String, String> provideCache() {
    return new HashMap<>();
  }

  @Qualifier
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Cache {

  }
}
