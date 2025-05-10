package com.aspatel.vagabond.configuration;

import gg.jte.TemplateEngine;
import gg.jte.generated.precompiled.Templates;

public interface JteConfiguration {

  Templates templates(TemplateEngine templateEngine);
}
