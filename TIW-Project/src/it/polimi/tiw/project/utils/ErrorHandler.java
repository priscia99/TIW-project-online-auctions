package it.polimi.tiw.project.utils;

import java.io.PrintWriter;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

public class ErrorHandler {
	public static final void displayErrorPage(WebContext context, PrintWriter writer, TemplateEngine engine,  String message) {
		context.setVariable("errorMessage", message);
		engine.process("/error.html", context, writer);
	}
}
