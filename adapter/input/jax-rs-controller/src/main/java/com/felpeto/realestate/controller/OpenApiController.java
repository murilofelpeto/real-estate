package com.felpeto.realestate.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;



@ApplicationScoped
@Path("/v1/openapi")
@Produces(value = MediaType.APPLICATION_JSON)
@OpenAPIDefinition(
    info = @Info(
        title = "Real State API",
        version = "1",
        description = "This API helps manage all properties that are for sale, rent, generate contracts and much more",
        contact = @Contact(url = "https://realestate.com.br", name = "Real Estate", email = "contato@realestate.com.br")
    )
)
public class OpenApiController {

  @GET
  @Operation(hidden = true)
  public InputStream openApi() {
    return OpenApiController.class.getResourceAsStream("openapi.json");
  }
}
