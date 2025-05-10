package com.aspatel.vagabond;

import gg.jte.generated.precompiled.Templates;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final Templates templates;

  @GetMapping("/")
  public ResponseEntity<String> home(final HttpServletResponse response) {
    response.setContentType(MediaType.TEXT_HTML_VALUE);
    return ResponseEntity.ok(templates.index("Hello World").render());
  }
}
