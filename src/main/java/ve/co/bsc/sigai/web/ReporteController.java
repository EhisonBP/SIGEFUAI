package ve.co.bsc.sigai.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import ve.co.bsc.sigai.domain.Parametro;
import ve.co.bsc.sigai.domain.Personalizacion;
import ve.co.bsc.sigai.domain.Reporte;
import ve.co.bsc.sigai.form.ReporteParametro;
import ve.co.bsc.util.ListDecorator;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "reporte", automaticallyMaintainView = true, formBackingObject = Reporte.class)
@RequestMapping("/reporte/**")
@Controller
@SessionAttributes({ "parametros", "reporte" })
public class ReporteController {

	static Logger logger = Logger.getLogger(ReporteController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@Autowired(required = true)
	private DataSource dataSource;

	@RequestMapping(value = "/reporte", method = RequestMethod.POST)
	public String create(@Valid Reporte reporte, BindingResult result,
			MultipartHttpServletRequest request, ModelMap modelMap,
			SessionStatus status) {
		if (reporte == null) {
			throw new IllegalArgumentException("A reporte is required");
		}

		if (request.getFile("filedata").isEmpty()) {
			result.addError(new ObjectError("reporte",
					"El campo ruta es obligatorio"));
		} else {
			String originalFilename = request.getFile("filedata")
					.getOriginalFilename();
			String[] originalFileNameArray = originalFilename.split("\\.");
			try {
				if (!originalFileNameArray[1].equals("jasper")) {
					result.addError(new FieldError(result.getObjectName(),
							"descripcion", "Tipo de archivo inválido"));
				}
			} catch (Exception e) {
				result.addError(new ObjectError("reporte",
						"No fue posible almacenar el archivo seleccionado"));
				return "archivoadjunto/create";
			}
			reporte.setNombre(originalFileNameArray[0]);
			String extension = reporte.getExtension();
			if (extension.equals("0")) {
				result.addError(new FieldError(result.getObjectName(),
						"extension", "Debe seleccionar el formato del reporte"));
			}
		}

		if (result.hasErrors()) {
			modelMap.addAttribute("reporte", reporte);
			return "reporte/create";
		}

		String clave = reporte.getClave();
		if ((clave != null) && (clave.length() > 0)) {
			reporte.setClave("ve.co.bsc.sigefuai.reportes." + clave);
		}

		reporte.persist();
		status.setComplete();
		return "redirect:/reporte/" + reporte.getId();
	}

	@RequestMapping(value = "/reporte/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap, HttpServletRequest hsr) {
		Reporte reporte = new Reporte();

		if (hsr.getParameter("idReporte") != null) {
			// Entra aqui si tiene un padre
			Reporte reportePadre = Reporte.findReporte(new Long(hsr
					.getParameter("idReporte")));
			reporte.setReporte(reportePadre);
			reporte.setExtension(reportePadre.getExtension());
		}

		modelMap.addAttribute("reporte", reporte);
		return "reporte/create";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("reporte") Reporte reporte,
			BindingResult result, MultipartHttpServletRequest request,
			ModelMap modelMap, SessionStatus status) {
		if (reporte == null)
			throw new IllegalArgumentException("A reporte is required");

		if (!request.getFile("filedata").isEmpty()) {
			String originalFilename = request.getFile("filedata")
					.getOriginalFilename();
			String[] originalFileNameArray = originalFilename.split("\\.");
			try {
				if (!originalFileNameArray[1].equals("jasper")) {
					result.addError(new FieldError(result.getObjectName(),
							"nombre", "Tipo de archivo inválido"));
				}
			} catch (Exception e) {
				result.addError(new ObjectError("reporte",
						"No fue posible almacenar el archivo seleccionado"));
				return "archivoadjunto/update";
			}

			String extension = reporte.getExtension();
			if (extension.equals("0")) {
				result.addError(new FieldError(result.getObjectName(),
						"extension", "Debe seleccionar el formato del reporte"));
			}

			if (result.hasErrors()) {
				modelMap.addAttribute("reporte", reporte);
				return "reporte/update";
			}

			String clave = reporte.getClave();
			if ((clave != null) && (clave.length() > 0)) {
				reporte.setClave("ve.co.bsc.sigefuai.reportes."
						+ reporte.getClave());
			}

			reporte.merge();
			status.setComplete();
			return "redirect:/reporte/" + reporte.getId();
		} else {
			if (result.hasErrors()) {
				modelMap.addAttribute("reporte", reporte);
				return "reporte/update";
			}
			Reporte rep = Reporte.findReporte(reporte.getId());
			rep.setDescripcion(reporte.getDescripcion());
			rep.setExtension(reporte.getExtension());
			rep.setClave(reporte.getClave());
			String clave = rep.getClave();
			if (clave.length() > 0) {
				rep.setClave("ve.co.bsc.sigefuai.reportes." + clave);
			}
			rep.merge();
			status.setComplete();
			return "redirect:/reporte/" + reporte.getId();
		}

	}

	@RequestMapping(value = "/reporte/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		Reporte reporte = Reporte.findReporte(id);

		String clave = reporte.getClave();
		if ((clave != null) && (clave.length() > 0)) {
			String claveToda = reporte.getClave();
			logger.debug("");
			String[] todo = claveToda.split("ve.co.bsc.sigefuai.reportes.");
			reporte.setClave(todo[1]);
		}

		modelMap.addAttribute("reporte", reporte);
		return "reporte/update";
	}

	@RequestMapping(value = "/reporte/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		Reporte elReporte = Reporte.findReporte(id);
		modelMap.addAttribute("reporte", elReporte);
		modelMap.addAttribute("parametros",
				Parametro.findParametroesByReporte(elReporte).getResultList());
		modelMap.addAttribute("subReportes",
				Reporte.findReportesByReporte(elReporte).getResultList());

		return "reporte/show";
	}

	@RequestMapping(value = "/reporte", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			ModelMap modelMap) {
		modelMap.addAttribute("reportes", Reporte.findReportesByReporteNull()
				.getResultList());
		return "reporte/list";
	}

	@RequestMapping(value = "/reporte/prepare", method = RequestMethod.GET)
	public String prepare(
	// @PathVariable("id") Long id,
	// @RequestParam(value="id",required=false) Long id,
	// @RequestParam(value="key",required=false) String key,
			HttpServletRequest hsr, ModelMap modelMap) {

		// if (id == null) {
		// throw new IllegalArgumentException("An Identifier is required");
		// }
		logger.debug("Ejecutando prepare....");
		// logger.debug("key es " + key);
		Reporte elReporte = new Reporte();
		// La funcion se llamo desde el modulo de reportes
		if (hsr.getParameter("id") != null)
		// if (id!=null)
		{
			logger.debug("Id no es null");
			elReporte = Reporte.findReporte(Long.parseLong(hsr
					.getParameter("id")));
		}
		// La funcion se llamo directamente (ajax)
		else {
			logger.debug("Key no es null");
			logger.debug("El valor de key es " + hsr.getParameter("key"));
			List<Reporte> reportes = Reporte.findReportesPorClave(
					hsr.getParameter("key")).getResultList();
			logger.debug("AQUI");
			logger.debug("Reportes lench: " + reportes.size());
			elReporte = reportes.get(0);
			logger.debug("El reporte seleccionado es: " + elReporte.getNombre());
		}
		logger.debug("El nombre del reporte es " + elReporte.getNombre());
		// Reporte elReporte = Reporte.findReporte(id);
		// if (elReporte == null) {
		// throw new IllegalArgumentException("Reporte no encontrado");
		// }

		List<Parametro> losParametros = Parametro.findParametroesByReporte(
				elReporte).getResultList();

		List<ReporteParametro> param = new LinkedList<ReporteParametro>();
		for (Parametro p : losParametros) {
			logger.debug("Iterando losParametros");
			ReporteParametro unParametro = new ReporteParametro();
			logger.debug("Nombre parametro " + p.getParametro());
			unParametro.setNombre(p.getParametro());
			unParametro.setTipo(p.getTipoParametro());

			param.add(unParametro);
		}
		ListDecorator<Parametro> parametros = new ListDecorator(param);
		logger.debug("Array parametros " + parametros);
		modelMap.addAttribute("reporte", elReporte);
		modelMap.addAttribute("parametros", parametros);

		return "reporte/prepare";
	}

	public void escribirSubreportes(Reporte _reporte) {
		String rutaTemp = System.getProperty("java.io.tmpdir");
		List<Reporte> subReportes = Reporte.findReportesByReporte(_reporte)
				.getResultList();
		Iterator it = subReportes.iterator();
		while (it.hasNext()) {
			Reporte elReporte = (Reporte) it.next();
			try {
				FileOutputStream out = new FileOutputStream(rutaTemp
						+ File.separator + elReporte.getNombre() + ".jasper");
				out.write(elReporte.getFiledata());
				out.close();
				logger.debug("*** El subReporte se escribe en: " + rutaTemp
						+ File.separator + elReporte.getNombre() + ".jasper");
				escribirSubreportes(elReporte);
			} catch (FileNotFoundException ex) {
				logger.error("Error intenando escribir archivo en directorio temporal");
			} catch (IOException e) {
				logger.error("Error intenando escribir archivo en directorio temporal");
			}
		}
	}

	@RequestMapping(value = "/reporte/render/{id}", method = RequestMethod.GET)
	public void render(
			ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute("parametros") ListDecorator<ReporteParametro> parametros,
			@PathVariable("id") Long id) throws ClassNotFoundException {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}

		Personalizacion personalizacion = Personalizacion
				.findPersonalizacion(new Long(1));

		Reporte elReporte = Reporte.findReporte(id);
		if (elReporte == null) {
			throw new IllegalArgumentException("Reporte no encontrado");
		}
		try {

			HashMap params = new HashMap();
			for (ReporteParametro p : parametros.getElements()) {
				if (p.getTipo().equals("int")) {
					params.put(p.getNombre(), Integer.parseInt(p.getValor()));
				} else {
					params.put(p.getNombre(), p.getValor());
				}
				// TODO: else if, else if etc...
			}

			InputStream is = new ByteArrayInputStream(
					personalizacion.getArchivoImagen());
			params.put("rutaLogo", is);

			String rutaTemp = System.getProperty("java.io.tmpdir");

			params.put("reportsDirPath", rutaTemp);

			// Enviamos el usuario que esta logeado
			Object principal = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			params.put("preparadoPor", ((User) principal).getUsername()
					.toString());
			// *******************************************************************************
			// Enviamos el Rif de la institucion pertenecientes al usuario
			// loggiado
			Util util = new Util();
			params.put("IntRif", util.obtenerRif());

			// *******************************************************************************
			String contentType = "";
			JRExporter exporter = null;

			// then make a file object
			// Saco el archivo del reporte ppal guardado en la bd y lo escribo
			// en el sistema de archivos temporales
			FileOutputStream out = new FileOutputStream(rutaTemp
					+ File.separator + elReporte.getNombre() + ".jasper");
			out.write(elReporte.getFiledata());
			out.close();

			logger.debug("*** (render) El reporte padre se escribe en: "
					+ rutaTemp + File.separator + elReporte.getNombre()
					+ ".jasper");

			// Busco si el reporte tiene subreportes y escribo los archivos en
			// el sistema de archivos temporales
			escribirSubreportes(elReporte);

			JasperPrint print = JasperFillManager.fillReport(rutaTemp
					+ File.separator + elReporte.getNombre() + ".jasper",
					params, dataSource.getConnection());

			if (elReporte.getExtension().equals("pdf")) {

				// Create a PDF exporter
				exporter = new JRPdfExporter();
				// contentType = "application/PDF";
				ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,
						print);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM,
						baosPDF);
				exporter.exportReport();

				response.reset();
				response.addHeader("content-disposition",
						"attachment; filename=" + elReporte.getNombre() + "."
								+ elReporte.getExtension());
				response.setContentType("application/pdf");
				response.setContentLength(baosPDF.size());

				ServletOutputStream outputstreamPDF = response
						.getOutputStream();
				baosPDF.writeTo(outputstreamPDF);

				outputstreamPDF.flush();
				baosPDF.close();

			} else if (elReporte.getExtension().equals("odt")) {

				// Create a ODTexporter
				exporter = new JROdtExporter();
				// exporter = new JRRtfExporter();
				// contentType = "application/ODT";
				ByteArrayOutputStream baosODT = new ByteArrayOutputStream();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
						baosODT);
				exporter.exportReport();

				response.reset();
				response.addHeader("content-disposition",
						"attachment; filename=" + elReporte.getNombre() + "."
								+ elReporte.getExtension());
				// response.addHeader("content-disposition",
				// "attachment; filename=" + elReporte.getNombre() + ".rtf" );
				response.setContentType("application/odt");
				response.setContentLength(baosODT.size());

				ServletOutputStream outputstreamODT = response
						.getOutputStream();
				baosODT.writeTo(outputstreamODT);

				outputstreamODT.flush();
				baosODT.close();

			}
		} catch (IOException e) {
			logger.error("Error renderizando reporte ", e);
		} catch (JRException e) {
			logger.error("Error renderizando reporte ", e);
		} catch (SQLException e) {
			logger.error("Error renderizando reporte ", e);
		}
	}

	@RequestMapping(value = "/reporte/renderDirect/", method = RequestMethod.GET)
	public void renderDirect(
			ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute("parametros") ListDecorator<ReporteParametro> parametros,
			@Valid @ModelAttribute("reporte") Reporte elReporte) {
		// if (id == null) {
		// throw new IllegalArgumentException("An Identifier is required");
		// }

		Personalizacion personalizacion = Personalizacion
				.findPersonalizacion(new Long(1));

		// Reporte elReporte = Reporte.findReporte(id);
		if (elReporte == null) {
			throw new IllegalArgumentException("Reporte no encontrado");
		}
		try {

			HashMap params = new HashMap();
			for (ReporteParametro p : parametros.getElements()) {
				if (p.getTipo().equals("int")) {
					params.put(p.getNombre(), Integer.parseInt(p.getValor()));
					logger.debug("***valor de actuacionId: " + p.getValor());
				} else {
					params.put(p.getNombre(), p.getValor());
				}
				// TODO: else if, else if etc...
			}

			InputStream is = new ByteArrayInputStream(
					personalizacion.getArchivoImagen());
			params.put("rutaLogo", is);

			String rutaTemp = System.getProperty("java.io.tmpdir");

			params.put("reportsDirPath", rutaTemp);

			// Enviamos el usuario que esta logeado
			Object principal = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			params.put("preparadoPor", ((User) principal).getUsername()
					.toString());
			// *******************************************************************************
			// Enviamos el Rif de la institucion pertenecientes al usuario
			// loggiado
			Util util = new Util();
			params.put("IntRif", util.obtenerRif());

			// *******************************************************************************
			String contentType = "";
			JRExporter exporter = null;

			// then make a file object
			// Saco el archivo del reporte ppal guardado en la bd y lo escribo
			// en el sistema de archivos temporales
			FileOutputStream out = new FileOutputStream(rutaTemp
					+ File.separator + elReporte.getNombre() + ".jasper");
			out.write(elReporte.getFiledata());
			out.close();

			logger.debug("*** (renderDirect) El reporte padre se escribe en: "
					+ rutaTemp + File.separator + elReporte.getNombre()
					+ ".jasper");

			// Busco si el reporte tiene subreportes y escribo los archivos en
			// el sistema de archivos temporales
			escribirSubreportes(elReporte);

			JasperPrint print = JasperFillManager.fillReport(rutaTemp
					+ File.separator + elReporte.getNombre() + ".jasper",
					params, dataSource.getConnection());

			if (elReporte.getExtension().equals("pdf")) {

				// Create a PDF exporter
				exporter = new JRPdfExporter();
				// contentType = "application/PDF";
				ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,
						print);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM,
						baosPDF);
				exporter.exportReport();

				response.reset();
				response.addHeader("content-disposition",
						"attachment; filename=" + elReporte.getNombre() + "."
								+ elReporte.getExtension());
				response.setContentType("application/pdf");
				response.setContentLength(baosPDF.size());

				ServletOutputStream outputstreamPDF = response
						.getOutputStream();
				baosPDF.writeTo(outputstreamPDF);

				outputstreamPDF.flush();
				baosPDF.close();

			} else if (elReporte.getExtension().equals("odt")) {
				// Create a ODTexporter
				exporter = new JROdtExporter();
				// contentType = "application/ODT";
				ByteArrayOutputStream baosODT = new ByteArrayOutputStream();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
						baosODT);
				exporter.exportReport();

				response.reset();
				response.addHeader("content-disposition",
						"attachment; filename=" + elReporte.getNombre() + "."
								+ elReporte.getExtension());
				response.setContentType("application/odt");
				response.setContentLength(baosODT.size());

				ServletOutputStream outputstreamODT = response
						.getOutputStream();
				baosODT.writeTo(outputstreamODT);

				outputstreamODT.flush();
				baosODT.close();
			}
		} catch (IOException e) {
			logger.error("Error renderizando reporte ", e);
		} catch (JRException e) {
			logger.error("Error renderizando reporte ", e);
		} catch (SQLException e) {
			logger.error("Error renderizando reporte ", e);
		}
	}

	@RequestMapping(value = "/reporte/listuser", method = RequestMethod.GET)
	public String listUser(ModelMap modelMap) {
		modelMap.addAttribute("reportes", Reporte.findReportesByReporteNull()
				.getResultList());

		return "reporte/listuser";
	}
}
