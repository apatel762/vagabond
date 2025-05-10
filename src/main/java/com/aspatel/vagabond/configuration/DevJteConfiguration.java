package com.aspatel.vagabond.configuration;

import gg.jte.TemplateEngine;
import gg.jte.generated.precompiled.DynamicTemplates;
import gg.jte.generated.precompiled.Templates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevJteConfiguration implements JteConfiguration {

  @Bean
  @Override
  public Templates templates(final TemplateEngine templateEngine) {
    return new DynamicTemplates(templateEngine);
  }
}
