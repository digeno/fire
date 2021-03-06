/* Copyright (C) 2017 [Gobierno de Espana]
 * This file is part of FIRe.
 * FIRe is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation;
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either version 1.1 or (at your option) any later version.
 * Date: 08/09/2017
 * You may contact the copyright holder at: soporte.afirma@correo.gob.es
 */
package es.gob.fire.test.webapp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.gob.fire.client.BatchResult;

/**
 * Servicio para la recuperaci&oacute;n del resultado de firma de un lote.
 */
public class RecoverBatchService extends HttpServlet {

	/** Serial Id. */
	private static final long serialVersionUID = 2571572136476032759L;

	private static final Logger LOGGER = Logger.getLogger(RecoverBatchService.class.getName());

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		final HttpSession session = request.getSession(false);
		if ( session == null || session.getAttribute("user") == null) { //$NON-NLS-1$
			response.sendRedirect("Login.jsp"); //$NON-NLS-1$
			return;
		}

		final String transactionId = (String) session.getAttribute("transactionId"); //$NON-NLS-1$
		if (transactionId == null) {
			LOGGER.severe("No se ha encontrado id de transaccion iniciada"); //$NON-NLS-1$
			response.sendRedirect("Login.jsp"); //$NON-NLS-1$
			return;
		}

	   BatchResult result = null;
	    try {
	    	result = BatchHelper.recoverBatchResult(request);
	    }
	    catch (final Exception e) {
			Logger.getLogger("es.gob.fire.test.webapp").severe( //$NON-NLS-1$
					"Error durante la operacion de recuperacion del lote: " + e); //$NON-NLS-1$
	    	response.sendRedirect("ErrorPage.jsp?msg=" + e.getMessage()); //$NON-NLS-1$
	    	return;
	    }


	    response.getOutputStream().write(result.toString().getBytes(StandardCharsets.UTF_8));
	    response.flushBuffer();
	}
}
