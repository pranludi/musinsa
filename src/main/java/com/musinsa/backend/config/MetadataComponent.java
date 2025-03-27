package com.musinsa.backend.config;

import com.musinsa.backend.domain.Metadata;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Component;

@Component
public class MetadataComponent {

  private final AtomicReference<Metadata> refMetadata = new AtomicReference<>();

  public Metadata get() {
    return refMetadata.get();
  }

  public void set(Metadata metadata) {
    this.refMetadata.set(metadata);
  }
}
